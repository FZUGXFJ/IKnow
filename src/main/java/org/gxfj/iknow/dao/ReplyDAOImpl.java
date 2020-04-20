package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Reply;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("replyDAO")
public class ReplyDAOImpl implements ReplyDAO{
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
    public void add(Reply bean) {
        getHibernateTemplate().save(bean);
    }

    @Override
    public Reply get(Integer id) {
        return getHibernateTemplate().get(Reply.class,id);
    }

    @Override
    public void update(Reply bean) {
        getHibernateTemplate().update(bean);

    }
}
