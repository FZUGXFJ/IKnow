package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Browsinghistory;
import org.gxfj.iknow.pojo.Collectionproblem;

import java.util.List;

public interface BrowsingHistoryDAO extends BaseDAO<Browsinghistory>{
    /**
     * 获取历史记录数量
     * @param questionId 问题id
     * @return 数量
     */
    Integer getBrowsingCount(Integer questionId);

    /**
     * 查找最近几天所有用户的浏览记录
     * @param day 查询的天数
     * @return 用List存储了所有用户在近day天内的所有浏览记录
     */
    List<Browsinghistory> listInLastDay(Integer day);

    /**
     * 根据用户id获取所有浏览记录
     * @param userId 用户id
     * @param start 起始地址
     * @return  浏览记录
     */
    List<Browsinghistory> getBrowsingHistoryByUserId(Integer userId, Integer start);

    /**
     * 根据用户id和问题id获取所有浏览记录
     * @param userId 用户id
     * @param questionId 问题id
     * @return  浏览记录
     */
    List<Browsinghistory> getBrowsingHistoryByUserIdAndquestionId(Integer userId, Integer questionId);

    /**
     * 获得用户浏览记录的数目
     * @param userId 用户id
     * @return 用户浏览的条数
     */
    Integer getUserBrowseCount(Integer userId);
}
