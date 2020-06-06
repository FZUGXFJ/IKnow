package org.gxfj.iknow.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.service.AdminService;
import org.gxfj.iknow.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hhj
 */
@Controller
public class AdminAction {
    private Integer accountNum;
    private String password;
    private String dateNow;
    private Integer typeSum;
    private Integer reportType;
    private Integer userID;
    private Integer typeID;
    private Integer type;
    private Integer reportID;
    private Integer days;
    private Integer questionID;
    private Integer answerID;
    private Integer commentID;
    private Integer replyID;
    private String schoolName;
    private String studentsInfo;
    private Integer schoolID;
    //字符串形式的json数据
    private String studentInfo;
    private String teachersInfo;

    private String teacherNO;
    private String name;
    //存教师接口的教师id
    private Integer school;
    private String college;

    @Autowired
    private AdminService adminService;

    private InputStream inputStream;
   /* private final String SUCCESS = "success";
    private final Integer UNLOGIN = 1;
    private static Integer HASH_MAP_NUM = 20;
    private final static int MIN_HASH_MAP_NUM = 10;
    private final static String LOGIN_ADMIN_SESSION_NAME = "admin";
    private final static String NO_ADMIN = null;
    private final static String RESULT_CODE = "resultCode";
    private final static int SUCCESSLOGIN = 0;*/

    /**
     * 管理员登出
     */
    public String logout(){
        Map<String,Object> session = ActionContext.getContext().getSession();
        if(!session.isEmpty()){
            session.clear();
        }
        inputStream = new ByteArrayInputStream("{\"response\":0}".getBytes(StandardCharsets.UTF_8));
        return  ConstantUtil.RETURN_STRING;
    }

