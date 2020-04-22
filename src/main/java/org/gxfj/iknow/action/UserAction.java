package org.gxfj.iknow.action;

import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.gxfj.iknow.service.UserServiceImpl;
import org.gxfj.iknow.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
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
    @Autowired
    UserServiceImpl userService;
    @Autowired
    MailUtil mailUtil;
    private InputStream inputStream;
    private final String EMAIL = "email";
    private final String VERIFY_CODE = "verifyCode";

    public InputStream getInputStream() {
        return inputStream;
    }

    void login() {

    }

    public String logon() throws Exception {
        Map<String,Object> session = ActionContext.getContext().getSession();
        String result = null;
        if (session.get(VERIFY_CODE) == null || !verifyCode.equals(session.get(VERIFY_CODE))) {
            result = "{\"response\":3}";
            inputStream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
            return "success";
        } else if (session.get(EMAIL) == null || !email.equals(session.get(EMAIL))) {
            result = "{\"response\":4}";
            inputStream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
            return "success";
        }
        Map<String,Object> resultMap = userService.logon(username,password,email,verifyCode);
        if ((Integer) resultMap.get("value") == 0) {
            session.put("user",resultMap.get("user"));
            result = "{\"response\":0}";
            inputStream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
        } else if ((Integer) resultMap.get("value") == 1) {
            result = "{\"response\":1}";
            inputStream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
        } else if ((Integer) resultMap.get("value") == 2) {
            result = "{\"response\":2}";
            inputStream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
        }
        return "success";
    }

    public String sendEmail() throws Exception {
        Map<String,Object> session = ActionContext.getContext().getSession();
        Map<String,String> resultMap = userService.sendVerifyCode(email);
        session.put(EMAIL,email);
        session.put(VERIFY_CODE,resultMap.get("verifyCode"));
        inputStream = new ByteArrayInputStream(resultMap.get("result").getBytes(StandardCharsets.UTF_8));
        return "success";
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

}
