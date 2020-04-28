package org.gxfj.iknow.service;

import org.gxfj.iknow.pojo.User;

/**
 * @author 爱学习的水先生
 */
public interface CommentService {
    /**
     * 提交评论
     * user 提交用户
     * answerId 回答的id
     * content 评论的内容
     */
    public void postComment(User user, Integer answerId, String content);
}
