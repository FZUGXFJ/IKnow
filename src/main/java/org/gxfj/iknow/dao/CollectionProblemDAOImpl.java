package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Browsinghistory;
import org.gxfj.iknow.pojo.Collectionproblem;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
    public void add(Collectionproblem bean) {
        getHibernateTemplate().save(bean);
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
    public Integer getCollectionCount(Integer questionID){
        List<Collectionproblem> list=getHibernateTemplate().execute(new HibernateCallback<List<Collectionproblem>>() {
            @Override
            public List<Collectionproblem> doInHibernate(Session session) throws HibernateException {
                SQLQuery sqlQuery=session.createSQLQuery("select * from collectionproblem where " +
                        "questionID="+questionID).addEntity(Collectionproblem.class);
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
