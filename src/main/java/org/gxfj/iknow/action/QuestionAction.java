package org.gxfj.iknow.action;


import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.service.CollectionService;
import org.gxfj.iknow.service.QuestionService;
import org.gxfj.iknow.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
    private String keyword;
    private Integer userId;
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
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response = questionService.postQuestion(user, questionTitle,questionContent,
                categoriesType, subjectType,majorType,isAnonymous);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 获得问题类型
     * @return SUCCESS
     */
    public String questionType() {
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response = questionService.getQuestionType(user);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String viewQuestion() {
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response = questionService.viewQuestionService(user, questionId, 20, sort);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 取消匿名，没有实现这个功能
     * @return SUCCESS
     */
    public String cancelAnonymous() {
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response = questionService.cancelAnonymous(user, questionId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 收藏问题
     * @return SUCCESS
     */
    public String collectQuestion() {
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response = collectionService.collectProblem(user, questionId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 取消收藏
     * @return SUCCESS
     */
    public String cancelCollect() {
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response = collectionService.cancelCollect(user, questionId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 查看更多回答
     * @return SUCCESS
     */
    public String moreAnswer() {
        Map<String, Object> response = questionService.moreAnswers(questionId, start, 20, sort);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 删除问题
     * @return SUCCESS
     */
    public String deleteQuestion() {
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response =questionService.deleteQuestion(user, questionId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 获得问题的信息
     * @return SUCCESS
     */
    public String getQuestionInfo(){
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response = questionService.getQuestioninf(user, questionId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String updateQuesiton(){
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response = questionService.updateQuesiton(user, questionId, questionTitle, questionContent,
                categoriesType, subjectType, majorType);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String findUser(){
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response = questionService.findUser(user, keyword);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 邀请回答
     * @return SUCCESS
     */
    public String inviteAnswer(){
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response = questionService.inviteAnswer(user, questionId, userId);
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
