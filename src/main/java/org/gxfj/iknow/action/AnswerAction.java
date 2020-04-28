package org.gxfj.iknow.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hhj
 */

@Controller
public class AnswerAction {
    private Integer questionId;
    private String content;
    private Byte isAnonmyous;
    private String questionTitle;
    private InputStream inputStream;

    private final int SUCCESS = 0;
    private final int UN_LOGIN = 1;
    private final int MISS_QUESTIONID = 2;
    private final int MISS_ANSWER_IF = 3;

    final static private int RESPONSE_NUM = 20;
    @Autowired
    AnswerService answerService;

    public InputStream getInputStream() {
        return inputStream;
    }

    public String questionTitle(){
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response = new HashMap<>(RESPONSE_NUM);
        User user = (User)session.get("user");
        if(user == null){
            response.put("resultCode" , UN_LOGIN);
        }
        else if(questionId == null){
            response.put("resultCode" , MISS_QUESTIONID);
        }
        else{
            questionTitle = answerService.getQuestiontitle(questionId);
            response.put("title" , questionTitle);
            response.put("resulteCode" , SUCCESS);
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "success";
    }

    public String postAnswer(){
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response = new HashMap<>(RESPONSE_NUM);
        User user = (User)session.get("user");
        if(user == null){
            response.put("resultCode" , UN_LOGIN);
        }
        else if(questionId == null){
            response.put("resultCode" , MISS_QUESTIONID);
        }
        else if(content == null){
            response.put("resultCode" , MISS_ANSWER_IF);
        }
        else{
            //得到新发布的回答的id
            response = answerService.postAnswer(questionId,content,isAnonmyous,user);
            response.put("resultCode" , SUCCESS);
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "success";
    }
}
