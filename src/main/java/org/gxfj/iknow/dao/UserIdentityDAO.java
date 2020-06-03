package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.pojo.Useridentity;

import java.util.List;

public interface UserIdentityDAO extends BaseDAO<Useridentity>{

    /**
     * 获得用户的所有身份
     * @param userId 用户id
     * @return 用户身份列表，没有返回null
     */
    public List<Useridentity> listUserIdentitiesByUserId (Integer userId);

    /**
     * 获得满足身份的人员信息
     * @param userId 用户id
     * @param schoolId 学校id
     * @param realName 用户姓名
     * @param studentNum 用户学号
     * @return 用户身份列表，没有返回null
     */
    public List<Useridentity> listStuIdentities (Integer userId, Integer schoolId, String realName, String studentNum);
}
