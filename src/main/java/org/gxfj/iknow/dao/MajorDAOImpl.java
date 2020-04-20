package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Major;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("majorDAO")
public class MajorDAOImpl implements MajorDAO{
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
    public void add(Major bean) {
        getHibernateTemplate().save(bean);
    }

    @Override
    public Major get(Integer id) {
        return getHibernateTemplate().get(Major.class,id);
    }

    @Override
    public void update(Major bean) {
        getHibernateTemplate().update(bean);
    }
}
