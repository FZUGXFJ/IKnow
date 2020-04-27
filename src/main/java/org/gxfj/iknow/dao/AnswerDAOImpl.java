package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Answer;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("answerDAO")
public class AnswerDAOImpl implements AnswerDAO{
    private HibernateTemplate ht = null;

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    private HibernateTemplate getHibernateTemplate() {
        if (ht == null) {
            ht = new HibernateTemplate(sessionFactory);
        }
        return ht;
    }
    @Override
    public void add(Answer bean) {
        getHibernateTemplate().save(bean);
    }

    @Override
    public Answer get(Integer id) {
        return getHibernateTemplate().get(Answer.class,id);
    }


    @Override
    public void update(Answer bean) {
        getHibernateTemplate().update(bean);
    }

    @Override
    public List getAnswersbyQid(Integer qid) {
        List<Answer> list=getHibernateTemplate().execute(new HibernateCallback<List<Answer>>() {
            @Override
            public List<Answer> doInHibernate(Session session) throws HibernateException {
                SQLQuery sqlQuery=session.createSQLQuery("select * from answer where " +
                        "questionID='"+qid+"'").addEntity(Answer.class);
                return sqlQuery.list();
            }
        });
        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

}
