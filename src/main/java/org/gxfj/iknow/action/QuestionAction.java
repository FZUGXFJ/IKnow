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
import org.gxfj.iknow.util.ConstantUtil;
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

import static org.gxfj.iknow.util.ServiceConstantUtil.JSON_RESULT_CODE_VERIFY_TEXT_FAIL;

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

    /*
    private static final int QUESTION_SHOW_ANSWER_NUM = 10;
    private final int SUCCESS = 0;
    private final int UN_LOGIN = 1;
    private final int NO_MORE = 1;
    private final int MISS_QUESTION_INF = 2;
    private final int DEFAULT_SORT = 1;
    private final int USER_IS_NOT_QUESTION_ONWER_DELETE_FAULT = 2;
     */






    public InputStream getInputStream() {
        return inputStream;
    }

    final static private int RESPONSE_NUM = 20;

    /**
     * 发布问题
     * @return SUCCESS
     */
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
        Map<String, Object> response = questionService.postQuestion
                (questionTitle,questionContent,categoriesType,subjectType,majorType,isAnonymous);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 获得问题类型
     * @return SUCCESS
     */
    public String questionType() {
        Map<String, Object> response = questionService.getQuestionType();
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String viewQuestion() {
        Map<String, Object> response = questionService.getQuestion(questionId, 20, sort);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 取消采纳，没有实现这个功能
     * TODO 删除这个方法
     * @return SUCCESS
     */
    public String cancelAdopt() {
        Map<String, Object> response = questionService.cancelAdopt(questionId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String collectQuestion() {
        Map<String, Object> response = collectionService.collectProblem(questionId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String cancelCollect() {
        Map<String, Object> response = collectionService.cancelCollect(questionId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String moreAnswer() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        User user = (User) session.get(ConstantUtil.SESSION_USER);
        Integer sort1 =(Integer)session.get("answersort");
        if(sort1 ==null){
            sort1 = ConstantUtil.QUESTION_DEFAULT_SORT;
        }
        Map<String, Object> response = new HashMap<>(RESPONSE_NUM);
        if (questionService.moreAnswers(user,questionId,start, ConstantUtil.SHOW_ANSWERS_NUM,sort1 )==null){
            response.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.NO_MORE);
        }
        else {
            response=questionService.moreAnswers(user,questionId,start, ConstantUtil.SHOW_ANSWERS_NUM,sort1);
            response.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.SUCCESS);
        }

        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String deleteQuestion() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        User user = (User) session.get(ConstantUtil.SESSION_USER);
        Map<String, Object> response = new HashMap<>(RESPONSE_NUM);
        if (user == null) {
            response.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.UN_LOGIN);
        } else {
            if (questionService.deleteQuestion(user, questionId)) {
                response.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
            } else {
                response.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.USER_IS_NOT_QUESTION_ONWER_DELETE_FAULT);
            }
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String getQuestionInfo(){
        Map<String, Object> session = ActionContext.getContext().getSession();
        User user = (User) session.get(ConstantUtil.SESSION_USER);
        Map<String, Object> response;
        if(user == null){
            response = questionService.getQuestioninf(questionId,null);
        }
        else {
            response = questionService.getQuestioninf(questionId,user);
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String updateQuesiton(){
        Map<String, Object> response = questionService.updateQuesiton(questionId, questionTitle, questionContent,
                categoriesType, subjectType, majorType);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
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
