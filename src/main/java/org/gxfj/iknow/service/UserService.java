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
     * @return 返回信息如下
     */
    Map<String,Object> logon(String username, String password, String email, String verifyCode);

    /**
     * 发送验证码到邮箱
     * @param email 目标邮箱
     * @return 返回结果集合
     */
    Map<String,String> sendVerifyCode(String email);

    /**
     * 用密码登录
     * @param email 用户输入的邮箱
     * @param password 用户输入的密码
     * @return 返回验证结果的Map
     */
    Map<String, Object> loginByPassword(String email, String password);

    /**
     *免密码登录
     * @param email 登录用的email
     * @param verifyCode 登录的验证码
     * @return 消息集合
     */
    Map<String,Object> loginByNoPassword(String email,String verifyCode);

    /**
     *获得简略的用户信息
     * @param user 用户
     * @return json数据 包含头像，用户名，性别和介绍
     */
    String getSimpleUserInf(User user);

    /**
     * 获得用户的全部信息
     * @param user 用户
     * @return json形式的用户信息
     */
    Map<String, Object> getAllUserInf(User user);

    /**
     *修改用户信息
     * @param head 新头像
     * @param username 新用户名
     * @param gender 新性别
     * @param introduction 新介绍信息
     * @return json数据 resultCode：响应数据码，0表示修改成功，1表示用户名已占用
     */
    Map<String, Object> editUserInf(String head,String username,String gender,String introduction);


    /**
     * 设置用户新密码
     * @param user 要设置的用户
     * @param newPassword 新密码
     * @return 成功返回true，失败返回false
     */
    Boolean resetPassword(User user, String newPassword);

    /**
     * 将用户的邮箱更新。
     * @param user 用户
     * @param newEmail 新邮件
     * @return 是否成功
     */
    boolean reBindEmail(User user , String newEmail);

    /**
     * 是否存在邮箱
     * @param email 判断的邮箱
     * @return 是否存在:0不存在可以更新，1存在
     */
    boolean existEmail(String email);

    /**
     * 发送验证码到邮箱
     * @param email 目标邮箱
     * @return 返回结果集合
     */
    Map<String,String> sendVerifyCoderesetemail(String email);
}
