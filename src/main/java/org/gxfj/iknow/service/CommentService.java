package org.gxfj.iknow.service;

import org.gxfj.iknow.pojo.User;

import java.util.Map;

/**
 * @author 爱学习的水先生
 */
public interface CommentService {
    /**
     * 提交评论
     * @param answerId 回答ID
     * @param content 用户输入的回答内容
     * @return 评论id
     */
    public Map<String, Object> postComment(User user, Integer answerId, String content);

    /**
     * 获取回答的评论(评论数方法中指定）
     * @param answerId 回答Id
     * @param sort 排序方式
     * @return 构造好的MAP
     */
    public Map<String, Object> viewComments(User user, Integer answerId, Integer sort);

    /**
     * 验证用户是否点过赞，如果点过，则更新数据库中点赞相关记录
     * @param commentId 被点赞的评论的id
     * @return 点赞成功true,点赞失败false
     */
    public Map<String, Object> approveComment(User user, Integer commentId);

    /**
     * 验证用户是否点过赞，如果点过，则修改数据库中的点赞信息
     * @param commentId 被点赞的评论的id
     * @return 取消成功返回true，失败返回false
     */
    public Map<String, Object> cancelApprove(User user, Integer commentId);

    /**
     * 获取更多回答的评论(评论数方法中指定）
     * @param answerId 回答Id
     * @param start 起始地址
     * @param sort     排序的方式，0为正序，1为逆序
     * @return 构造好的MAP
     */
    public Map<String, Object> moreComments(User user, Integer answerId, Integer start, Integer sort);

    /**
     * 删除评论
     * @param commentId 评论id
     * @return 是否删除
     */
    public Map<String, Object> deleteComment(User user, Integer commentId);
}
