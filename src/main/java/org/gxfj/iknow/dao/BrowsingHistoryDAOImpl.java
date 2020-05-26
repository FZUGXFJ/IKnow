package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Browsinghistory;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository("browsingHistoryDAO")
public class BrowsingHistoryDAOImpl implements BrowsingHistoryDAO{
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
    public Integer add(Browsinghistory bean) {
        return (Integer)getHibernateTemplate().save(bean);
    }

    @Override
    public Browsinghistory get(Integer id) {
        return getHibernateTemplate().get(Browsinghistory.class,id);
    }

    @Override
    public void update(Browsinghistory bean) {
        getHibernateTemplate().update(bean);
    }

    @Override
    public Integer getBrowsingCount(Integer questionId){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select count(b) from Browsinghistory as b WHERE questionID = ?");
        return ((Long)query.setInteger(0,questionId).uniqueResult()).intValue();
    }

    @Override
    public List<Browsinghistory> listInLastDay(Integer day) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -day);
        String hql = "FROM Browsinghistory as b WHERE b.date >= ?";
        Query query = getSession().createQuery(hql);
//        System.out.println(simpleDateFormat.format(calendar.getTime()));
        return query.setParameter(0, calendar.getTime()).list();
    }

    @Override
    public List<Browsinghistory> getBrowsingHistoryByUserId(Integer userId, Integer start) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from BrowsingHistory as b WHERE" +
                " userID = :userId order by date desc");
        return (List)query.setParameter("userId",userId).setFirstResult(start).setMaxResults(20).list();
    }
}
