package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Question;
import org.gxfj.iknow.pojo.Reply;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public Integer add(Reply bean) {
        return (Integer)getHibernateTemplate().save(bean);
    }

    @Override
    public Reply get(Integer id) {
        return getHibernateTemplate().get(Reply.class,id);
    }

    @Override
    public void update(Reply bean) {
        getHibernateTemplate().update(bean);

    }

    @Override
    public List<Reply> listByCommentId(Integer commentId, Integer start, Integer count) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Reply WHERE commentID = ?");
        return query.setInteger(0,commentId).setFirstResult(start).setMaxResults(count).list();
    }

    @Override
    public List<Reply> getAllReplies(Integer commentId){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Reply WHERE commentID = ?");
        return query.setInteger(0,commentId).list();
    }

    @Override
    public Integer getCount(Integer commentId){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(r) from Reply as r WHERE commentID = ?");
        return ((Long)query.setInteger(0,commentId).uniqueResult()).intValue();
    }

    @Override
    public Reply getNotDelete(Integer id) {
        Reply reply = null;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Reply WHERE (id = ?) and (isDelete = 0)");
        List<Reply> list = query.setInteger(0, id).list();
        if (!list.isEmpty()) {
            reply = list.get(0);
        }
        return reply;
    }
}
