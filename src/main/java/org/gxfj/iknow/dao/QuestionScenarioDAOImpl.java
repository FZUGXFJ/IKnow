package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Questionscenario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("questionScenarioDAO")
public class QuestionScenarioDAOImpl implements QuestionScenarioDAO{
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
    public Integer add(Questionscenario bean) {
        return (Integer)getHibernateTemplate().save(bean);
    }

    @Override
    public Questionscenario get(Integer id) {
        return getHibernateTemplate().get(Questionscenario.class,id);
    }

    @Override
    public void update(Questionscenario bean) {
        getHibernateTemplate().update(bean);
    }
}
