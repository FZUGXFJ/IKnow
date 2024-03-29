package org.gxfj.iknow.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.service.AnswerService;
import org.gxfj.iknow.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
    private Integer start;

    /*
    private final String SESSION_USER = "user";
    private final int SUCCESS = 0;
    private final int UN_LOGIN = 1;
    private final int NO_MORE = 1;
    private final int MISS_QUESTIONID = 2;
    private final int MISS_ANSWER_IF = 3;
    private final int USER_IS_NOT_QUESTIONER = 2;
    private final int USER_IS_NOT_ANSWERER = 1;
    private final int USER_IS_NOT_ANSWERER_TWO = 2;

    final static private int RESPONSE_NUM = 20;
    private static int MAP_NUM = 20;
    */

    @Autowired
    AnswerService answerService;

    /**
     * 获得问题标题
     * @return
     */
    public String questionTitle(){

        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response = answerService.getQuestiontitle(user, questionId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 发布回答
     * @return SUCCESS
     */
    public String postAnswer() {
        User user = (User)ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response = answerService.postAnswer(user, questionId,content,isAnonymous);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 查看回答
     * @return SUCCESS
     */
    public String viewAnswer() {
        User user = (User)ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String,Object> response = answerService.viewAnswer(user, questionId,answerId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 采纳回答
     * @return SUCCESS
     */
    public String adoptAnswer() {
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response = answerService.adoptAnswerService(user, answerId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 取消匿名
     * @return SUCCESS
     */
    public String cancelAnonymous(){
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response = answerService.cancelAnonymous(user, answerId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 主页的推荐回答
     * @return SUCCESS
     */
    public String recommendAnswer(){
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response = answerService.getRecommendAnswerForUser(user, 20);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 赞同回答
     * @return SUCCESS
     */
    public String approveAnswer(){
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response = answerService.approveAnswer(user, answerId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 取消赞同
     * @return SUCCESS
     */
    public String cancelApprove(){
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response = answerService.cancelApprove(user, answerId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 反对回答
     * @return SUCCESS
     */
    public String oppositionAnswer(){
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response = answerService.oppositionAnswer(user, answerId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 取消反对
     * @return SUCCESS
     */
    public String cancelOppose(){
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response = answerService.cancelOppose(user, answerId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 获得更多推荐的回答
     * @return
     */
    public String moreRecommend(){
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response=answerService.moreRecommendAnswer(user,
                ConstantUtil.RECOMMEND_ANSWERS_NUM_PER_TIME,start);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 删除回答
     * @return SUCCESS
     */
    public String deleteAnswer(){
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response =answerService.deleteAnswer(user, answerId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 获得回答的内容
     * @return
     */
    public String getAnswerInfo(){
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response = answerService.getAnswerContent(user, answerId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 编辑问题
     * @return SUCCESS
     */
    public String updateAnswer(){
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response=answerService.updateAnswerContent(user, answerId,content);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
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

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }
}
