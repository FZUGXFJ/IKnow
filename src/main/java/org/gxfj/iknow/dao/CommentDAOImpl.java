package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Answer;
import org.gxfj.iknow.pojo.Comment;
import org.gxfj.iknow.pojo.Level;
import org.gxfj.iknow.pojo.User;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
        List<Comment> list=getHibernateTemplate().execute(new HibernateCallback<List<Comment>>() {
            @Override
            public List<Comment> doInHibernate(Session session) throws HibernateException {
                SQLQuery sqlQuery=session.createSQLQuery("select * from comment where " +
                        "answerID = " +answerId).addEntity(Comment.class);
                return sqlQuery.list();
            }
        });
        if (list.isEmpty()) {
            return 0;
        } else {
            return list.size();
        }
    }
}
