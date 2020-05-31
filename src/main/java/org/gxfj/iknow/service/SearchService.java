package org.gxfj.iknow.service;

import org.gxfj.iknow.pojo.Question;
import org.gxfj.iknow.pojo.User;

import java.util.List;
import java.util.Map;

public interface SearchService {
    /**
     * 按热度降序输出搜索记录文本
     */
    Map<String, Object> searchHistory();

    /**
     *  按关键词搜索
     *  @param keyword 搜索的关键字
     */
    Map<String,Object> searchResult(String keyword,Integer userId);
}
