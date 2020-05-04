package org.gxfj.iknow.action;


import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.dao.CollectionProblemDAO;
import org.gxfj.iknow.dao.QuestionDAO;
import org.gxfj.iknow.dao.UserDAO;
import org.gxfj.iknow.pojo.Collectionproblem;
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
    private Integer questionId;
    private String questionTitle;
    private String questionContent;
    private Integer categoriesType;
    private Integer subjectType;
    private Integer majorType;
    private Byte isAnonymous;
    private InputStream inputStream;
    @Autowired
    QuestionService questionService;
    @Autowired
    QuestionDAO questionDAO;
    @Autowired
    CollectionProblemDAO collectionProblemDAO;
    @Autowired
    UserDAO userDAO;

    private static final int QUESTION_SHOW_ANSWER_NUM = 10;
    private final int SUCCESS = 0;
    private final int UN_LOGIN = 1;
    private final int MISS_QUESTION_INF = 2;

    public InputStream getInputStream() {
        return inputStream;
    }

    final static private int RESPONSE_NUM = 20;
    public String postQuestion() {
        /*
        测试用输出到屏幕
         */
        System.out.println(questionTitle);
        System.out.println(questionContent);
        System.out.println(isAnonymous);
        System.out.println(categoriesType);
        System.out.println(subjectType);
        System.out.println(majorType);
        /////////////
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response = new HashMap<>(RESPONSE_NUM);
        User user = (User) session.get("user");
        if (user == null) {
            response.put("resultCode",UN_LOGIN);
        }
        else if (questionTitle == null || questionContent == null || categoriesType == null || subjectType == null
            || majorType == null || isAnonymous == null) {
            response.put("resultCode",MISS_QUESTION_INF);
        }
        else {
            questionService.postQuestion(user, questionTitle, questionContent, categoriesType, subjectType, majorType
                    , isAnonymous);
            response.put("resultCode",SUCCESS);
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "success";
    }

    public String questionType() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response = new HashMap<>(RESPONSE_NUM);
        User user = (User) session.get("user");
        if (user == null) {
            response.put("resultCode",UN_LOGIN);
        } else {
            response = questionService.getQuestionType();
            response.put("resultCode", SUCCESS);
        }
        System.out.println(JSON.toJSONString(response));
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "success";
    }

    public String viewQuestion() {
        System.out.println(questionId);
        Map<String, Object> session = ActionContext.getContext().getSession();
        User user = (User) session.get("user");
        //题主
        User viewUser = questionDAO.get(questionId).getUserByUserId();
        boolean isQuestionUser = (user != null && user.getId().equals(viewUser.getId()));
        Map<String, Object> response = new HashMap<>(RESPONSE_NUM);
        response.put("question",questionService.getQuestion(user,questionId, 10));
        response.put("resultCode",SUCCESS);
        if(user == null || !isQuestionUser){
            response.put("viewerIsOwner",0);
        }
        if(isQuestionUser){
            response.put("viewerIsOwner",1);
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "success";
    }

    public String cancelAdopt(){
        Map<String, Object> session = ActionContext.getContext().getSession();
        User user = (User) session.get("user");
        User viewUser = questionDAO.get(questionId).getUserByUserId();
        Map<String, Object> response = new HashMap<>(RESPONSE_NUM);
        boolean isQuestionUser = (user != null && user.getId().equals(viewUser.getId()));
        if(user == null || !isQuestionUser){
            response.put("resultCode",1);
        }else{
            questionService.cancelAdopt(questionId);
            response.put("resultCode",SUCCESS);
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "success";
    }

    public String collectQuestion(){
        Map<String, Object> session = ActionContext.getContext().getSession();
        User user = (User) session.get("user");
        Map<String, Object> response = new HashMap<>(RESPONSE_NUM);
        if (user == null) {
            response.put("resultCode",UN_LOGIN);
        } else if(collectionProblemDAO.getCollectionQuestion(user.getId(),questionId) != null){
            //resultCode = 2 表示已收藏无法再次收藏
            response.put("resultCode", 2);
        } else {
            questionService.collectProblem(user,questionId);
            response.put("resultCode", SUCCESS);
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "success";
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public Integer getCategoriesType() {
        return categoriesType;
    }

    public void setCategoriesType(Integer categoriesType) {
        this.categoriesType = categoriesType;
    }

    public Integer getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(Integer subjectType) {
        this.subjectType = subjectType;
    }

    public Integer getMajorType() {
        return majorType;
    }

    public void setMajorType(Integer majorType) {
        this.majorType = majorType;
    }

    public Byte getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(Byte isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }
}
