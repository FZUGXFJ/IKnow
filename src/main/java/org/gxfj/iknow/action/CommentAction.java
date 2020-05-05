package org.gxfj.iknow.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.service.CommentService;
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
public class CommentAction {
    private Integer answerId;
    private String content;
    private Integer commentId;
    private InputStream inputStream;
    @Autowired
    CommentService commentService;

    private final String SESSION_USER = "user";
    private final String RETURN_STRING = "success";
    private final String RESULT_CODE = "resultCode";
    private final int SUCCESS = 0;
    private final int UN_LOGIN = 1;
    private final int MISS_COMMENT_INF = 2;
    private final int RESULT_CODE_APPROVED = 2;
    private final int RESULT_CODE_NOT_APPROVED = 2;

    public InputStream getInputStream() { return inputStream; }

    final static private int RESPONSE_NUM = 20;

    public String postComment(){
        System.out.println(content);
        System.out.println(answerId);
        Map<String , Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response = new HashMap<>(RESPONSE_NUM);
        User user = (User) session.get(SESSION_USER);
        if(user == null){
            response.put(RESULT_CODE, UN_LOGIN);
        }
        else if(content == null){
            response.put(RESULT_CODE, MISS_COMMENT_INF );
        }
        else{
            commentService.postComment(user , answerId , content);
            response.put(RESULT_CODE, SUCCESS);
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return RETURN_STRING;
    }

    public String viewComments(){
        Map<String , Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response;
        response = commentService.getComments(answerId, (User) session.get(SESSION_USER));
        response.put(RESULT_CODE, SUCCESS);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return RETURN_STRING;
    }

    public String approveComment() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response = new HashMap<>(RESPONSE_NUM);
        User user = (User) session.get(SESSION_USER);

        if (user != null) {
            if (commentService.approveComment(user, commentId)) {
                response.put(RESULT_CODE, SUCCESS);
            } else {
                response.put(RESULT_CODE, RESULT_CODE_APPROVED);
            }
        } else {
            response.put(RESULT_CODE, UN_LOGIN);
        }

        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return RETURN_STRING;
    }

    public String cancelApprove() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response = new HashMap<>(RESPONSE_NUM);
        User user = (User ) session.get(SESSION_USER);

        if (user != null) {
            if (commentService.cancelApprove(user, commentId)) {
                response.put(RESULT_CODE, SUCCESS);
            } else {
                response.put(RESULT_CODE, RESULT_CODE_NOT_APPROVED);
            }
        } else {
            response.put(RESULT_CODE, UN_LOGIN);
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return RETURN_STRING;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
