package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Achievement;
import org.gxfj.iknow.pojo.Achievementrecord;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public Integer add(Achievementrecord bean) {
        return (Integer)getHibernateTemplate().save(bean);
    }

    @Override
    public Achievementrecord get(Integer id) {
        return getHibernateTemplate().get(Achievementrecord.class,id);
    }

    @Override
    public void update(Achievementrecord bean) {
        getHibernateTemplate().update(bean);
    }

    @Override
    public List<Achievement> listAchievementsByUserId(Integer userId, Integer start) {
        Query query = getSession().createQuery("from Achievementrecord as a WHERE" +
                " userID = :userId");
        return (List)query.setParameter("userId",userId).setFirstResult(start).list();
    }
}
