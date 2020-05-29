package org.gxfj.iknow.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

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

    private final String SESSION_USER = "user";
    private final int SUCCESS = 0;
    private final int UN_LOGIN = 1;
    private final int MISS_COMMENT_INF = 2;

    public InputStream getInputStream() { return inputStream; }
    final static private int RESPONSE_NUM = 20;
    public String sendReply(){
        Map<String , Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response = new HashMap<>(RESPONSE_NUM);
        User user = (User) session.get("user");
        if(user == null){
            response.put("resultCode" , UN_LOGIN);
        } else if(content == null){
            response.put("resultCode" , MISS_COMMENT_INF );
        } else{
            replyService.postReply(commentId,content, replyTarget,user);
            response.put("resultCode" , SUCCESS);
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "success";
    }

    public String showMoreReply(){
        Map<String , Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response;
        User user = (User) session.get(SESSION_USER);
        response = replyService.showAllReplys(commentId, user, sortType);
        //在session中保存排序的方式
        session.put("sortType", sortType);
        response.put("resultCode" , SUCCESS);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "success";
    }

    public String approveReply(){
        Map<String , Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response = new HashMap<>(RESPONSE_NUM);
        User user = (User) session.get(SESSION_USER);
        if(user == null){
            response.put("resultCode" , UN_LOGIN);
        }
        else if(!replyService.approveReply(replyId,user)){
            response.put("resultCode" , 2 );
        }
        else{
            response.put("resultCode" , SUCCESS);
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "success";
    }

    public String cancelApprove(){
        Map<String , Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response = new HashMap<>(RESPONSE_NUM);
        User user = (User) session.get(SESSION_USER);
        if(user == null){
            response.put("resultCode" , UN_LOGIN);
        }
        else if(!replyService.cancelApprove(replyId,user)){
            response.put("resultCode" , 2 );
        }
        else{
            response.put("resultCode" , SUCCESS);
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "success";
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
