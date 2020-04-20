package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Admin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("adminDAO")
public class AdminDAOImpl implements AdminDAO{
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
    public void add(Admin bean) {
        getHibernateTemplate().save(bean);
    }

    @Override
    public Admin get(Integer id) {
        return getHibernateTemplate().get(Admin.class,id);
    }

    @Override
    public void update(Admin bean) {
        getHibernateTemplate().update(bean);
    }
}
