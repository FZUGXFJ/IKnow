package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Searchhistory;

import java.util.List;

public interface SearchHistoryDAO extends BaseDAO<Searchhistory>{
    /**
     * 获得热度前10的搜索文本，精确获取
     * @return  按热度降序输出搜索文本列表
     */
    List<String> getHotSearch();
}
