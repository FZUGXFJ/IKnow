package org.gxfj.iknow.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import static org.gxfj.iknow.util.ConstantUtil.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Controller
public class SearchAction {
    @Autowired
    private SearchService searchService;
    private InputStream inputStream;
    private String keyword;

    public InputStream getInputStream() {
        return inputStream;
    }
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getKeyword() {
        return keyword;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }


    public String searchHistory() {
        Map<String,Object> response = searchService.searchHistory();
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return RETURN_STRING;
    }

    public String searchResult() {
        Map<String, Object> response = searchService.searchResult(keyword);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return RETURN_STRING;
    }
}
