package org.gxfj.iknow.service;

import org.gxfj.iknow.dao.UserDAO;
import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.pojo.Userstate;
import org.gxfj.iknow.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Administrator
 */
@Service("userService")
@Transactional(readOnly = false)
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDAO userDAO;

    @Override
    public String logon(String username, String password, String email) {
        User user = new User();
        user.setName(username);
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
        return "success";
    }
}
