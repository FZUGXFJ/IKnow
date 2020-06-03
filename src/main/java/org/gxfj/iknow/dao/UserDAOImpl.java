package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Level;
import org.gxfj.iknow.pojo.Question;
import org.gxfj.iknow.pojo.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {

    private final static int ACTIVATE_STATE = 1;
    private final static int FORBIDDEN_WORD_STATE = 3;
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
    public Integer add(User bean) {
        return (Integer)getHibernateTemplate().save(bean);
    }

    @Override
    public User get(Integer id) {
        return getHibernateTemplate().get(User.class,id);
    }

    @Override
    public void update(User bean) {
        getHibernateTemplate().merge(bean);
    }

    @Override
    public User getUserByUsername(String username) {
        User example = new User();
        example.setName(username);
        List<User> list = getHibernateTemplate().findByExample(example);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public User getUserByEmail(String email) {
        User example = new User();
        example.setEmail(email);
        List<User> list = getHibernateTemplate().findByExample(example);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public boolean hasUserId(Integer id) {
        User user = new User();
        user.setId(id);
        return getHibernateTemplate().findByExample(user).size() != 0;
    }

    @Override
    public boolean hasUsername(String username) {
        User user = new User();
        user.setName(username);
        return getHibernateTemplate().findByExample(user).size() != 0;
    }

    @Override
    public boolean hasUserEmail(String email) {
        User user = new User();
        user.setEmail(email);
        return getHibernateTemplate().findByExample(user).size() != 0;
    }

    @Override
    public List<Integer> getActiveUserId() {
        String hql = "SELECT u.id FROM User AS u WHERE (stateID = ?) or (stateID = ?)";
        Query query = getSession().createQuery(hql);

        return query.setInteger(0,ACTIVATE_STATE).setInteger(1,FORBIDDEN_WORD_STATE).list();
    }

    @Override
    public List<Integer> getAllUserId() {
        String hql = "SELECT u.id FROM User AS u";
        Query query = getSession().createQuery(hql);

        return query.list();
    }

    @Override
    public List<User> listByKeyword(String keyword) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM User WHERE ( name like '%" + keyword + "%' )";
        Query query = session.createQuery(hql);
        return query.setMaxResults(20).list();
    }
}
