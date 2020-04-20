package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Questionstate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("questionStateDAO ")
public class QuestionStateDAOImpl implements QuestionStateDAO{
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
    public void add(Questionstate bean) {
        getHibernateTemplate().save(bean);
    }

    @Override
    public Questionstate get(Integer id) {
        return getHibernateTemplate().get(Questionstate.class,id);
    }

    @Override
    public void update(Questionstate bean) {
        getHibernateTemplate().update(bean);
    }
}
