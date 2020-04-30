package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Approvalreply;
import org.gxfj.iknow.pojo.Reply;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("approvalReplyDAO")
public class ApprovalReplyDAOImpl implements ApprovalReplyDAO{
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
    public void add(Approvalreply bean) {
        getHibernateTemplate().save(bean);
    }

    @Override
    public Approvalreply get(Integer id) {
        return getHibernateTemplate().get(Approvalreply.class,id);
    }

    @Override
    public void update(Approvalreply bean) {
        getHibernateTemplate().update(bean);
    }

}
