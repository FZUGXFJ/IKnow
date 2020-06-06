package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.College;
import org.gxfj.iknow.pojo.Major;
import org.gxfj.iknow.pojo.School;
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
    public Major getMajorByName(String majorName,Integer collegeId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Major as m WHERE name = :majorName and collegeID = :collegeId";
        Query query = session.createQuery(hql);
        return (Major) query.setParameter("majorName",majorName)
                .setParameter("collegeId",collegeId).uniqueResult();
    }

    @Override
    public boolean delete(Integer majorId) {
        Session session = sessionFactory.getCurrentSession();
        Major major = (Major) session.load(Major.class, majorId);
        sessionFactory.getCurrentSession().delete(major);
        return true;
    }
}
