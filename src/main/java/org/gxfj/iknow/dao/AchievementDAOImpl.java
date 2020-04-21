package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Achievement;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("achievementDAO")
public class AchievementDAOImpl implements AchievementDAO{
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
    public void add(Achievement bean) {
        getHibernateTemplate().save(bean);
    }

    @Override
    public Achievement get(Integer id) {
        return getHibernateTemplate().get(Achievement.class,id);
    }

    @Override
    public void update(Achievement bean) {
        getHibernateTemplate().save(bean);
    }
}