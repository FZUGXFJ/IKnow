package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Admin;

public interface AdminDAO extends BaseDAO<Admin>{

    /**
     * 根据管理员的账号获得管理员
     * @param account 账号
     * @return 管理员
     */
    public Admin getAdminByCount(Integer account);

}
