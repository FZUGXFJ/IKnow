package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Comment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("commentDAO")
public class CommentDAOimpl implements CommentDAO{

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
    public void add(Comment bean) {
        getHibernateTemplate().save(bean);
    }

    @Override
    public Comment get(Integer id) {
        return getHibernateTemplate().get(Comment.class,id);
    }

    @Override
    public void update(Comment bean) {
        getHibernateTemplate().update(bean);
    }
}
