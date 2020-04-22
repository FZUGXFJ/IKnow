package org.gxfj.iknow.service;

import org.gxfj.iknow.pojo.User;

import javax.mail.MessagingException;
import java.util.Map;

/**
 * @author herokilito
 */
public interface UserService {

    /**
     * 注册
     * @param username 用户名
     * @param email 邮箱
     * @param password 密码
     * @param verifyCode 注册验证码
     * @return 返回信息如下
     * success:成功注册
     * exist email:邮箱已注册
     * exist username:用户名已存在
     * wrong verify code:验证码错误
     */
    public Map<String,Object> logon(String username, String password, String email, String verifyCode);

    /**
     * 发送验证码到邮箱
     * @param email 目标邮箱
     * @return 返回结果集合
     */
    public Map<String,String> sendVerifyCode(String email);
}
