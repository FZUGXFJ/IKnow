package org.gxfj.iknow.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.service.UserService;
import org.gxfj.iknow.util.ConstantUtil;
import org.gxfj.iknow.util.MailUtil;
import static org.gxfj.iknow.util.ConstantUtil.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


/**
 * @author herokilito
 */
@Controller
@Scope("prototype")
public class UserAction {

    Integer userId;
    String username;
    String email;
    String password;
    String verifyCode;
    String head;
    String gender;
    String introduction;
    Integer remember;
    String newPassword;
    String newEmail;
    @Autowired
    UserService userService;

    @Autowired
    MailUtil mailUtil;
    private InputStream inputStream;

    /*
    private final String EMAIL = "email";
    private final String VERIFY_CODE = "verifyCode";
    private final String SUCCESS = "success";
    private final static String RESULT_CODE = "resultCode";
    private final static String LOGIN_USER_SESSION_NAME = "user";
    private final static String RESET_PASSWORD_VERIFY_CODE_SESSION_NAME = "resetPasswordVerifyCode";
    private final static String RESET_PASSWORD_VERIFY_STATE_SESSION_NAME = "resetPasswordVerifyState";
    private final static String REBIND_EMAIL_VERIFY_CODE_SESSION_NAME = "rebindEmailVerifyCode";
    private final static String REBIND_EMAIL_VERIFY_STATE_SESSION_NAME = "rebindEmailVerifyState";
    private final static String NEW_EMAIL_SESSION_NAME = "newEmail";
    private final static String NEW_EMAIL_VERIFY_CODE_SESSION_NAME = "newEmailVerifyCode";
    private final static String NEW_EMAIL_VERIFY_STATE_SESSION_NAME = "newEmailVerifyState";

    private final static String SESSION_NAME = "";
    private final static int SUCCESS_CODE = 0;
    private final static int RESET_PASSWORD_FAIL = 1;
    private final static int UN_LOGIN = 1;
    private final static int VERIFY_DEFAULT = 1;
    private final static int MIN_HASH_MAP_NUM = 10;
    private final static int SUCCESS_LOGON = 0;
    private final static String NO_USER = null;
     */

    /**
     * 用密码登录
     * @return SUCCESS
     */
    public String passwordLogin() {
        Map<String, Object> result = userService.loginByPassword(email, password);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return RETURN_STRING;
    }

    public String emailLogin() {
        Map<String,Object> result = userService.loginByNoPassword(email, verifyCode);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return RETURN_STRING;
    }

    public String logon() {
        Map<String,Object> resultMap = userService.logon(username,password,email, verifyCode);

        inputStream = new ByteArrayInputStream(JSON.toJSONString(resultMap).getBytes(StandardCharsets.UTF_8));
        return RETURN_STRING;
    }

    /**
     * 用于邮箱登录，session中无user
     * @return 处理是否成功
     */
    public String sendEmail() {
        Map<String,Object> session = ActionContext.getContext().getSession();
        Map<String,String> resultMap = userService.sendVerifyCode(email);
        session.put(EMAIL,email);
        session.put(VERIFY_CODE,resultMap.get("verifyCode"));
        inputStream = new ByteArrayInputStream(resultMap.get("result").getBytes(StandardCharsets.UTF_8));
        return RETURN_STRING;
    }

