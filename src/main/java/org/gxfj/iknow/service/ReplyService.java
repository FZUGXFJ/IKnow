package org.gxfj.iknow.service;

import org.gxfj.iknow.pojo.User;

import java.util.Map;

/**
 * @author erniumo ， hhj
 * @author
 */
public interface ReplyService {
    /**
     *
     * user 提交用户
     * commentId
     * content
     */

    /**
     * 提交回复
     * @param commentId 评论的id
     * @param content 评论的内容
     * @param replyTarget **
     * @param user 提交用户
     */
    public void postReply(Integer commentId, String content, Integer replyTarget, User user);

    /**
     * 查看全部回复
     * @param commentId 评论
     * @param visitor 浏览的用户
     * @param sortType 排序的方式
     * @return json格式的评论信息和所有回复信息
     */
    public Map<String , Object> showAllReplys(Integer commentId, User visitor, Integer sortType);

    /**
     * 点赞回复
     * @param replyId 回复id
     * @param user 点赞用户
     * @return 是否点赞
     */
    public boolean approveReply(Integer replyId,User user);

    /**
     * 取消点赞
     * @param replyId 回复id
     * @param user 用户
     * @return 是否取消成功
     */
    public boolean cancelApprove(Integer replyId,User user);

    /**
     * 删除回复
     * @param replyId 回复id
     * @param user 用户
     * @return 是否删除
     */
    public boolean deleteReply(Integer replyId,User user);
}
