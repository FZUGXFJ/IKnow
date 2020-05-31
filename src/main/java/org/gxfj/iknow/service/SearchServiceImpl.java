package org.gxfj.iknow.service;

import org.gxfj.iknow.dao.SearchHistoryDAO;
import org.gxfj.iknow.pojo.Searchhistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("searchService")
public class SearchServiceImpl implements SearchService{

    @Autowired
    SearchHistoryDAO searchHistoryDAO;

    @Override
    public Map<String, Object> searchHistory() {
        List<String> hotSearch=searchHistoryDAO.getHotSearch();
        List<Map<String,Object>> hotSearchContents=new ArrayList<>();
        Map<String,Object> hotSearchContent;
        for (String content:hotSearch) {
            hotSearchContent=new HashMap<>(1);
            hotSearchContent.put("content",content);
            hotSearchContents.add(hotSearchContent);
        }
        Map<String,Object> response=new HashMap<>(10);
        response.put("hotSearch",hotSearchContents);
        return response;
    }
}
