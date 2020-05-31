package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Answer;
import org.gxfj.iknow.pojo.Question;
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
        Query query = session.createQuery("from Answer WHERE (questionID = ?) and (isDelete = 0)");
        return query.setInteger(0,questionId).list();
    }

    @Override
    public List<Answer> listByQuestionId(int questionId, int start, int length) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Answer WHERE (questionID = ?) and (isDelete = 0)");
        return query.setInteger(0,questionId).setFirstResult(start).setMaxResults(length).list();
    }

    @Override
    public List<Answer> listNoDelete(Integer start, Integer count) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Answer where isDelete = 0");
        return query.setFirstResult(start).setMaxResults(count).list();
    }

    @Override
    public List<Answer> listNoDelete(Integer count) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Answer where isDelete = 0 ORDER BY date DESC");
        return query.setMaxResults(count).list();
    }

    @Override
    public List<Answer> listNoDelete() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Answer where isDelete = 0");
        return query.list();
    }

    @Override
    public List<Answer> list() {
        Query query = getSession().createQuery("FROM Answer");
        return query.list();
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

    @Override
    public List<Answer> listPartByUserId(Integer userId, Integer start, Integer length){
        List<Answer> answers = null;
        String hql = "from Answer as a WHERE ( userID= " + userId + " ) and (a.isDelete = 0) order by a.id desc";
        //Session session = sessionFactory.openSession();
        Query query = getSession().createQuery(hql);
        //Query query = session.createQuery(hql);
        answers = query.setFirstResult(start).setMaxResults(length).list();
        //session.close();
        return answers;
    }

    @Override
    public List<Answer> listPartByUserId(Integer userId) {
        Query query = getSession().createQuery("from Answer as a WHERE ( userID= " + userId + " )" +
                " order by a.id desc");
        List<Answer> answers = query.list();
        return answers;
    }

    @Override
    public List<Answer> listByQuestionIdSort(int questionId, int start, int length, int sort) {
        Session session=sessionFactory.openSession();
        Query query;
        if (sort==0){
            query = session.createQuery("from Answer WHERE (questionID = ?) and (isDelete = 0) " +
                    "order by approvalCount DESC");
        }
        else {
            query = session.createQuery("from Answer WHERE (questionID = ?) and (isDelete = 0) " +
                    "order by date DESC");
        }
        return query.setInteger(0,questionId).setFirstResult(start).setMaxResults(length).list();
    }

    @Override
    public void delete(Answer answer){
        answer.setIsDelete((byte)1);
        update(answer);
    }

    @Override
    public Integer getUserAnswersCount(Integer userId) {
        Query query = getSession().createQuery("select count(a) from Answer as a WHERE " +
                "( userID= " + userId + " ) ");
        return ((Long)query.uniqueResult()).intValue();
    }

    @Override
    public List<Answer> listByKeyword(String keyword) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Answer as a WHERE ( content like '%" + keyword + "%' ) and (a.isDelete = 0)";
        Query query = session.createQuery(hql);
        return query.setMaxResults(20).list();
    }
}
