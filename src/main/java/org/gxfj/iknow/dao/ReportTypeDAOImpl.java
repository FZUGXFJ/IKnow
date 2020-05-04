package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Reporttype;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("reportTypeDAO")
public class ReportTypeDAOImpl implements ReportTypeDAO{
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
    public Integer add(Reporttype bean) {
        return (Integer)getHibernateTemplate().save(bean);
    }

    @Override
    public Reporttype get(Integer id) {
        return getHibernateTemplate().get(Reporttype.class,id);
    }

    @Override
    public void update(Reporttype bean) {
        getHibernateTemplate().update(bean);
    }
}
