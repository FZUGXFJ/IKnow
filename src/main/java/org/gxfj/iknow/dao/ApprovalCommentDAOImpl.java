package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Approvalcomment;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("approvalCommentDAO")
public class ApprovalCommentDAOImpl implements ApprovalCommentDAO{
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
    public void add(Approvalcomment bean) {
        getHibernateTemplate().save(bean);
    }

    @Override
    public Approvalcomment get(Integer id) {
        return getHibernateTemplate().get(Approvalcomment.class,id);
    }

    @Override
    public void update(Approvalcomment bean) {
        getHibernateTemplate().update(bean);
    }

    @Override
    public Integer getCount(Integer commentId){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Approvalcomment WHERE commentID = ?");
        return query.setInteger(0,commentId).list().size();
    }
}
