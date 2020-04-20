package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Categoriestype;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("categoriesTypeDAO")
public class CategoriesTypeDAOImpl implements CategoriesTypeDAO{
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
    public void add(Categoriestype bean) {
        getHibernateTemplate().save(bean);
    }

    @Override
    public Categoriestype get(Integer id) {
        return getHibernateTemplate().get(Categoriestype.class,id);
    }

    @Override
    public void update(Categoriestype bean) {
        getHibernateTemplate().update(bean);
    }
}
