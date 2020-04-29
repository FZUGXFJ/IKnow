package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Answer;
import org.gxfj.iknow.pojo.Comment;
import org.gxfj.iknow.pojo.Level;
import org.gxfj.iknow.pojo.User;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("commentDAO")
public class CommentDAOImpl implements CommentDAO{

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

    @Override
    public Integer getCount(Integer answerId){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Comment WHERE answerID = ?");
        return query.setInteger(0,answerId).list().size();
    }

    @Override
    public List<Comment> listByAnswerId(int answerId, int start, int length){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Comment WHERE answerID = ?");
        return query.setInteger(0,answerId).setFirstResult(start).setMaxResults(length).list();
    }
}
