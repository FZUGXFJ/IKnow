package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.*;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public Questiontype get(Integer categoryTypeID, Integer subjectTypeID, Integer majorTypeID) {
        String hql = "FROM Questiontype AS q WHERE (q.categoriestypeByCategoryId.id = ?0) and (q.subjecttypeBySubjectId.id = ?1) and (q.majortypeByMajorId.id = ?2) ";
        Query query = getSession().createQuery(hql);
        query.setParameter("0", categoryTypeID);
        query.setParameter("1", subjectTypeID);
        query.setParameter("2", majorTypeID);
        List<Questiontype> list = query.list();

        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public void update(Questiontype bean) {
        getHibernateTemplate().update(bean);
    }
}
