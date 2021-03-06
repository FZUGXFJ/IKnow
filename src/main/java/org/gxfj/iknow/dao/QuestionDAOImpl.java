package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Answer;
import org.gxfj.iknow.pojo.Comment;
import org.gxfj.iknow.pojo.Question;
import org.gxfj.iknow.pojo.Questionstate;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Repository("questionDAO")
public class QuestionDAOImpl implements QuestionDAO {

    private HibernateTemplate ht = null;

    static final int SOLVED = 1;
    static final int UNSOLVED = 2;
    static final int DELETED = 1;

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
    public Question get(Integer id) {
        return getHibernateTemplate().get(Question.class,id);
    }

    @Override
    public void update(Question bean) {
        getHibernateTemplate().merge(bean);
    }

    @Override
    public Integer add(Question bean) {
        return (Integer)getHibernateTemplate().save(bean);
    }

    @Override
    public void delete(Question bean) {
        bean.setIsDelete((byte)DELETED);
        update(bean);
    }

    @Override
    public Integer getQuestionStateId(Integer questionId){
        Integer stateId=getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session) throws HibernateException {
                SQLQuery sqlQuery=session.createSQLQuery("select stateID from question where id="+ questionId);
                return (Integer)sqlQuery.uniqueResult();
            }
        });
        if (stateId == SOLVED) {
            return 0;
        } else if(stateId == UNSOLVED) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public Integer getAnswerCount(Integer id) {
        String sql = "SELECT count(*) from Answer WHERE ( questionId= " + id + " ) and (isDelete = 0)";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        return ((Long) query.uniqueResult()).intValue();
    }

    @Override
    public Question getNotDelete(Integer id) {
        Question question = null;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Question WHERE (id = ?) and (isDelete = 0)");
        return (Question)query.setInteger(0, id).uniqueResult();
    }

    @Override
    public List<Question> listPartByUserId(Integer userId, Integer start, Integer length){
        List<Question> questions = null;
        String hql = "from Question as q WHERE ( userID= " + userId + " ) and (q.isDelete = 0) order by date desc";
        //Session session = sessionFactory.openSession();
        Query query = getSession().createQuery(hql);
        //Query query = session.createQuery(hql);
        questions = query.setFirstResult(start).setMaxResults(length).list();
        //session.close();
        return questions;
    }

    @Override
    public List<Question> listPartByUserIdnoAn(Integer userId, Integer start, Integer length) {
        List<Question> questions = null;
        String hql = "from Question as q WHERE ( userID= " + userId + " ) and (q.isDelete = 0) and " +
                "(q.isAnonymous = 0) order by date desc";
        //Session session = sessionFactory.openSession();
        Query query = getSession().createQuery(hql);
        //Query query = session.createQuery(hql);
        questions = query.setFirstResult(start).setMaxResults(length).list();
        //session.close();
        return questions;
    }

    @Override
    public List<Question> listPartByUserId(Integer userId) {
        Query query = getSession().createQuery("from Question as q WHERE ( userID= " + userId + " ) " +
                "order by q.id desc");
        List<Question> questions = questions = query.list();
        return questions;
    }

    @Override
    public List<Question> listPartbyUseridNodelete(Integer userId) {
        Query query = getSession().createQuery("from Question as q WHERE ( userID= " +
                userId + " ) and ( isDelete = 0) order by date desc");
        List<Question> questions = questions = query.list();
        return questions;
    }

    @Override
    public Integer getUserQuestionCount(Integer userId) {
        Query query = getSession().createQuery("select count(q) from Question as q WHERE" +
                "(userID = "+ userId +") and (q.isDelete = 0) ");
        return ((Long)query.uniqueResult()).intValue();
    }

    @Override
    public List<Question> listByQuestionType(Integer questionTypeId, Integer start, Integer count) {
        String hql = "FROM Question WHERE (typeId = ?) and (isDelete = 0)";
        Query query = getSession().createQuery(hql);
        return query.setInteger(0,questionTypeId).setFirstResult(start).setMaxResults(count).list();
    }

    @Override
    public List<Question> listByKeyword(String keyword) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Question as q WHERE ( title like '%" + keyword + "%' ) and (q.isDelete = 0)" +
                "or ( contentText like '%" + keyword + "%' ) " +
                "and (q.isDelete = 0)";
        Query query = session.createQuery(hql);
        return query.setMaxResults(20).list();
    }
}
