package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Categoriestype;
import org.gxfj.iknow.pojo.Majortype;
import org.gxfj.iknow.pojo.Questiontype;
import org.gxfj.iknow.pojo.Subjecttype;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        Questiontype questiontype = new Questiontype();
        Categoriestype categoriestype = new Categoriestype();
        Subjecttype subjecttype = new Subjecttype();
        Majortype majortype = new Majortype();

        categoriestype.setId(categoryTypeID);
        subjecttype.setId(subjectTypeID);
        majortype.setId(majorTypeID);
        questiontype.setCategoriestypeByCategoryId(categoriestype);
        questiontype.setSubjecttypeBySubjectId(subjecttype);
        questiontype.setMajortypeByMajorId(majortype);

        List<Questiontype> list = getHibernateTemplate().findByExample(questiontype);
        if (list.size() != 0) {
            return list.get(0);
        } else {
            return null;
        }

    }

    @Override
    public void update(Questiontype bean) {
        getHibernateTemplate().update(bean);
    }
}
