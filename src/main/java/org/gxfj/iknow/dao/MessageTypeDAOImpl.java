package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Message;
import org.gxfj.iknow.pojo.Messagetype;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author erniumo
 */
@Repository("messageTypeDAO")
public class MessageTypeDAOImpl implements MessageTypeDAO {
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
    public Integer add(Messagetype bean) {
        return (Integer)getHibernateTemplate().save(bean);
    }

    @Override
    public Messagetype get(Integer id) {
        return getHibernateTemplate().get(Messagetype.class,id);
    }

    @Override
    public void update(Messagetype bean) {
        getHibernateTemplate().update(bean);
    }
}
