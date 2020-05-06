package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Answer;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("answerDAO")
public class AnswerDAOImpl implements AnswerDAO{
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
    public Integer add(Answer bean) {
        return (Integer) getHibernateTemplate().save(bean);
    }

    @Override
    public Answer get(Integer id) {
        return getHibernateTemplate().get(Answer.class,id);
    }


    @Override
    public void update(Answer bean) {
        getHibernateTemplate().update(bean);
    }

    @Override
    public List<Answer> getAnswersbyQid(Integer questionId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Answer WHERE questionID = ?");
        return query.setInteger(0,questionId).list();
    }

    @Override
    public List<Answer> listByQuestionId(int questionId, int start, int length) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Answer WHERE questionID = ?");
        return query.setInteger(0,questionId).setFirstResult(start).setMaxResults(length).list();
    }

    @Override
    public List<Answer> list(Integer start,Integer count) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Answer");
        return query.setFirstResult(start).setMaxResults(count).list();
    }

    @Override
    public Answer getNotDelete(Integer id) {
        Answer answer = null;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Answer WHERE (id = ?) and (isDelete = 0)");
        List<Answer> list = query.setInteger(0, id).list();
        if (!list.isEmpty()) {
            answer = list.get(0);
        }
        return answer;
    }
}
