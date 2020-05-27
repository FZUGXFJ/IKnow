package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Useridentity;

import java.util.List;

public interface UserIdentityDAO extends BaseDAO<Useridentity>{

    /**
     * 获得用户的所有身份
     * @param userId 用户id
     * @return 用户身份列表，没有返回null
     */
    public List<Useridentity> listUserIdentitiesByUserId (Integer userId);

}
