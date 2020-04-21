package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Searchhistory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("searchHistoryDAO ")
public class SearchHistoryDAOImpl implements SearchHistoryDAO{
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
    public void add(Searchhistory bean) {
        getHibernateTemplate().save(bean);
    }

    @Override
    public Searchhistory get(Integer id) {
        return getHibernateTemplate().get(Searchhistory.class,id);
    }

    @Override
    public void update(Searchhistory bean) {
        getHibernateTemplate().update(bean);
    }
}
