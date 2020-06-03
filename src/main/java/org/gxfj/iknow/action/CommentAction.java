package org.gxfj.iknow.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.service.CommentService;
import org.gxfj.iknow.util.ConstantUtil;
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
public class CommentAction {
    private Integer answerId;
    private String content;
    private Integer commentId;
    private InputStream inputStream;
    private Integer start;
    private Integer sort;
    @Autowired
    CommentService commentService;

    /*
    private final String SESSION_USER = "user";
    private final String RETURN_STRING = "success";
    private final String RESULT_CODE = "resultCode";
    private final int SUCCESS = 0;
    private final int UN_LOGIN = 1;
    private final int NO_MORE = 1;
    private final int MISS_COMMENT_INF = 2;
    private final int RESULT_CODE_APPROVED = 2;
    private final int RESULT_CODE_NOT_APPROVED = 2;
    private final int DEFAULT_SORT = 0;
    private final int NO_COMMENTER = 2;
     */




    public InputStream getInputStream() { return inputStream; }

    final static private int RESPONSE_NUM = 20;

    /**
     * 提交评论
     * @return SUCCUESS
     */
    public String postComment(){
        Map<String, Object> response = commentService.postComment(answerId, content);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 查看评论
     * @return SUCCESS
     */
    public String viewComments(){
        Map<String, Object> response = commentService.getComments(answerId, sort);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String approveComment() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response = new HashMap<>(RESPONSE_NUM);
        User user = (User) session.get(ConstantUtil.SESSION_USER);

        if (user != null) {
            if (commentService.approveComment(user, commentId)) {
                response.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
            } else {
                response.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.RESULT_CODE_APPROVED);
            }
        } else {
            response.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.UN_LOGIN);
        }

        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String cancelApprove() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response = new HashMap<>(RESPONSE_NUM);
        User user = (User ) session.get(ConstantUtil.SESSION_USER);

        if (user != null) {
            if (commentService.cancelApprove(user, commentId)) {
                response.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
            } else {
                response.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.RESULT_CODE_NOT_APPROVED);
            }
        } else {
            response.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.UN_LOGIN);
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }
    public String moreComment(){
        Map<String , Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response= new HashMap<>(RESPONSE_NUM);
        Integer sort1 =(Integer)session.get("commentsort");
        if(sort1 ==null){
            sort1 =ConstantUtil.COMMENT_DEFAULT_SORT;
        }
        if (commentService.moreComments(answerId, (User) session.get(ConstantUtil.SESSION_USER),start,sort1 )==null){
            response.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.NO_MORE);
        }
        else {
            response=commentService.moreComments(answerId, (User) session.get(ConstantUtil.SESSION_USER),start,sort1 );
            response.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String deleteComment(){
        Map<String , Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response= new HashMap<>(RESPONSE_NUM);
        User user=(User)session.get("user");
        if(user==null){
            response.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.UN_LOGIN);
        }
        else {
            if (commentService.deleteComment(commentId,user)){
                response.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.SUCCESS);
            }
            else {
                response.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.NO_COMMENTER);
            }
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
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

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getStart() {
        return start;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getSort() {
        return sort;
    }
}
