package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.College;
import org.gxfj.iknow.pojo.Comment;
import org.gxfj.iknow.pojo.School;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public Integer add(College bean) {
        return (Integer)getHibernateTemplate().save(bean);
    }

    @Override
    public College get(Integer id) {
        return getHibernateTemplate().get(College.class,id);
    }

    @Override
    public void update(College bean) {
        getHibernateTemplate().update(bean);
    }

    @Override
    public College getCollegeByName(String collegeName) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from College as c WHERE c.name = :collegeName";
        Query query = session.createQuery(hql);
        return (College) query.setParameter("collegeName",collegeName).uniqueResult();
    }

    @Override
    public List<College> listBySchoolId(Integer schoolId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from College as c WHERE schoolID = :schoolId";
        Query query = session.createQuery(hql);
        return  query.setParameter("schoolId",schoolId).list();
    }


}
