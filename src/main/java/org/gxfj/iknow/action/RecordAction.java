package org.gxfj.iknow.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.service.AnswerService;
import org.gxfj.iknow.service.CollectionService;
import org.gxfj.iknow.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 爱学习的水先生
 */


@Controller
public class RecordAction {
    private Integer start;
    private InputStream inputStream;

    @Autowired
    RecordService recordService;

    public InputStream getInputStream() { return inputStream; }
    final static private int RESPONSE_NUM = 20;

    private final String SESSION_USER = "user";
    private final int SUCCESS = 0;
    private final int UN_LOGIN = 2;
    private final int NO_MORE = 1;

    public String collectionRecord(){
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response = new HashMap<>(RESPONSE_NUM);
        User user = (User)session.get(SESSION_USER);
        if(user == null){
            response.put("resultCode" , UN_LOGIN);
        }
        else {
            response=recordService.collectionRecord(user,start);
            Integer x=(Integer)response.get("Num");
            if(x<20){
                response.put("resultCode",NO_MORE);
            }
            else
            {
                response.put("resultCode",SUCCESS);
            }
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "success";
    }
    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getStart() {
        return start;
    }
}
