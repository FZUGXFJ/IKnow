package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Reply;
import org.gxfj.iknow.pojo.Report;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("reportDAO")
public class ReportDAOImpl implements ReportDAO{
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
    public Integer add(Report bean) {
        return (Integer)getHibernateTemplate().save(bean);
    }

    @Override
    public Report get(Integer id) {
        return getHibernateTemplate().get(Report.class,id);
    }

    @Override
    public void update(Report bean) {
        getHibernateTemplate().update(bean);
    }

    @Override
    public List<Report> listReportByType(Integer reportType){
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Report as r WHERE typeID = :reportType";
        Query query = session.createQuery(hql).setParameter("reportType",reportType);
        return query.list();
    }

    @Override
    public void delete(Report bean) {
        getHibernateTemplate().delete(bean);
    }
}
