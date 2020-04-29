package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Browsinghistory;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("browsingHistoryDAO")
public class BrowsingHistoryDAOImpl implements BrowsingHistoryDAO{
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
    public void add(Browsinghistory bean) {
        getHibernateTemplate().save(bean);
    }

    @Override
    public Browsinghistory get(Integer id) {
        return getHibernateTemplate().get(Browsinghistory.class,id);
    }

    @Override
    public void update(Browsinghistory bean) {
        getHibernateTemplate().update(bean);
    }

    @Override
    public Integer getBrowsingCount(Integer questionId){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Browsinghistory WHERE questionID = ?");
        return query.setInteger(0,questionId).list().size();
    }

}
