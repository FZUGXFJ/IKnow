package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Useridentity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userIdentityDAO")
public class UserIdentityDAOImpl implements UserIdentityDAO{
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
    public Integer add(Useridentity bean) {
        return (Integer)getHibernateTemplate().save(bean);
    }

    @Override
    public Useridentity get(Integer id) {
        return getHibernateTemplate().get(Useridentity.class,id);
    }

    @Override
    public void update(Useridentity bean) {
        getHibernateTemplate().update(bean);
    }

    @Override
    public List<Useridentity> listUserIdentitiesByUserId(Integer userId) {
        Query query = getSession().createQuery("from Useridentity as ui where ( userID= " + userId + " )");
        List<Useridentity> useridentities = query.list();
        return useridentities;
    }

    @Override
    public Useridentity getStuIdentitie (Integer schoolId, String realName, String studentNum) {
        String hql = "select ui from Useridentity as ui where (schoolID ="+schoolId+")" +
                " and (name ='"+realName+"') and (studentNum ="+studentNum+")";
        Query query = getSession().createQuery(hql);
        return (Useridentity)query.uniqueResult();
    }

    @Override
    public Useridentity getTeaIdentitie (Integer schoolId, String realName, String jobNum) {
        String hql = "select ui from Useridentity as ui where (schoolID ="+schoolId+")" +
                " and (name ='"+realName+"') and (jobNum ="+jobNum+")";
        Query query = getSession().createQuery(hql);
        return (Useridentity)query.uniqueResult();
    }

    @Override
    public Useridentity getStudentIdentity(Integer schoolId, Integer studentNum) {
        String hql = "select ui from Useridentity as ui where (schoolID ="+schoolId+") and (studentNum ="+studentNum+")";
        Query query = getSession().createQuery(hql);
        return (Useridentity)query.uniqueResult();
    }

    @Override
    public Useridentity getTeacherIdentity(Integer schoolId, Integer jobNum) {
        String hql = "select ui from Useridentity as ui where (schoolID ="+schoolId+") and (jobNum ="+jobNum+")";
        Query query = getSession().createQuery(hql);
        return (Useridentity)query.uniqueResult();
    }

    @Override
    public Integer getCollegeTeaNum(Integer collegeId) {
        String hql = "select count(ui) from Useridentity as ui where (collegeID ="+collegeId+") and (type = '教师')";
        Query query = getSession().createQuery(hql);
        return ((Long)query.uniqueResult()).intValue();
    }

    @Override
    public Integer getCollegeStuNum(Integer collegeId) {
        String hql = "select count(ui) from Useridentity as ui where (collegeID ="+collegeId+") and (type = '学生')";
        Query query = getSession().createQuery(hql);
        return ((Long)query.uniqueResult()).intValue();
    }

    @Override
    public Integer getMajorTeaNum(Integer majorId) {
        String hql = "select count(ui) from Useridentity as ui where (majorID ="+majorId+") and (type = '教师')";
        Query query = getSession().createQuery(hql);
        return ((Long)query.uniqueResult()).intValue();
    }

    @Override
    public Integer getMajorStuNum(Integer majorId) {
        String hql = "select count(ui) from Useridentity as ui where (majorID ="+majorId+") and (type = '学生')";
        Query query = getSession().createQuery(hql);
        return ((Long)query.uniqueResult()).intValue();
    }
}
