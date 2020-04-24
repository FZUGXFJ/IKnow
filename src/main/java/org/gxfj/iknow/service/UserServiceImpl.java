package org.gxfj.iknow.service;

import com.alibaba.fastjson.JSONObject;
import org.gxfj.iknow.dao.UserDAO;
import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.pojo.Userstate;
import org.gxfj.iknow.util.MailUtil;
import org.gxfj.iknow.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
@Service("userService")
@Transactional(readOnly = false)
public class UserServiceImpl<result> implements UserService{

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private MailUtil mailUtil;

    @Override
    public Map<String,Object> logon(String username, String password, String email, String verifyCode) {
        Map<String,Object> resultMap = new HashMap<>(16);
        User user = new User();
        if (userDAO.hasUsername(username)) {
            resultMap.put("value",1);
            return resultMap;
        }
        if (userDAO.hasUserEmail(email)) {
            resultMap.put("value",2);
            return resultMap;
        }
        user.setName(username);
        //使用md5加密
        user.setPasswd(SecurityUtil.md5(password));
        user.setEmail(email);
        /*默认信息*/
        //注册时间为当前系统时间
        user.setDate( new Timestamp(System.currentTimeMillis()));
        //初始徽章为0
        user.setBadgeNum(0);
        //初始经验为1
        user.setExp(1);
        //初始性别为保密
        user.setGender("保密");
        //初始用户无身份认证信息
        user.setIsAttest((byte) 0);
        Userstate userstate = new Userstate();
        userstate.setId(1);
        //初始用户状态为正常
        user.setUserstateByStateId(userstate);
        //初始头像
        user.setHead("<i class='fas fa-user-circle'></i>");
        userDAO.add(user);
        resultMap.put("value",0);
        resultMap.put("result","注册成功");
        resultMap.put("user",user);
        return resultMap;
    }

    @Override
    public Map<String,String> sendVerifyCode(String email) {
        Map<String,String> map = new HashMap<>();
        String subject = "IKnow验证邮件";
        String verifyCode = SecurityUtil.generatorVerifyCode(6);
        map.put("verifyCode",verifyCode);
        String content = "您的验证码是<h1>" +
                verifyCode +
                "</h1>请在15分钟内完成验证";
        String result;
        try {
            mailUtil.sendMail(email,subject,content);
        } catch (Exception e) {
            result = "{\"head\":\"发送失败\",\"body\":\"服务器异常\"}";
            map.put("result",result);
            e.printStackTrace();
            return map;
        }
        result = "{\"head\":\"发送成功\",\"body\":\"请进入邮箱查看验证码\"}";
        map.put("result",result);
        return map;
    }

    @Override
    public User loginByPassword(User loginInf) {
        User user = userDAO.getUserByEmail(loginInf.getEmail());
        if (SecurityUtil.md5Compare(loginInf.getPasswd(),user.getPasswd())) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public Map<String,Object> loginByNoPassword(String email, String sessionEmail, String verifyCode, String sessionVerifyCode) {
        Map<String,Object> result = new HashMap<>(16);
        if (!email.equals(sessionEmail)) {
            result.put("value",1);
        } else if (!verifyCode.equals(sessionVerifyCode)) {
            result.put("value",2);
        } else {
            User user = userDAO.getUserByEmail(email);
            if (user == null) {
                result.put("value",3);
            } else {
                result.put("value",0);
                result.put("user",user);
            }
        }
        return result;
    }

    @Override
    public String getUserInf(User userInf) {
        String result;
        if(userInf==null) {
            return "{\"resultCode\":1}";
        }
        else {
            if (userInf.getIntroduction() == null) {
                result = "{\"resultCode\" :" + 0 + ", \"userInf\": { \"head\": \"" + userInf.getHead() +
                        "\",\"username\":\" " + userInf.getName() + "\",\"gender\": \"" + userInf.getGender() +
                        "\",\"introduction\":" + userInf.getIntroduction() + "}}";
            } else {
                result = "{\"resultCode\" :" + 0 + ", \"userInf\": { \"head\": \"" + userInf.getHead() +
                        "\",\"username\":\" " + userInf.getName() + "\",\"gender\": \"" + userInf.getGender() +
                        "\",\"introduction\":\" " + userInf.getIntroduction() + "\"}}";
            }
        }
        return result;
    }

    @Override
    public String editUserInf(String head, String username, String gender, String introduction,User userInf) {
        if (userDAO.hasUsername(username)){
            return "{\"resultCode\":0}";
        }
        userInf.setHead(head);
        userInf.setGender(gender);
        userInf.setName(username);
        userInf.setIntroduction(introduction);
        userDAO.update(userInf);
        return "{\"resultCode\":0}";
    }
}
