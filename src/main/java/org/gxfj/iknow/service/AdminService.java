package org.gxfj.iknow.service;

import org.gxfj.iknow.pojo.Admin;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Map;

/**
 * @author hhj
 */
public interface AdminService {

    /**
     * 管理员登录
     * @param accountNum 管理员的账号
     * @param password
     * @return 管理员
     */
    Map<String, Object> login(Integer accountNum, String password);

    /**
     * 判断管理员是否登录
     * @return
     */
    Map<String, Object> isLogin();

    /**
     * 获取过去七天中每天的用户/问题总数
     * @param dateNow 当前日期
     * @param typeSum 数据类型
     * @return 数据
     */
    Map<String,Object> getSumData(String dateNow, Integer typeSum);

    /**
     * 获取过去length天，每天的用户活跃数
     * @param dateNow 当前日期
     * @param length 获取的天数
     * @return 数据
     */
    Map<String,Object> getDailyActiveUserData(String dateNow, Integer length);

    /**
     * 获取过去length月，每天的用户活跃数
     * @param dateNow 当前日期
     * @param length 获取的月数
     * @return 数据
     */
    Map<String,Object> getMonthlyActiveUserData(String dateNow, Integer length);

    /**
     * 获取各个门类下的问题数量
     * @return 数据
     */
    Map<String,Object> getQuestionTypeSumData();

    /**
     * 获取指定举报类别的举报信息
     * @param reportType 举报类型
     * @return 包含举报信息列表的map数据
     */
    Map<String,Object> getReportByType(Integer reportType);

    /**
     * 获取举报原因
     * @return 包含举报原因列表的map数据
     */
    Map<String,Object> getReportReason();

    /**
     * 获取用户信息
     * @return 包含用户信息列表的map数据
     */
    Map<String,Object> getUserInfo(Integer userId);

    /**
     * 获得被举报的问题的信息
     * @param questionId 问题id
     * @return 包含举报的问题的信息的map数据
     */
    Map<String, Object> getReportedQuestion(Integer questionId);

    /**
     * 获取对应回答信息列表
     * @param typeId 回答举报对应的信息id
     * @param type 类型（0为回答，1为评论，2为回复）
     * @return 包含用户信息列表的map数据
     */
    Map<String,Object> getAnswerReported(Integer typeId, Integer type);

    /**
     * 删除对应举报信息
     * @param reportId 举报信息id
     * @return 数据
     */
    Map<String, Object> deleteReport(Integer reportId);

    /**
     * 封禁用户
     * @param userID 要封禁的用户id
     * @param days 封禁的时间
     * @return 返回的数据
     */
    Map<String, Object> ban(Integer userID, Integer days);

    /**
     * 禁言用户
     * @param userID 要禁言的用户id
     * @param days 禁言的时间
     * @return 返回的数据
     */
    Map<String, Object> estoppel(Integer userID, Integer days);


    /**
     * 删除问题
     * @param questionID 要删除的问题
     * @return
     */
    Map<String, Object> questionDel(Integer questionID);

    /**
     * 删除问题
     * @param answerID 要删除的问题
     * @return
     */
    Map<String, Object> answerDel(Integer answerID);

    /**
     * 删除评论
     * @param commentID 要删除的评论
     * @return
     */
    Map<String, Object> commentDel(Integer commentID);

    /**
     * 删除回复
     * @param replyID 要删除的回复
     * @return
     */
    Map<String, Object> replyDel(Integer replyID);

    /**
     * 删掉所有问题举报记录
     * @return 包含响应码的map数据
     */
    Map<String, Object> deleteAllQueReport();

    /**
     * 删掉所有回答举报记录
     * @return 包含响应码的map数据
     */
    Map<String, Object> deleteAllAnsReport();

    /**
     * 删掉所有评论举报记录
     * @return 包含响应码的map数据
     */
    Map<String, Object> deleteAllComReport();

    /**
     * 删掉所有回复举报记录
     * @return 包含响应码的map数据
     */
    Map<String, Object> deleteAllRepReport();

    /**
     * 存入学校
     * @param schoolName 学校名
     * @return 包含学校Id的返回信息
     */
    Map<String, Object> saveSchool(String schoolName);

    /**
     * 存入学生、学院、专业信息
     * @param studentsInfo 学生信息
     * @param schoolId 学校Id
     * @return 学院，专业的返回信息
     */
    Map<String, Object> saveStudents(String studentsInfo, Integer schoolId);

    /**
     * 存入学生信息
     * @param studentInfo 学生信息
     * @return 包含响应码的map数据
     */
    Map<String, Object> saveStudent(String studentInfo);

    /**
     * 存入教师信息
     * @param teachersInfo 字符串形式的json数组多个教师信息
     * @param schoolID 学校id
     * @return 包含响应码的map数据
     */
    Map<String, Object> saveTeachers(String teachersInfo, Integer schoolID);

    /**
     * 存入教师信息
     * @param teacherNO 教师工号
     * @param name 姓名
     * @param schoolId 学校id
     * @param colloge 学院名
     * @return 包含响应码的map数据
     */
    Map<String, Object> saveTeacher(String teacherNO, String name, Integer schoolId, String colloge);
}
