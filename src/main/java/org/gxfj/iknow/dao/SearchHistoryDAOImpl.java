package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Searchhistory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("searchHistoryDAO ")
public class SearchHistoryDAOImpl implements SearchHistoryDAO{
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
    public Integer add(Searchhistory bean) {
        return (Integer)getHibernateTemplate().save(bean);
    }

    @Override
    public Searchhistory get(Integer id) {
        return getHibernateTemplate().get(Searchhistory.class,id);
    }

    @Override
    public void update(Searchhistory bean) {
        getHibernateTemplate().update(bean);
    }

    //获取热门搜索记录
    public List<String> getHotSearch() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "select content from Searchhistory as s group by content order by count(s) desc";
        Query query = session.createQuery(hql);
        query.setMaxResults(10);
        return query.list();
    }
}
