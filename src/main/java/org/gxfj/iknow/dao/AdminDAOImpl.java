package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Admin;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("adminDAO")
public class AdminDAOImpl implements AdminDAO{
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
    public Integer add(Admin bean) {
        return (Integer)getHibernateTemplate().save(bean);
    }

    @Override
    public Admin get(Integer id) {
        return getHibernateTemplate().get(Admin.class,id);
    }

    @Override
    public void update(Admin bean) {
        getHibernateTemplate().update(bean);
    }

    @Override
    public Admin getAdminByCount(Integer account) {
        Admin admin = new Admin();
        admin.setAccount(account);
        List<Admin> adminList = getHibernateTemplate().findByExample(admin);
        if(adminList.isEmpty()){
            return null;
        }
        else{
            return adminList.get(0);
        }
    }
}
