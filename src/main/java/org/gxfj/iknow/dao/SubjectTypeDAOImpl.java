package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Subjecttype;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public Integer add(Subjecttype bean) {
        return (Integer)getHibernateTemplate().save(bean);
    }

    @Override
    public Subjecttype get(Integer id) {
        return getHibernateTemplate().get(Subjecttype.class,id);
    }

    @Override
    public void update(Subjecttype bean) {
        getHibernateTemplate().update(bean);
    }

    @Override
    public List<Subjecttype> list(Integer categoryTypeId) {
        String hql = "FROM Subjecttype as s WHERE s.categoriestypeByCategoryId.id = ?1";
        Query query = getSession().createQuery(hql);
        query.setParameter("1", categoryTypeId);
        return (List<Subjecttype>) query.list();

    }
}
