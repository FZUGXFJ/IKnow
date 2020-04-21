package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Userstate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("userStateDAO")
public class UserStateDAOImpl implements  UserStateDAO{
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
    public void add(Userstate bean) {
        getHibernateTemplate().save(bean);
    }

    @Override
    public Userstate get(Integer id) {
        return getHibernateTemplate().get(Userstate.class,id);
    }

    @Override
    public void update(Userstate bean) {
        getHibernateTemplate().update(bean);
    }
}
