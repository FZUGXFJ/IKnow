package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Answer;
import org.gxfj.iknow.pojo.Question;
import org.gxfj.iknow.pojo.Questionstate;
import org.hibernate.*;
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
    public Integer add(Question bean) {
        return (Integer)getHibernateTemplate().save(bean);
    }

    @Override
    public void delete(Question bean) {
        update(bean);
    }

    @Override
    public Integer getQuestionStateId(Integer questionId){
        Integer stateId=getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session) throws HibernateException {
                SQLQuery sqlQuery=session.createSQLQuery("select stateID from question where id="+ questionId);
                return (Integer)sqlQuery.uniqueResult();
            }
        });
        if (stateId == 1) {
            return 0;
        } else if(stateId == 2){
            return 1;
        }else{
            return -1;
        }
    }

    @Override
    public Integer getAnswerCount(Integer id) {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT count(*) from Answer WHERE questionId=" + id);
        return ((Long) query.uniqueResult()).intValue();
    }

}
