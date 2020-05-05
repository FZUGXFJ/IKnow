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
    private Byte isAnonymous;
    private String questionTitle;
    private InputStream inputStream;
    private Integer answerId;

    private final int SUCCESS = 0;
    private final int UN_LOGIN = 1;
    private final int MISS_QUESTIONID = 2;
    private final int MISS_ANSWER_IF = 3;
    private final int USER_IS_NOT_QUESTIONER = 2;
    private final int USER_IS_NOT_ANSWERER = 1;

    final static private int RESPONSE_NUM = 20;
    private static int MAP_NUM = 20;
    @Autowired
    AnswerService answerService;

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
            response.put("resultCode" , SUCCESS);
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "success";
    }

    public String postAnswer() {
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
            response = answerService.postAnswer(questionId,content,isAnonymous,user);
            response.put("resultCode" , SUCCESS);
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "success";
    }

    public String viewAnswer() {
        Map<String,Object> session = ActionContext.getContext().getSession();
        User user = (User) session.get("user");
        Map<String,Object> response = answerService.getRecommendAnswer(questionId,answerId,user);
        response.put("resultCode",SUCCESS);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "success";
    }

    public String adoptAnswer() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response = new HashMap<>(MAP_NUM);
        //从Session中获得当前用户对象
        User user = (User) session.get("user");


        if (user != null) {
            if (answerService.adoptAnswer(user, answerId)) {
                //用户已登录，且用户为题主，返回采纳成功
                response.put("resultCode", SUCCESS);
            } else {
                //用户已登录，但用户不是题主，返回用户不是提问者
                response.put("resultCode", USER_IS_NOT_QUESTIONER);
            }
        } else {
            //用户未登录，返回未登录
            response.put("resultCode", UN_LOGIN);
        }

        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "success";
    }

    public String cancelAdopt(){
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response = new HashMap<>(MAP_NUM);
        User user = (User) session.get("user");

        if (user != null) {
            if (answerService.cancelAdopt(user, answerId)) {
                response.put("resultCode", SUCCESS);
            } else {
                response.put("resultCode", USER_IS_NOT_ANSWERER);
            }
        } else {
            response.put("resultCode", UN_LOGIN);
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "success";
    }

    public String recommendAnswer(){
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response;
        Map<String,Object> cUser=new HashMap<>(2);
        response=answerService.getRecommendAnswer(20);
        response.put("resultCode",SUCCESS);
        User user=(User)session.get("user");
        if (user != null) {
            cUser.put("id",user.getId());
            cUser.put("head","<img src='../head/" + user.getHead() +
                    "' width='100%' height='100%' style='border-radius: 100%' alt=''>");
        } else {
            cUser.put("id",0);
            cUser.put("head","<img src='../head/0.jpg' width='100%' height='100%'" +
                    " style='border-radius: 100%' alt=''>");
        }
        response.put("user",cUser);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "success";
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Byte getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(Byte isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

}
