package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Questiontype;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("questionTypeDAO")
public class QuestionTypeDAOImpl implements QuestionTypeDAO{
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
    public void add(Questiontype bean) {
        getHibernateTemplate().save(bean);
    }

    @Override
    public Questiontype get(Integer id) {
        return getHibernateTemplate().get(Questiontype.class,id);
    }

    @Override
    public void update(Questiontype bean) {
        getHibernateTemplate().update(bean);
    }
}
