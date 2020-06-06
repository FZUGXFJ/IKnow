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
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response = commentService.postComment(user, answerId, content);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 查看评论
     * @return SUCCESS
     */
    public String viewComments(){
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response = commentService.viewComments(user, answerId, sort);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 赞同评论
     * @return SUCCESS
     */
    public String approveComment() {
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response = commentService.approveComment(user, commentId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 取消赞同
     * @return SUCCESS
     */
    public String cancelApprove() {
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response = commentService.cancelApprove(user, commentId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 查看更多评论
     * @return SUCCESS
     */
    public String moreComment(){
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response= commentService.moreComments(user, answerId, start, sort);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 删除评论
     * @return SUCCESS
     */
    public String deleteComment(){
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String, Object> response= commentService.deleteComment(user, commentId);
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
