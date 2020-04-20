package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Subjecttype;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("subjectTypeDAO")
public class SubjectTypeDAOImpl implements SubjectTypeDAO{
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
    public void add(Subjecttype bean) {
        getHibernateTemplate().save(bean);
    }

    @Override
    public Subjecttype get(Integer id) {
        return getHibernateTemplate().get(Subjecttype.class,id);
    }

    @Override
    public void update(Subjecttype bean) {
        getHibernateTemplate().update(bean);
    }
}
