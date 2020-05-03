package org.gxfj.iknow.service;

import org.gxfj.iknow.pojo.Comment;
import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.pojo.ViewCommentsJSON;

import java.util.List;
import java.util.Map;

/**
 * @author 爱学习的水先生
 */
public interface CommentService {
    /**
     * 提交评论
     * @user 提交用户
     * @answerId 回答的id
     * @content 评论的内容
     */
    public void postComment(User user, Integer answerId, String content);

    /**
     * 获取回答的评论(评论数方法中指定）
     * @answerId 回答Id
     * @user 发起查看请求的用户
     * @return 构造好的MAP
     */
    public Map<String, Object> getComments(Integer answerId, User user);

    /**
     * 验证用户是否点过赞，如果点过，则更新数据库中点赞相关记录
     * @param user 点赞用户
     * @param commentId 被点赞的评论的id
     * @return 点赞成功true,点赞失败false
     */
    public boolean approveComment(User user, Integer commentId);

    /**
     * 验证用户是否点过赞，如果点过，则修改数据库中的点赞信息
     * @param user 取消点赞的用户
     * @param commentId 被点赞的评论的id
     * @return 取消成功返回true，失败返回false
     */
    public boolean cancelApprove(User user, Integer commentId);

}