    public String getSimpleUserInf() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        String result=userService.getSimpleUserInf((User) session.get(ConstantUtil.SESSION_USER));
        inputStream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
        return RETURN_STRING;
    }

    public String editInf() {
        Map<String, Object> result = userService.editUserInf(head,username,gender,introduction);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return RETURN_STRING;
    }

    public String isLogin() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        if (session.get(LOGIN_USER_SESSION_NAME) == NO_USER) {
            result.put(JSON_RETURN_CODE_NAME, UN_LOGIN);
        } else {
            result.put(JSON_RETURN_CODE_NAME, SUCCESS);
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        System.out.println(JSON.toJSONString(result));
        return RETURN_STRING;
    }

    /**
     * 用户已登录状态下使用，用于修改密码，发送验证码到用户邮箱
     * @return 处理是否成功
     */
    public String sendResetPasswordVerifyEmail() {
        Map<String,Object> session = ActionContext.getContext().getSession();
        User user = (User) session.get("user");

        Map<String,String> resultMap = userService.sendVerifyCode(user.getEmail());
        session.put(RESET_PASSWORD_VERIFY_CODE_SESSION_NAME,resultMap.get(VERIFY_CODE));
        session.put(RESET_PASSWORD_VERIFY_STATE_SESSION_NAME, false);
        inputStream = new ByteArrayInputStream(resultMap.get("result").getBytes(StandardCharsets.UTF_8));
        return RETURN_STRING;
    }

    /**
     * 用于修改密码时，邮箱验证码的验证
     * @return 处理是否成功
     */
    public String resetPasswordVerify() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        Boolean state = (Boolean)session.get(RESET_PASSWORD_VERIFY_STATE_SESSION_NAME);
        if (session.get(LOGIN_USER_SESSION_NAME) == NO_USER) {
            result.put(JSON_RETURN_CODE_NAME, UN_LOGIN);
        } else {
            if (state != null && !state && verifyCode.equals(session.get(RESET_PASSWORD_VERIFY_CODE_SESSION_NAME))) {
                result.put(JSON_RETURN_CODE_NAME,SUCCESS);
                session.put(RESET_PASSWORD_VERIFY_CODE_SESSION_NAME, null);
                session.put(RESET_PASSWORD_VERIFY_STATE_SESSION_NAME, true);
            } else {
                result.put(JSON_RETURN_CODE_NAME, VERIFY_DEFAULT);
            }
        }

        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return RETURN_STRING;
    }

    /**
     * 修改密码中，如果之前的邮箱验证通过则修改密码
     * @return 处理是否成功
     */
    public String resetPassword() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);

        User user = (User) session.get("user");
        Boolean resetPasswordVerify = (Boolean)session.get(RESET_PASSWORD_VERIFY_STATE_SESSION_NAME);
        if (user != null && resetPasswordVerify != null && resetPasswordVerify) {
            if (userService.resetPassword(user, newPassword)) {
                result.put(JSON_RETURN_CODE_NAME, SUCCESS);
            } else {
                result.put(JSON_RETURN_CODE_NAME, RESET_PASSWORD_FAIL);
            }

        } else {
            result.put(JSON_RETURN_CODE_NAME, UN_LOGIN);
        }
        session.put(RESET_PASSWORD_VERIFY_STATE_SESSION_NAME, null);

        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return RETURN_STRING;
    }


    /**
     * 用户已登录状态下使用，用于修改绑定，发送验证码到用户邮箱
     * @return 处理是否成功
     */
    public String sendRebindEmailVerifyEmail() {
        Map<String,Object> session = ActionContext.getContext().getSession();
        User user = (User) session.get(LOGIN_USER_SESSION_NAME);

        Map<String,String> resultMap = userService.sendVerifyCoderesetemail(user.getEmail());
        session.put(REBIND_EMAIL_VERIFY_CODE_SESSION_NAME,resultMap.get(VERIFY_CODE));
        session.put(REBIND_EMAIL_VERIFY_STATE_SESSION_NAME, false);
        inputStream = new ByteArrayInputStream(resultMap.get("result").getBytes(StandardCharsets.UTF_8));
        return RETURN_STRING;
    }


    /**
     * 用于修改密码时，邮箱验证码的验证
     * @return JSON字符串
     */
    public String reBindEmailVerify() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        Boolean state = (Boolean) session.get(REBIND_EMAIL_VERIFY_STATE_SESSION_NAME);

        if (session.get(LOGIN_USER_SESSION_NAME) == NO_USER ) {
            result.put(JSON_RETURN_CODE_NAME,UN_LOGIN);
        } else {
            if (state != null && !state  && verifyCode.equals(session.get(REBIND_EMAIL_VERIFY_CODE_SESSION_NAME))) {
                result.put(JSON_RETURN_CODE_NAME,SUCCESS);

                session.put(REBIND_EMAIL_VERIFY_CODE_SESSION_NAME,null);
                session.put(REBIND_EMAIL_VERIFY_STATE_SESSION_NAME, true);
            } else {
                result.put(JSON_RETURN_CODE_NAME, VERIFY_DEFAULT);
            }
        }

        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return RETURN_STRING;
    }

    /**
     * 修改绑定邮箱中，对新邮箱发送验证码
     * @return 返回"success"
     */
    public String newEmailVerify(){
        Map<String,Object> session = ActionContext.getContext().getSession();

        String response;

        Boolean emailState = (Boolean)session.get(REBIND_EMAIL_VERIFY_STATE_SESSION_NAME);
        if (emailState != null && emailState) {
            Map<String,String> resultMap = userService.sendVerifyCoderesetemail(newEmail);
            session.put(NEW_EMAIL_SESSION_NAME, newEmail);
            session.put(NEW_EMAIL_VERIFY_CODE_SESSION_NAME, resultMap.get(VERIFY_CODE));
            session.put(NEW_EMAIL_VERIFY_STATE_SESSION_NAME, false);
            response = resultMap.get("result");
        } else {
            response = "{\"head\":\"发送失败\",\"body\":\"服务器异常\"}";
        }
        inputStream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));
        return RETURN_STRING;
    }

    /**
     * 修改绑定邮箱中，验证发送给新邮箱的验证码，若成功则绑定新邮箱
     * @return 返回"success"
     */
    public String reBindEmail(){
        Map<String,Object> session = ActionContext.getContext().getSession();
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        String code=(String)session.get(NEW_EMAIL_VERIFY_CODE_SESSION_NAME);
        Boolean state = (Boolean)session.get(NEW_EMAIL_VERIFY_STATE_SESSION_NAME);
        User user=(User)session.get(LOGIN_USER_SESSION_NAME);
        //验证新邮箱是否存在
        if (userService.existEmail((String) session.get(NEW_EMAIL_SESSION_NAME))) {
            result.put(JSON_RETURN_CODE_NAME, 2);
        } else {
            if (state != null && !state && code.equals(verifyCode)) {
                if (userService.reBindEmail(user, (String) session.get(NEW_EMAIL_SESSION_NAME))) {
                    result.put(JSON_RETURN_CODE_NAME, 0);
                } else {
                    result.put(JSON_RETURN_CODE_NAME, 1);
                }
            } else {
                result.put(JSON_RETURN_CODE_NAME, 1);
            }
        }
        session.put(NEW_EMAIL_VERIFY_STATE_SESSION_NAME, null);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return RETURN_STRING;
    }

    public String logout(){
        Map<String,Object> session = ActionContext.getContext().getSession();
        if(!session.isEmpty()){
            session.clear();
        }
        inputStream = new ByteArrayInputStream("{\"response\":0}".getBytes(StandardCharsets.UTF_8));
        return  RETURN_STRING;
    }

    public String getAllUserInf(){
        Map<String,Object> session = ActionContext.getContext().getSession();
        User user = (User)session.get(ConstantUtil.SESSION_USER);
        Map<String, Object> result = userService.getAllUserInf(user);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return RETURN_STRING;
    }

    public String userHome(){
        Map<String, Object> result = userService.getHomedata(userId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return RETURN_STRING;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getHead(){ return head; }

    public void setHead(String head){ this.head=head;}

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public Integer getRemember() {
        return remember;
    }

    public void setRemember(Integer remember) {
        this.remember = remember;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
