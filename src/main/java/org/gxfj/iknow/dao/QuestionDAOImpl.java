package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Answer;
import org.gxfj.iknow.pojo.Question;
import org.gxfj.iknow.pojo.Questionstate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("questionDAO")
public class QuestionDAOImpl implements QuestionDAO {

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
    public Question get(Integer id) {
        return getHibernateTemplate().get(Question.class,id);
    }

    @Override
    public void update(Question bean) {
        getHibernateTemplate().update(bean);
    }

    @Override
    public void add(Question bean) {
        getHibernateTemplate().save(bean);
    }

    @Override
    public void delete(Question bean) {
        update(bean);
    }

    @Override
    public Integer getQuestionStateId(Integer questionID){
        List<Integer> list=getHibernateTemplate().execute(new HibernateCallback<List<Integer>>() {
            @Override
            public List<Integer> doInHibernate(Session session) throws HibernateException {
                SQLQuery sqlQuery=session.createSQLQuery("select stateID from question where id="+questionID);
                return sqlQuery.list();
            }
        });
        if (list.isEmpty()) {
            return 0;
        } else {
            return list.get(0);
        }
    }

}
