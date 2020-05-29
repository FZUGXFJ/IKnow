package org.gxfj.iknow.action;


import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.dao.CollectionProblemDAO;
import org.gxfj.iknow.dao.QuestionDAO;
import org.gxfj.iknow.dao.UserDAO;
import org.gxfj.iknow.pojo.Collectionproblem;
import org.gxfj.iknow.pojo.Question;
import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.service.CollectionService;
import org.gxfj.iknow.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

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
    private Integer start;
    private Integer sort;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CollectionService collectionService;

    private static final int QUESTION_SHOW_ANSWER_NUM = 10;
    private final int SUCCESS = 0;
    private final int UN_LOGIN = 1;
    private final int NO_MORE = 1;
    private final int MISS_QUESTION_INF = 2;
    private final int DEFAULT_SORT = 1;

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
            Integer x=questionService.postQuestion(user, questionTitle, questionContent, categoriesType, subjectType
                    , majorType, isAnonymous);
            response.put("questionId",x);
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
        Integer Sort;
        if (sort==null){
            session.put("sort",DEFAULT_SORT);
            Sort=DEFAULT_SORT;
        }
        else {
            Sort=sort;
            session.put("sort",sort);
        }
        //题主
        User viewUser = questionService.get(questionId);
        boolean isQuestionUser = (user != null && user.getId().equals(viewUser.getId()));
        Map<String, Object> response = new HashMap<>(RESPONSE_NUM);
        response.put("question",questionService.getQuestion(user,questionId, 20,Sort));
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

    public String cancelAdopt() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        User user = (User) session.get("user");
        User viewUser = questionService.get(questionId);
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

    public String collectQuestion() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        User user = (User) session.get("user");
        Map<String, Object> response = new HashMap<>(RESPONSE_NUM);
        if (user == null) {
            response.put("resultCode",UN_LOGIN);
        } else if(collectionService.getCollectionQuestion(user.getId(),questionId) != null){
            //resultCode = 2 表示已收藏无法再次收藏
            response.put("resultCode", 2);
        } else {
            collectionService.collectProblem(user,questionId);
            response.put("resultCode", SUCCESS);
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "success";
    }

    public String cancelCollect() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        User user = (User) session.get("user");
        Map<String, Object> response = new HashMap<>(RESPONSE_NUM);
        if (user == null) {
            response.put("resultCode",UN_LOGIN);
        } else if(collectionService.getCollectionQuestion(user.getId(),questionId) == null){
            //resultCode = 2 表示未收藏无法取消收藏
            response.put("resultCode", 2);
        } else {
            collectionService.cancelCollect(user,questionId);
            response.put("resultCode", SUCCESS);
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "success";
    }

    public String moreAnswer() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        User user = (User) session.get("user");
        Integer Sort=(Integer)session.get("sort");
        if(Sort==null){
            Sort=DEFAULT_SORT;
        }
        Map<String, Object> response = new HashMap<>(RESPONSE_NUM);
        if (questionService.moreAnswers(user,questionId,start,20,Sort)==null){
            response.put("resultCode",NO_MORE);
        }
        else {
            response=questionService.moreAnswers(user,questionId,start,20,Sort);
            response.put("resultCode",SUCCESS);
        }

        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "success";
    }

    public String deleteQuestion() {
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

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
