package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Browsinghistory;

public interface BrowsingHistoryDAO extends BaseDAO<Browsinghistory>{
    /**
     * 获取历史记录数量
     * @param questionId 问题id
     * @return 数量
     */
    Integer getBrowsingCount(Integer questionId);
}