    /**
     * 管理员登录
     */
    public String login(){

        Map<String, Object> result = adminService.login(accountNum, password);

        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 统计过去7天每天用户/回答的总数
     */
    public String statistics(){
        Map<String,Object> result = adminService.getSumData(dateNow,typeSum);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 判断管理员是否登录
     */
    public String isLogin() {
        Map<String, Object> result = adminService.isLogin();
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        System.out.println(JSON.toJSONString(result));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 获取活跃的用户，有两者结果
     */
    public String active() {
        Map<String,Object> result = null;
        if (typeSum == ConstantUtil.DAILY_ACTIVE_USER_STATICS_TYPE_CODE) {
            result = adminService.getDailyActiveUserData(dateNow, ConstantUtil.DAILY_ACTIVE_USER_STATICS_LENGTH);
        } else if (typeSum == ConstantUtil.MONTHLY_ACTIVATE_USER_STATICS_TYPE_CODE) {
            result = adminService.getMonthlyActiveUserData(dateNow, ConstantUtil.MONTHLY_ACTIVATE_USER_STATICS_LENGTH);
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 获得各个门类的问题总数
     */
    public String questionTypeSum(){
        Map<String,Object> result = adminService.getQuestionTypeSumData();
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 获得reportType对应的类型的举报数据
     * reportType：举报类别（0为问题举报，1为回答举报，2为评论举报，3为回复举报）
     */
    public String reportType(){
        Map<String,Object> result = adminService.getReportByType(reportType);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 获得数据库中所有的举报原因
     */
    public String reportReason(){
        Map<String,Object> result = adminService.getReportReason();
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 根据用户id获得用户的相关信息
     * userID：用户id
     */
    public String userInfo(){
        Map<String,Object> result = adminService.getUserInfo(userID);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 获得问题的举报记录
     * typeID：问题举报对应的信息id
     * @return “SUCCESS”
     */
    public String questionReported(){
        Map<String,Object> result = adminService.getReportedQuestion(typeID);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }


    /**
     * 获得回答举报记录
     * typeID：回答举报对应的信息id
     * type：类型（0为回答，1为评论，2为回复）
     */
    public String answerReported(){
        Map<String,Object> result = adminService.getAnswerReported(typeID,type);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 删除对应的举报记录
     * @return “SUCCESS”
     */
    public String reportDel(){
        Map<String,Object> result = new HashMap<>(ConstantUtil.HASH_MAP_NUM);
        result = adminService.deleteReport(reportID);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 封禁用户,根据userID封禁对应的用户days天
     */
    public String ban() {
        Map<String, Object> result = adminService.ban(userID, days);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 禁言用户,根据userID禁言对应的用户days天
     */
    public String estoppel() {
        Map<String, Object> result = adminService.estoppel(userID, days);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String questionDel() {
        Map<String, Object> result = adminService.questionDel(questionID);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String answerDel() {
        Map<String, Object> result = adminService.answerDel(answerID);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String commentDel() {
        Map<String, Object> result = adminService.commentDel(commentID);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String replyDel() {
        Map<String, Object> result = adminService.replyDel(replyID);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 删掉所有问题举报记录
     * @return “SUCCESS”
     */
    public String questionReportClear(){
        Map<String,Object> result;
        result = adminService.deleteAllQueReport();
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 删掉所有回答举报记录
     * @return “SUCCESS”
     */
    public String answerReportClear(){
        Map<String,Object> result;
        result = adminService.deleteAllAnsReport();
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 删掉所有评论举报记录
     * @return “SUCCESS”
     */
    public String commentReportClear(){
        Map<String,Object> result;
        result = adminService.deleteAllComReport();
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 删掉所有回复举报记录
     * @return “SUCCESS”
     */
    public String replyReportClear(){
        Map<String,Object> result;
        result = adminService.deleteAllRepReport();
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 存入学校
     * @return SUCCESS
     */
    public String saveSchool(){
        Map<String,Object> response = adminService.saveSchool(schoolName);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 存入学生、学院、专业信息
     * @return SUCCESS
     */
    public String saveStudents(){
        Map<String,Object> response = adminService.saveStudents(studentsInfo, schoolID);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }
    /**
     * 存入学生信息
     * @return “SUCCESS”
     */
    public String saveStudent(){
        Map<String,Object> result;
        result = adminService.saveStudent(studentInfo);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 存入教师信息（json数组）
     * @return “SUCCESS”
     */
    public String saveTeachers(){
        Map<String,Object> result;
        result = adminService.saveTeachers(teachersInfo, schoolID);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 存入教师信息
     * @return “SUCCESS”
     */
    public String saveTeacher(){
        Map<String,Object> result;
        result = adminService.saveTeacher(teacherNO, name, school, college);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }
    public Integer getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(Integer accountNum) {
        this.accountNum = accountNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Integer getTypeSum() {
        return typeSum;
    }

    public void setTypeSum(Integer typeSum) {
        this.typeSum = typeSum;
    }

    public String getDateNow() {
        return dateNow;
    }

    public void setDateNow(String dateNow) {
        this.dateNow = dateNow;
    }

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }

    public Integer getReportType() {
        return reportType;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getTypeID() {
        return typeID;
    }

    public void setTypeID(Integer typeID) {
        this.typeID = typeID;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getReportID() {
        return reportID;
    }

    public void setReportID(Integer reportID) {
        this.reportID = reportID;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Integer questionID) {
        this.questionID = questionID;
    }

    public Integer getAnswerID() {
        return answerID;
    }

    public void setAnswerID(Integer answerID) {
        this.answerID = answerID;
    }

    public Integer getCommentID() {
        return commentID;
    }

    public void setCommentID(Integer commentID) {
        this.commentID = commentID;
    }

    public Integer getReplyID() {
        return replyID;
    }

    public void setReplyID(Integer replyID) {
        this.replyID = replyID;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getStudentInfo() {
        return studentsInfo;
    }

    public void setStudentInfo(String studentInfo) {
        this.studentsInfo = studentsInfo;
    }

    public Integer getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(Integer schoolID) {
        this.schoolID = schoolID;
    }

    public String getTeachersInfo() {
        return teachersInfo;
    }

    public void setTeachersInfo(String teachersInfo) {
        this.teachersInfo = teachersInfo;
    }

    public String getTeacherNO() {
        return teacherNO;
    }

    public void setTeacherNO(String teacherNO) {
        this.teacherNO = teacherNO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSchool() {
        return school;
    }

    public void setSchool(Integer school) {
        this.school = school;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getStudentsInfo() {
        return studentsInfo;
    }

    public void setStudentsInfo(String studentsInfo) {
        this.studentsInfo = studentsInfo;
    }
}
