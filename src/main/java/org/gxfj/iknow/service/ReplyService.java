package org.gxfj.iknow.service;

import org.gxfj.iknow.pojo.User;

import java.util.Map;

/**
 * @author erniumo
 * @author
 */
public interface ReplyService {
    /**
     * 提交回复
     * user 提交用户
     * commentId 评论的id
     * content 评论的内容
     */
    public void postReply(Integer commentID, String content, Integer replyTarget, User user);
}
