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
    //按时间顺序排序,即先发的先显示
    private static Integer SORT_BY_TIME = 1;
    //按时间逆序排序
    private static Integer SORT_BY_TIME_REVERSE = 0;

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
    public List<Reply> listByCommentIdSort(Integer commentId, Integer start, Integer length, Integer sortType) {
        Session session = sessionFactory.getCurrentSession();
        Query query ;
        if(sortType.equals(SORT_BY_TIME)){
            query = session.createQuery("from Reply WHERE (commentID = ?) and (isDelete = 0) " +
                    "order by date ASC");
        }
        else if(sortType.equals(SORT_BY_TIME_REVERSE)){
            query = session.createQuery("from Reply WHERE (commentID = ?) and (isDelete = 0) " +
                    "order by date DESC");
        }
        else{
            query = session.createQuery("from Reply WHERE (commentID = ?) and (isDelete = 0) ");
        }
        return query.setInteger(0,commentId).setFirstResult(start).setMaxResults(length).list();
    }

    @Override
    public List<Reply> getAllRepliesSort(Integer commentId, Integer sortType){
        Session session = sessionFactory.getCurrentSession();
        Query query ;
        if(sortType.equals(SORT_BY_TIME)){
            query = session.createQuery("from Reply WHERE (commentID = ?) and (isDelete = 0) " +
                    "order by date ASC");
        }
        else if(sortType.equals(SORT_BY_TIME_REVERSE)){
            query = session.createQuery("from Reply WHERE (commentID = ?) and (isDelete = 0) " +
                    "order by date DESC");
        }
        else{
            query = session.createQuery("from Reply WHERE (commentID = ?) and (isDelete = 0) ");
        }
        return query.setInteger(0,commentId).list();
    }

    @Override
    public Integer getCount(Integer commentId){
        Session session = sessionFactory.getCurrentSession();
        String hql = "select count(r) from Reply as r WHERE (commentID = ?) and (isDelete = 0)";
        Query query = session.createQuery(hql);
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
