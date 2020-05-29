package org.gxfj.iknow.service;

import org.gxfj.iknow.pojo.Admin;

import java.util.Map;

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

    /**
     * 获取统计
     * @param dateNow 当前日期
     * @param typeSum 数据类型
     * @return 数据
     */
    public Map<String,Object> getData(String dateNow,Integer typeSum);
}
