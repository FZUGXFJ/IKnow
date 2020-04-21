package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Useridentity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("userIdentityDAO")
public class UserIdentityDAOImpl implements UserIdentityDAO{
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
    public void add(Useridentity bean) {
        getHibernateTemplate().save(bean);
    }

    @Override
    public Useridentity get(Integer id) {
        return getHibernateTemplate().get(Useridentity.class,id);
    }

    @Override
    public void update(Useridentity bean) {
        getHibernateTemplate().update(bean);
    }
}