package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Message;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("messageDAO")
public class MessageDAOImpl implements MessageDAO{
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
    public Integer add(Message bean) {
        return (Integer)getHibernateTemplate().save(bean);
    }

    @Override
    public Message get(Integer id) {
        return getHibernateTemplate().get(Message.class,id);
    }

    @Override
    public void update(Message bean) {
        getHibernateTemplate().update(bean);
    }

    @Override
    public List<Message> listByUserId(Integer userId) {
        Query query = getSession().createQuery("from Message as m WHERE ( userID= " + userId + " ) and " +
                "(isRead = 0) order by date desc");
        List<Message> messages = query.list();
        return messages;
    }
}
