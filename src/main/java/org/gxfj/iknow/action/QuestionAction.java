package org.gxfj.iknow.action;


import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.pojo.Question;
import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qmbx
 * @version 0.0.1
 */
@Controller
@Scope("prototype")
public class QuestionAction {
    private String questionTitle;
    private String questionContent;
    private Integer categoriesType;
    private Integer subjectType;
    private Integer majorType;
    private Byte isAnonymous;
    private InputStream inputStream;
    @Autowired
    QuestionService questionService;

    private enum ResultEnum {
        //处理成功
        SUCCESS,
        //没有登录
        UN_LOGIN,
        //缺少问题相关信息
        MISS_QUESTION_INF,
    }


    final static private int RESPONSE_NUM = 20;
    public String postQuestion() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response = new HashMap<>(RESPONSE_NUM);
        User user = (User) session.get("user");

        if (user == null) {
            response.put("resultCode",ResultEnum.UN_LOGIN);
        }
        else if (questionTitle == null || questionContent == null || categoriesType == null || subjectType == null
            || majorType == null || isAnonymous == null) {
            response.put("resultCode",ResultEnum.MISS_QUESTION_INF);
        }
        else {
            questionService.postQuestion(user, questionTitle, questionContent, categoriesType, subjectType, majorType
                    , isAnonymous);
            response.put("resultCode",ResultEnum.SUCCESS);
        }

        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "SUCCESS";
    }

    public String questionType() {
        Map<String, Object> response = questionService.getQuestionType();

        response.put("resultCode",ResultEnum.SUCCESS);
        System.out.println(JSON.toJSONString(response));
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "SUCCESS";
    }

}
