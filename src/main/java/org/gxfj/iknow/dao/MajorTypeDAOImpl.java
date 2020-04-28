package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Major;
import org.gxfj.iknow.pojo.Majortype;
import org.gxfj.iknow.pojo.Subjecttype;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author qmbx
 */
@Repository("majorTypeDAO")
public class MajorTypeDAOImpl implements MajorTypeDAO {
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
    public void add(Majortype bean) {
        getHibernateTemplate().save(bean);
    }

    @Override
    public Majortype get(Integer id) {
        return getHibernateTemplate().get(Majortype.class,id);
    }

    @Override
    public void update(Majortype bean) {
        getHibernateTemplate().update(bean);
    }

    @Override
    public List<Majortype> list(Integer subjectTypeID) {
        String hql = "FROM Majortype AS m WHERE m.subjecttypeBySubjectId.id = ?1";
        Query query = getSession().createQuery(hql);
        query.setParameter("1", subjectTypeID);
        return (List<Majortype>) query.list();
    }

}
