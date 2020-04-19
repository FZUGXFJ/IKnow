package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Level;
import org.gxfj.iknow.pojo.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {

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
    public void add(User bean) {
        getHibernateTemplate().save(bean);
    }

    @Override
    public User get(Integer id) {
        return getHibernateTemplate().get(User.class,id);
    }

    @Override
    public void update(User bean) {
        getHibernateTemplate().update(bean);
    }
}
