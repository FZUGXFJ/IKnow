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

    /**
     * 获取指定举报类别的举报信息
     * @return 包含举报信息列表的map数据
     */
    public Map<String,Object> getReportByType(Integer reportType);

    /**
     * 获取举报原因
     * @return 包含举报原因列表的map数据
     */
    public Map<String,Object> getReportReason();

    /**
     * 获取用户信息
     * @return 包含用户信息列表的map数据
     */
    public Map<String,Object> getUserInfo(Integer userId);

    /**
     * 获得被举报的问题的信息
     * @param questioinId 问题id
     * @return 包含举报的问题的信息的map数据
     */
    public Map<String, Object> getReportedQuestion(Integer questioinId);
}
