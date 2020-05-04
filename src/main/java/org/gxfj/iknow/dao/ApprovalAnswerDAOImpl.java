package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Approvalanswer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("approvalAnswerDAO")
public class ApprovalAnswerDAOImpl implements ApprovalAnswerDAO{
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
    public Integer add(Approvalanswer bean) {
        return (Integer)getHibernateTemplate().save(bean);
    }

    @Override
    public Approvalanswer get(Integer id) {
        return getHibernateTemplate().get(Approvalanswer.class,id);
    }

    @Override
    public void update(Approvalanswer bean) {
        getHibernateTemplate().update(bean);
    }
}
