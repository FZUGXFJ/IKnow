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
    Map<String, Object> login(Admin adminInf);

    /**
     * 判断管理员是否登录
     * @return
     */
    Map<String, Object> isLogin();

    /**
     * 获取过去七天中每天的用户/问题总数
     * @param dateNow 当前日期
     * @param typeSum 数据类型
     * @return 数据
     */
    Map<String,Object> getSumData(String dateNow, Integer typeSum);

    /**
     * 获取过去length天，每天的用户活跃数
     * @param dateNow 当前日期
     * @param length 获取的天数
     * @return 数据
     */
    Map<String,Object> getDailyActiveUserData(String dateNow, Integer length);

    /**
     * 获取过去length月，每天的用户活跃数
     * @param dateNow 当前日期
     * @param length 获取的月数
     * @return 数据
     */
    Map<String,Object> getMonthlyActiveUserData(String dateNow, Integer length);

    /**
     * 获取各个门类下的问题数量
     * @return 数据
     */
    Map<String,Object> getQuestionTypeSumData();

    /**
     * 获取指定举报类别的举报信息
     * @param reportType 举报类型
     * @return 包含举报信息列表的map数据
     */
    Map<String,Object> getReportByType(Integer reportType);

    /**
     * 获取举报原因
     * @return 包含举报原因列表的map数据
     */
    Map<String,Object> getReportReason();

    /**
     * 获取用户信息
     * @return 包含用户信息列表的map数据
     */
    Map<String,Object> getUserInfo(Integer userId);

    /**
     * 获得被举报的问题的信息
     * @param questioinId 问题id
     * @return 包含举报的问题的信息的map数据
     */
    Map<String, Object> getReportedQuestion(Integer questioinId);

    /**
     * 获取对应回答信息列表
     * @param typeId 回答举报对应的信息id
     * @param type 类型（0为回答，1为评论，2为回复）
     * @return 包含用户信息列表的map数据
     */
    Map<String,Object> getAnswerReported(Integer typeId, Integer type);

    /**
     * 删除对应举报信息
     * @param reportId 举报信息id
     * @return 数据
     */
    Map<String, Object> deleteReport(Integer reportId);
}
