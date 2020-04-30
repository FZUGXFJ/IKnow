package org.gxfj.iknow.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.service.UserService;
import org.gxfj.iknow.util.MailUtil;
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

    String username;
    String email;
    String password;
    String verifyCode;
    String head;
    String gender;
    String introduction;
    boolean remember;
    @Autowired
    UserService userService;

    @Autowired
    MailUtil mailUtil;
    private InputStream inputStream;
    private final String EMAIL = "email";
    private final String VERIFY_CODE = "verifyCode";
    private final String SUCCESS = "success";
    private final static String RESULT_CODE_NAME = "resultCode";
    private final static int UN_LOGIN = 1;
    private final static int MIN_HASH_MAP_NUM = 10;

    public InputStream getInputStream() {
        return inputStream;
    }

    public String passwordLogin() {
        User loginInf = new User();
        loginInf.setEmail(email);
        loginInf.setPasswd(password);
        User user = userService.loginByPassword(loginInf);
        if (user != null) {
            ActionContext.getContext().getSession().put("user",user);
            inputStream = new ByteArrayInputStream("{\"response\":0}".getBytes(StandardCharsets.UTF_8));
        } else {
            inputStream = new ByteArrayInputStream("{\"response\":1}".getBytes(StandardCharsets.UTF_8));
        }
        return SUCCESS;
    }

    public String emailLogin() {
        User loginInf = new User();
        loginInf.setEmail(email);
        Map<String,Object> session = ActionContext.getContext().getSession();
        Map<String,Object> result = userService.loginByNoPassword(email,(String)session.get(EMAIL), verifyCode,(String) session.get(VERIFY_CODE));
        User user = (User) result.get("user");
        if (user != null) {
            ActionContext.getContext().getSession().put("user",user);
            inputStream = new ByteArrayInputStream("{\"response\":0}".getBytes(StandardCharsets.UTF_8));
        } else {
            String response = "{\"response\":" + result.get("value") + "}";
            inputStream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));
        }
        return SUCCESS;
    }

    public String logon() {
        Map<String,Object> session = ActionContext.getContext().getSession();
        String result = null;
        if (session.get(VERIFY_CODE) == null || !verifyCode.equals(session.get(VERIFY_CODE))) {
            result = "{\"response\":3}";
            inputStream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
            return SUCCESS;
        } else if (session.get(EMAIL) == null || !email.equals(session.get(EMAIL))) {
            result = "{\"response\":4}";
            inputStream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
            return SUCCESS;
        }
        Map<String,Object> resultMap = userService.logon(username,password,email,verifyCode);
        if ((Integer) resultMap.get("value") == 0) {
            session.put("user",resultMap.get("user"));
        }
        result = "{\"response\":" + resultMap.get("value") + "}";
        inputStream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
        return SUCCESS;
    }

    public String sendEmail() {
        Map<String,Object> session = ActionContext.getContext().getSession();
        Map<String,String> resultMap = userService.sendVerifyCode(email);
        session.put(EMAIL,email);
        session.put(VERIFY_CODE,resultMap.get("verifyCode"));
        inputStream = new ByteArrayInputStream(resultMap.get("result").getBytes(StandardCharsets.UTF_8));
        return SUCCESS;
    }

    public String userInf() {
        Map<String,Object> session = ActionContext.getContext().getSession();
        String result=userService.getUserInf((User) session.get("user"));
        inputStream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
        return SUCCESS;
    }

    public String editInf() {
        Map<String,Object> session = ActionContext.getContext().getSession();
        String result=userService.editUserInf(head,username,gender,introduction,(User) session.get("user"));
        inputStream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
        return SUCCESS;
    }

    public String isLogin() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map<String, Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        if (session.get("user") == null) {
            result.put(RESULT_CODE_NAME, UN_LOGIN);
        } else {
            result.put(RESULT_CODE_NAME, SUCCESS);
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return SUCCESS;
    }

    public String sendVerifyEmail() {
        return sendEmail();
    }

    public String resetPasswordVerify() {
        return SUCCESS;
    }

    public String resetPassword() {
        return SUCCESS;
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
}
