package org.gxfj.iknow.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.service.AnswerService;
import org.gxfj.iknow.util.ConstantUtil;
import org.gxfj.iknow.util.ImgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.gxfj.iknow.util.ServiceConstantUtil.JSON_RESULT_CODE_VERIFY_TEXT_FAIL;

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
        Map<String, Object> response = answerService.getQuestiontitle(questionId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 发布回答
     * @return SUCCESS
     */
    public String postAnswer() {
        Map<String, Object> response = answerService.postAnswer(questionId,content,isAnonymous);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 查看回答
     * @return SUCCESS
     */
    public String viewAnswer() {
        Map<String,Object> response = answerService.getRecommendAnswerForQuestion(questionId,answerId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 采纳回答
     * @return SUCCESS
     */
    public String adoptAnswer() {
        Map<String, Object> response = answerService.adoptAnswer(answerId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 取消匿名
     * @return SUCCESS
     */
    public String cancelAnonymous(){
        Map<String, Object> response = answerService.cancelAnonymous(answerId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 主页的推荐回答
     * @return SUCCESS
     */
    public String recommendAnswer(){
        Map<String, Object> response = answerService.getRecommendAnswerForUser(answerId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 赞同回答
     * @return SUCCESS
     */
    public String approveAnswer(){
        Map<String, Object> response = answerService.approveAnswer(answerId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String cancelApprove(){
        Map<String , Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response = new HashMap<>(ConstantUtil.RESPONSE_NUM);
        User user = (User) session.get(ConstantUtil.SESSION_USER);
        if(user == null){
            response.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.UN_LOGIN);
        }
        else if(!answerService.cancelApprove(answerId,user)){
            response.put(ConstantUtil.JSON_RETURN_CODE_NAME, 2 );
        }
        else{
            response.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }
    public String oppositionAnswer(){
        Map<String , Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response = new HashMap<>(ConstantUtil.RESPONSE_NUM);
        User user = (User) session.get(ConstantUtil.SESSION_USER);
        if(user == null){
            response.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.UN_LOGIN);
        }
        else if(!answerService.oppositionAnswer(answerId,user)){
            response.put(ConstantUtil.JSON_RETURN_CODE_NAME, 2 );
        }
        else{
            response.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String cancelOppose(){
        Map<String , Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response = new HashMap<>(ConstantUtil.RESPONSE_NUM);
        User user = (User) session.get(ConstantUtil.SESSION_USER);
        if(user == null){
            response.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.UN_LOGIN);
        }
        else if(!answerService.cancelOppose(answerId,user)){
            response.put(ConstantUtil.JSON_RETURN_CODE_NAME, 2 );
        }
        else{
            response.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String moreRecommend(){
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response=new HashMap<>(ConstantUtil.RESPONSE_NUM);
        User user = (User) session.get(ConstantUtil.SESSION_USER);
        if (user != null) {
            if(answerService.moreRecommendAnswer(user.getId(), ConstantUtil.RECOMMEND_ANSWERS_NUM_PER_TIME, start)==null) {
                response.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.NO_MORE);
            }
            else{
                response = answerService.moreRecommendAnswer(user.getId(), ConstantUtil.RECOMMEND_ANSWERS_NUM_PER_TIME,
                        start);
                response.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.SUCCESS);
            }

        } else {
            if(answerService.moreRecommendAnswer(null,ConstantUtil.RECOMMEND_ANSWERS_NUM_PER_TIME,start)==null){
                response.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.NO_MORE);
            }
            else{
                response = answerService.moreRecommendAnswer(null, ConstantUtil.RECOMMEND_ANSWERS_NUM_PER_TIME, start);
                response.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.SUCCESS);
            }
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String deleteAnswer(){
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response=new HashMap<>(ConstantUtil.RESPONSE_NUM);
        User user = (User) session.get(ConstantUtil.SESSION_USER);
        if (user == null) {
            response.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.UN_LOGIN);
        } else {
            if(answerService.isAnswerer(answerId,user)) {
                answerService.deleteAnswer(user, answerId);
                response.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
            } else{
                response.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.USER_IS_NOT_ANSWERER_TWO);
            }
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String getAnswerInfo(){
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response=new HashMap<>(ConstantUtil.RESPONSE_NUM);
        User user = (User) session.get(ConstantUtil.SESSION_USER);
        if (user == null) {
            response.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.UN_LOGIN);
        } else {
            if(answerService.isAnswerer(answerId,user)) {
                response.put("content",answerService.getAnswerContent(answerId));
                response.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
            } else{
                response.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.USER_IS_NOT_ANSWERER_TWO);
            }
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String updateAnswer(){
        Map<String, Object> response=new HashMap<>(ConstantUtil.RESPONSE_NUM);
        if (answerService.updateAnswerContent(answerId, content)) {
            response.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        } else {
            response.put(ConstantUtil.JSON_RETURN_CODE_NAME, JSON_RESULT_CODE_VERIFY_TEXT_FAIL);
        }
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
