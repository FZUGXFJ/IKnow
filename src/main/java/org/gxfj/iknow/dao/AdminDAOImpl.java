package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Admin;
import org.gxfj.iknow.util.Time;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public List<Integer> getQuestionSum(String date) {
        Session session = getSession();
        List<Integer> nums = new ArrayList<>();
        //获取每天的发表问题数，记录数列表从七天前开始计数
        for(int i = 7;i >= 1;i--){
            String sql = "select count(*) from question where DateDiff('"+date+"',date) >= "+i;
            Query query = session.createSQLQuery(sql);
            Integer num = ((Number)query.uniqueResult()).intValue();
            nums.add(num);
        }
        return nums;
    }

    @Override
    public List<Integer> getUserSum(String date) {
        Session session = getSession();
        List<Integer> nums = new ArrayList<>();
        for(int i = 7;i >=1;i--){
            String sql = "select count(*) from user where DateDiff('"+date+"',date) >= "+i;
            Query query = session.createSQLQuery(sql);
            Integer num = ((Number)query.uniqueResult()).intValue();
            nums.add(num);
        }
        return nums;
    }
}
