package org.gxfj.iknow.service;

import org.gxfj.iknow.pojo.Comment;
import org.gxfj.iknow.pojo.User;

import java.util.List;
import java.util.Map;

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

    /**
     * 获取回答的评论(评论数方法中指定）
     * answerId 回答Id
     */
    public Map<String, Object> getComments(Integer answerId);

}
