package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Browsinghistory;
import org.gxfj.iknow.pojo.Collectionproblem;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("collectionProblemDAO")
public class CollectionProblemDAOImpl implements CollectionProblemDAO{

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
    public Integer add(Collectionproblem bean) {
        return (Integer)getHibernateTemplate().save(bean);
    }

    @Override
    public Collectionproblem get(Integer id) {
        return getHibernateTemplate().get(Collectionproblem.class,id);
    }

    @Override
    public void update(Collectionproblem bean) {
        getHibernateTemplate().update(bean);
    }

    @Override
    public Integer getCollectionCount(Integer questionId){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(c) from Collectionproblem as c WHERE questionID = ?");
        return ((Long)query.setInteger(0,questionId).uniqueResult()).intValue();
    }
}
