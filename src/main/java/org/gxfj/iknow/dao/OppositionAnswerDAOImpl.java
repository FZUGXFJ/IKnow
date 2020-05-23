package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Approvalanswer;
import org.gxfj.iknow.pojo.Oppositionanswer;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("oppositionAnswerDAO")
public class OppositionAnswerDAOImpl implements OppositionAnswerDAO{
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
    public Integer add(Oppositionanswer bean) {
        return (Integer)getHibernateTemplate().save(bean);
    }

    @Override
    public Oppositionanswer get(Integer id) {
        return getHibernateTemplate().get(Oppositionanswer.class,id);
    }

    @Override
    public void update(Oppositionanswer bean) {
        getHibernateTemplate().update(bean);
    }

    @Override
    public Integer searchByUserIdandAnswerId(Integer uid, Integer aid) {
        Integer answerId=getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session) throws HibernateException {
                SQLQuery sqlQuery=session.createSQLQuery(
                        "SELECT id from OppositionAnswer WHERE userID=" + uid + "&&answerID="+aid);
                return (Integer)sqlQuery.uniqueResult();
            }
        });
        if (answerId==null){
            return -1;
        }
        return answerId;
    }

    @Override
    public void delete(Oppositionanswer bean) {
        getHibernateTemplate().delete(bean);
    }
}
