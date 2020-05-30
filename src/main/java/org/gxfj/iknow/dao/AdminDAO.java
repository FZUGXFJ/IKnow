package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Admin;

import java.util.List;

public interface AdminDAO extends BaseDAO<Admin>{

    /**
     * 根据管理员的账号获得管理员
     * @param account 账号
     * @return 管理员
     */
    public Admin getAdminByCount(Integer account);

    /**
     * 获取过去7天每天的问题数
     * @param date 日期
     * @return 每天的个数
     */
    public List<Integer> getQuestionSum(String date);

    /**
     * 获取过去7天每天注册的用户数
     * @param date 日期
     * @return 每天的个数
     */
    public List<Integer> getUserSum(String date);

    /**
     * 获取过去7天每天的活跃用户数
     * @param date 日期
     * @return 每天的个数
     */
    public List<Integer> getUserdayActives(String date);

    /**
     * 获取过去3个月（包含当月）天每天的活跃用户数
     * @param date 日期
     * @return 每月的个数
     */
    public List<Integer> getUsermonActives(String date);

    /**
     * 获取各种类别的问题数
     * @return 各种类别分别的问题数
     */
    public List<Integer> getQuestiontypeSums();



}
