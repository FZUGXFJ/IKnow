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
     */
    public Map<String,Object> logon(String username, String password, String email, String verifyCode);

    /**
     * 发送验证码到邮箱
     * @param email 目标邮箱
     * @return 返回结果集合
     */
    public Map<String,String> sendVerifyCode(String email);

    /**
     * 无密码登录
     * @param loginInf 登录信息
     * @return 返回user，如果登录失败user为空
     */
    public User loginByPassword(User loginInf);

    /**
     *免密码登录
     * @param email 登录用的email
     * @param sessionEmail 发送邮件用的email
     * @param verifyCode 登录的验证码
     * @param sessionVerifyCode 生成的验证码
     * @return 消息集合
     */
    public Map<String,Object> loginByNoPassword(String email,String sessionEmail,String verifyCode,String sessionVerifyCode);
}
