package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Answer;
import org.gxfj.iknow.pojo.Level;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("LevelDAO")
public class LevelDAOImpl implements LevelDAO {

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

    public Level get(Integer id) {
        return getHibernateTemplate().get(Level.class,id);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Integer getLevelByExp(Integer exp) {
        List<Level> list=getHibernateTemplate().execute(new HibernateCallback<List<Level>>() {
            @Override
            public List<Level> doInHibernate(Session session) throws HibernateException {
                SQLQuery sqlQuery=session.createSQLQuery("select * from level where " +
                        "expBotLimit <= "+exp+" and expTopLimit >="+exp).addEntity(Level.class);
                return sqlQuery.list();
            }
        });
        if (list.isEmpty()) {
            return 0;
        } else {
            return list.get(0).getLevel();
        }
    }
}
