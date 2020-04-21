package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.College;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("collegeDAO")
public class CollegeDAOImpl implements CollegeDAO{
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
    public void add(College bean) {
        getHibernateTemplate().save(bean);
    }

    @Override
    public College get(Integer id) {
        return getHibernateTemplate().get(College.class,id);
    }

    @Override
    public void update(College bean) {
        getHibernateTemplate().update(bean);
    }
}
