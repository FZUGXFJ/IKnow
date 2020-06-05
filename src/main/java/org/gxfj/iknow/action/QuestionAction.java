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
     * 取消匿名，没有实现这个功能
     * @return SUCCESS
     */
    public String cancelAnonymous() {
        Map<String, Object> response = questionService.cancelAnonymous(questionId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 收藏问题
     * @return SUCCESS
     */
    public String collectQuestion() {
        Map<String, Object> response = collectionService.collectProblem(questionId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 取消收藏
     * @return SUCCESS
     */
    public String cancelCollect() {
        Map<String, Object> response = collectionService.cancelCollect(questionId);
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
        Map<String, Object> response =questionService.deleteQuestion(questionId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 获得问题的信息
     * @return SUCCESS
     */
    public String getQuestionInfo(){
        Map<String, Object> response = questionService.getQuestioninf(questionId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String updateQuesiton(){
        Map<String, Object> response = questionService.updateQuesiton(questionId, questionTitle, questionContent,
                categoriesType, subjectType, majorType);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String findUser(){
        Map<String, Object> response = questionService.findUser(keyword);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 邀请回答
     * @return SUCCESS
     */
    public String inviteAnswer(){
        Map<String, Object> response = questionService.inviteAnswer(questionId, userId);
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
