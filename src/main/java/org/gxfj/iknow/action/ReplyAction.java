package org.gxfj.iknow.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.service.ReplyService;
import org.gxfj.iknow.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.gxfj.iknow.util.ConstantUtil.*;
import static org.gxfj.iknow.util.ServiceConstantUtil.JSON_RESULT_CODE_VERIFY_TEXT_FAIL;

@Controller
public class ReplyAction {
    private Integer commentId;
    private String content;
    private Integer replyTarget;
    private InputStream inputStream;
    private Integer replyId;
    private Integer sortType;
    @Autowired
    private ReplyService replyService;

    /*private final String SESSION_USER = "user";
    private final int SUCCESS = 0;
    private final int UN_LOGIN = 1;
    private final int MISS_COMMENT_INF = 2;
    private final String RESULT_CODE="resultCode";
    private final int NO_REPLYER = 2;*/

    public InputStream getInputStream() { return inputStream; }
    final static private int RESPONSE_NUM = 20;

    /**
     * 发送回复
     * @return SUCCESS
     */
    public String sendReply(){
        Map<String, Object> response = replyService.postReply(commentId, content, replyTarget);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String showMoreReply(){
        Map<String, Object> response = replyService.showAllReplys(commentId, sortType);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String approveReply(){
        Map<String, Object> response = replyService.approveReply(replyId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String cancelApprove(){
        Map<String, Object> response = replyService.cancelApprove(replyId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String deleteReply(){
        Map<String, Object> response= replyService.deleteReply(replyId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public Integer getReplyTarget() {
        return replyTarget;
    }

    public String getContent() {
        return content;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public void setReplyTarget(Integer replyTarget) {
        this.replyTarget = replyTarget;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }
}
