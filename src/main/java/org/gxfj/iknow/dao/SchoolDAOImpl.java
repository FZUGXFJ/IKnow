package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.School;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("schoolDAO")
public class SchoolDAOImpl implements SchoolDAO{
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
    public Integer add(School bean) {
        return (Integer)getHibernateTemplate().save(bean);
    }

    @Override
    public School get(Integer id) {
        return getHibernateTemplate().get(School.class,id);
    }

    @Override
    public void update(School bean) {
        getHibernateTemplate().update(bean);
    }
}
