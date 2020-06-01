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

    /**
     * 获取过去length天，每天的用户活跃数
     * @param dateNow 当前日期
     * @param typeSum 数据的尺寸，可选为月、日
     * @param length 获取的天数
     * @return 数据
     */
    public Map<String,Object> getActiveData(String dateNow,Integer typeSum, Integer length);

    /**
     * 获取各个门类下的问题数量
     * @return 数据
     */
    public Map<String,Object> getQuestionTypeSumData();
}
