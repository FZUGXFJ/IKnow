package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Approvalreply;
import org.gxfj.iknow.pojo.Reply;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
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
    public Integer add(Approvalreply bean) {
        return (Integer)getHibernateTemplate().save(bean);
    }

    @Override
    public Approvalreply get(Integer id) {
        return getHibernateTemplate().get(Approvalreply.class,id);
    }

    @Override
    public void update(Approvalreply bean) {
        getHibernateTemplate().update(bean);
    }

    @Override
    public Integer searchByUserIdandReplyId(Integer uid, Integer rid) {
        Integer replyId=getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session) throws HibernateException {
                SQLQuery sqlQuery=session.createSQLQuery(
                        "SELECT id from Approvalreply WHERE userID=" + uid + "&&replyID="+rid);
                return (Integer)sqlQuery.uniqueResult();
            }
        });
        if (replyId==null){
            return -1;
        }
        return replyId;
    }

    @Override
    public void delete(Approvalreply bean) {
        getHibernateTemplate().delete(bean);
    }
}
