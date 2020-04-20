package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Achievementrecord;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("achievementRecordDAO")
public class AchievementRecordDAOImpl implements AchievementRecordDAO{
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
    public void add(Achievementrecord bean) {
        getHibernateTemplate().save(bean);
    }

    @Override
    public Achievementrecord get(Integer id) {
        return getHibernateTemplate().get(Achievementrecord.class,id);
    }

    @Override
    public void update(Achievementrecord bean) {
        getHibernateTemplate().update(bean);
    }
}
