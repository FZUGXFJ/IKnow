package org.gxfj.iknow.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.pojo.User;
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
        response.put("resultCode",0);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "success";
    }

    public String searchResult() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        User user = (User) session.get("user");
        Integer userId=-1;
        if (user != null) {
            userId=user.getId();
        }
        Map<String,Object> response = searchService.searchResult(keyword,userId);
        response.put("resultCode",0);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "success";
    }
}
