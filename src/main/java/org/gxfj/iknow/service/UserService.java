package org.gxfj.iknow.service;

import org.gxfj.iknow.pojo.User;

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
     * success:成功注册
     * exist email:邮箱已注册
     * exist username:用户名已存在
     * wrong verify code:验证码错误
     */
    public String logon(String username,String password,String email);
}
