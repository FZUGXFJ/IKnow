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

    public String logout(){
        Map<String,Object> session = ActionContext.getContext().getSession();
        if(!session.isEmpty()){
            session.clear();
        }
        inputStream = new ByteArrayInputStream("{\"response\":0}".getBytes(StandardCharsets.UTF_8));
        return  ConstantUtil.RETURN_STRING;
    }

    public String login(){
        Map<String, Object> resultMap = new HashMap<>(ConstantUtil.HASH_MAP_NUM);
        Admin adminInf = new Admin();
        adminInf.setAccount(accountNum);
        adminInf.setPasswd(password);
        Admin admin = adminService.login(adminInf);
        if(admin == null){
            resultMap.put(ConstantUtil.JSON_RETURN_CODE, ConstantUtil.UN_LOGIN);
        }
        else{
            ActionContext.getContext().getSession().put(ConstantUtil.LOGIN_ADMIN_SESSION_NAME,admin);
            resultMap.put(ConstantUtil.JSON_RETURN_CODE, ConstantUtil.SUCCESS);
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(resultMap).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String statistics(){
        Map<String,Object> result=adminService.getData(dateNow,typeSum);
        result.put(ConstantUtil.JSON_RETURN_CODE, ConstantUtil.SUCCESS);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String isLogin() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map<String, Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        if (session.get(ConstantUtil.LOGIN_ADMIN_SESSION_NAME) == ConstantUtil.NO_ADMIN) {
            result.put(ConstantUtil.JSON_RETURN_CODE, ConstantUtil.UN_LOGIN);
        } else {
            result.put(ConstantUtil.JSON_RETURN_CODE, ConstantUtil.SUCCESS);
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        System.out.println(JSON.toJSONString(result));
        return ConstantUtil.RETURN_STRING;
    }

    public String active() {
        int length = 0;
        if (typeSum == ConstantUtil.DAILY_ACTIVE_USER_STATICS_TYPE_CODE) {
            length = ConstantUtil.DAILY_ACTIVE_USER_STATICS_LENGTH;
        } else if (typeSum == ConstantUtil.MONTHLY_ACTIVATE_USER_STATICS_TYPE_CODE) {
            length = ConstantUtil.MONTHLY_ACTIVATE_USER_STATICS_LENGTH;
        }

        Map<String,Object> result = adminService.getActiveData(dateNow,typeSum, length);
        result.put(ConstantUtil.JSON_RETURN_CODE, ConstantUtil.SUCCESS);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String questionTypeSum(){
        Map<String,Object> result = adminService.getQuestionTypeSumData();
        result.put(ConstantUtil.JSON_RETURN_CODE, ConstantUtil.SUCCESS);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String reportType(){
        Map<String,Object> result = adminService.getReportByType(reportType);
        result.put(ConstantUtil.JSON_RETURN_CODE, ConstantUtil.SUCCESS);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String reportReason(){
        Map<String,Object> result = adminService.getReportReason();
        result.put(ConstantUtil.JSON_RETURN_CODE, ConstantUtil.SUCCESS);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String userInfo(){
        Map<String,Object> result = adminService.getUserInfo(userID);
        result.put(ConstantUtil.JSON_RETURN_CODE, ConstantUtil.SUCCESS);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 获得问题的举报记录
     * @return “SUCCESS”
     */
    public String questionReported(){
        return  null;
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
}
