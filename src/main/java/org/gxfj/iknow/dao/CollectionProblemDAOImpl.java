package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Approvalreply;
import org.gxfj.iknow.pojo.Browsinghistory;
import org.gxfj.iknow.pojo.Collectionproblem;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("collectionProblemDAO")
public class CollectionProblemDAOImpl implements CollectionProblemDAO{

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
    public Integer add(Collectionproblem bean) {
        return (Integer)getHibernateTemplate().save(bean);
    }

    @Override
    public Collectionproblem get(Integer id) {
        return getHibernateTemplate().get(Collectionproblem.class,id);
    }

    @Override
    public void update(Collectionproblem bean) {
        getHibernateTemplate().update(bean);
    }

    @Override
    public void delete(Collectionproblem bean) {
        getHibernateTemplate().delete(bean);
    }

    @Override
    public Integer getCollectionCount(Integer questionId){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(c) from Collectionproblem as c WHERE questionID = ?");
        return ((Long)query.setInteger(0,questionId).uniqueResult()).intValue();
    }

    @Override
    public Collectionproblem getCollectionQuestion(Integer userId,Integer questionId){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Collectionproblem as c WHERE" +
                " userID = :userId and questionID = :questionId");
        return (Collectionproblem)query.setParameter("userId",userId)
                .setParameter("questionId",questionId).uniqueResult();
    }

    @Override
    public List<Collectionproblem> getCollectionQuestionByUserId(Integer userId, Integer start) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Collectionproblem as c WHERE" +
                " userID = :userId");
        return (List)query.setParameter("userId",userId).setFirstResult(start).setMaxResults(20).list();
    }

    @Override
    public Integer getUserCollectCount(Integer userId) {
        Query query = getSession().createQuery("select count(c) from Collectionproblem as c WHERE "+
                "(userID = "+ userId +")");
        return ((Long)query.uniqueResult()).intValue();
    }
}
