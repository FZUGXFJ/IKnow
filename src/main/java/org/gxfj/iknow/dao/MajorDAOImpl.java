package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.College;
import org.gxfj.iknow.pojo.Major;
import org.hibernate.Query;
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
    public Integer add(Major bean) {
        return (Integer)getHibernateTemplate().save(bean);
    }

    @Override
    public Major get(Integer id) {
        return getHibernateTemplate().get(Major.class,id);
    }

    @Override
    public void update(Major bean) {
        getHibernateTemplate().update(bean);
    }

    @Override
    public Major getMajorByName(String majorName) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Major as m WHERE m.name = :majorName";
        Query query = session.createQuery(hql);
        return (Major) query.setParameter("majorName",majorName).uniqueResult();
    }
}
