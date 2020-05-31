package org.gxfj.iknow.action;

import com.alibaba.fastjson.JSON;
import org.gxfj.iknow.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Controller
public class SearchAction {
    @Autowired
    private SearchService searchService;
    private InputStream inputStream;

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String searchHistory() {
        Map<String,Object> response = searchService.searchHistory();
        response.put("resultCode",0);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "success";
    }

    public String searchResult(String keyword) {
        Map<String,Object> response = searchService.searchResult(keyword);
        response.put("resultCode",0);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "success";
    }
}
