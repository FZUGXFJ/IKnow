package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Reportreason;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("reportReasonDAO")
public class ReportReasonDAOImpl implements ReportReasonDAO{
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
    public Integer add(Reportreason bean) {
        return (Integer)getHibernateTemplate().save(bean);
    }

    @Override
    public Reportreason get(Integer id) {
        return getHibernateTemplate().get(Reportreason.class,id);
    }

    @Override
    public void update(Reportreason bean) {
        getHibernateTemplate().update(bean);
    }
}
