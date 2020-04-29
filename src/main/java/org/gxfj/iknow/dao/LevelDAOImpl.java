package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Answer;
import org.gxfj.iknow.pojo.Level;
import org.hibernate.*;
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

    @Override
    public Level get(Integer id) {
        return getHibernateTemplate().get(Level.class,id);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Integer getLevelByExp(Integer exp) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select l.level from Level l WHERE" +
                " l.expBotLimit <= :exp and expTopLimit >=:exp");
        Integer level = (Integer)query.setParameter("exp",exp).uniqueResult();
        if(level == null){
            return 0;
        }else{
            return level;
        }
    }
}
