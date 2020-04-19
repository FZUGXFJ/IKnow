package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo_tmp.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public User get(Integer id) {
        Session session=sessionFactory.getCurrentSession();
        User user=(User)session.get(User.class,id);
        return user;
    }

    @Override
    public boolean update(User user) {
        Session session=sessionFactory.getCurrentSession();
        session.update(user);
        return true;
    }

    @Override
    public boolean add(User user) {
        Session session=sessionFactory.getCurrentSession();
        session.save(user);
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}
