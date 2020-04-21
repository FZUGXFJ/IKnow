package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Collectionproblem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("collectionProblemDAO")
public class CollectionProblemDAOImpl implements CollectionProblemDAO{

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
    public void add(Collectionproblem bean) {
        getHibernateTemplate().save(bean);
    }

    @Override
    public Collectionproblem get(Integer id) {
        return getHibernateTemplate().get(Collectionproblem.class,id);
    }

    @Override
    public void update(Collectionproblem bean) {
        getHibernateTemplate().update(bean);
    }
}
