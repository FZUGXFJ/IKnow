package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.pojo.Useridentity;

import java.util.List;

public interface UserIdentityDAO extends BaseDAO<Useridentity>{

    /**
     * 获得用户的所有身份
     * @param userId 用户id
     * @return 用户身份列表，没有返回null
     */
    public List<Useridentity> listUserIdentitiesByUserId (Integer userId);

    /**
     * 获得满足身份的学生信息
     * @param schoolId 学校id
     * @param realName 用户姓名
     * @param studentNum 用户学号
     * @return 用户身份，没有返回null
     */
    public Useridentity getStuIdentitie (Integer schoolId, String realName, String studentNum);

    /**
     * 获得满足身份的教师信息
     * @param schoolId 学校id
     * @param realName 用户姓名
     * @param jobNum 用户工号
     * @return 用户身份，没有返回null
     */
    public Useridentity getTeaIdentitie (Integer schoolId, String realName, String jobNum);

    /**
     * 获得指定学校，指定学号的学生
     * @param schoolId 学校Id
     * @param studentNum 学号
     * @return 用户身份
     */
    public Useridentity getStudentIdentity(Integer schoolId, Integer studentNum);

    /**
     * 获得指定学校，指定工号的教师
     * @param schoolId 学校Id
     * @param jobNum 工号
     * @return 用户身份
     */
    public Useridentity getTeacherIdentity(Integer schoolId, Integer jobNum);

    /**
     * 获得学院教师数量
     * @param collegeId 学院Id
     * @return 教师数量
     */
    public Integer getCollegeTeaNum(Integer collegeId);

    /**
     * 获得学院学生数量
     * @param collegeId 学院Id
     * @return 学生数量
     */
    public Integer getCollegeStuNum(Integer collegeId);

    /**
     * 获得专业教师数量
     * @param majorId 专业Id
     * @return 教师数量
     */
    public Integer getMajorTeaNum(Integer majorId);

    /**
     * 获得专业学生数量
     * @param majorId 专业Id
     * @return 学生数量
     */
    public Integer getMajorStuNum(Integer majorId);

    /**
     * 获得指定专业的学生
     * @param majorId 专业Id
     * @return 学生身份列表
     */
    public List<Useridentity> listStuIdentity(Integer majorId);

    /**
     * 获得指定学院的教师
     * @param collegeId 专业Id
     * @return 教师身份列表
     */
    public List<Useridentity> listTeaIdentity(Integer collegeId);

    /**
     * 删除学生认证信息
     * @param schoolId 学校Id
     * @param studentNum 学号
     * @return 是否删除成功
     */
    public  boolean deleteStudent(Integer schoolId, String studentNum);

    /**
     * 删除教师认证信息
     * @param schoolId 学校Id
     * @param jobNum 工号
     * @return 是否删除成功
     */
    public boolean deleteTeacher(Integer schoolId, String jobNum);
}
