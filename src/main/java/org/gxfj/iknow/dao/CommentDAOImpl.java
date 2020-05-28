package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Answer;
import org.gxfj.iknow.pojo.Comment;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Integer add(Comment bean) {
        return (Integer)getHibernateTemplate().save(bean);
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
        String hql = "select count(c) from Comment as c WHERE (answerID = ?) and (isDelete = 0)";
        Query query = session.createQuery(hql);
        return ((Long)query.setInteger(0,answerId).uniqueResult()).intValue();
    }

    @Override
    public List<Comment> listByAnswerId(int answerId, int start, int length){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Comment WHERE (answerID = ?) and (isDelete = 0)");
        return query.setInteger(0,answerId).setFirstResult(start).setMaxResults(length).list();
    }

    @Override
    public List<Comment> getCommentsByAnswerId(Integer answerId){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Comment WHERE answerID = ?");
        return query.setInteger(0,answerId).list();
    }

    @Override
    public Comment getNotDelete(Integer id) {
        Comment comment = null;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Comment WHERE (id = ?) and (isDelete = 0)");
        List<Comment> list = query.setInteger(0, id).list();
        if (!list.isEmpty()) {
            comment = list.get(0);
        }
        return comment;
    }

    @Override
    public List<Comment> listByAnswerIdSort(int answerId, int start, int length, Integer sort) {
        Session session = sessionFactory.openSession();
        Query query ;
        if(sort==0){
            query=session.createQuery("from Comment WHERE (answerID = ?) and (isDelete = 0)" +
                    " order by date asc");
        }
        else {
            query=session.createQuery("from Comment WHERE (answerID = ?) and (isDelete = 0)" +
                    " order by date desc");
        }
        return query.setInteger(0,answerId).setFirstResult(start).setMaxResults(length).list();
    }
}
