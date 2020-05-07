package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Browsinghistory;

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
}
