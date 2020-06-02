package org.gxfj.iknow.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.pojo.Admin;
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

        Map<String, Object> resultMap = adminService.login(accountNum, password);

        inputStream = new ByteArrayInputStream(JSON.toJSONString(resultMap).getBytes(StandardCharsets.UTF_8));
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
     * 获得
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

    public void setUserId(Integer userID) {
        this.userID = userID;
    }

    public Integer getUserId() {
        return userID;
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
}
