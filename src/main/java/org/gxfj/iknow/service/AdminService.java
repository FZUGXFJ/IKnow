package org.gxfj.iknow.service;

import org.gxfj.iknow.pojo.Admin;

/**
 * @author hhj
 */
public interface AdminService {

    /**
     * 管理员登录
     * @param adminInf 输入的管理员信息
     * @return 管理员
     */
    public Admin login(Admin adminInf);
}
