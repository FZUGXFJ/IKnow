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
    private String title;
    private String context;
    private String categoryType;
    private String subjectType;
    private String majorType;
    private String isAnonymous;
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


    final static private int responseNum = 12;
    public String addQuestion() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response = new HashMap<>(responseNum);
        User user = (User) session.get("user");

        if (user == null) {
            response.put("resultCode",ResultEnum.UN_LOGIN);
        }
        else if (title == null || context == null || categoryType == null || subjectType == null
            || majorType == null || isAnonymous == null) {
            response.put("resultCode",ResultEnum.MISS_QUESTION_INF);
        }
        else {

            Question question = new Question();
            question.setUserByUserId(user);
            question.setTitle(title);
            question.setContent(context);
            //TODO 解决Question的构建
            question.setQuestiontypeByTypeId();
            response.put("resultCode",ResultEnum.SUCCESS);
        }

        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "SUCCESS";
    }


}
