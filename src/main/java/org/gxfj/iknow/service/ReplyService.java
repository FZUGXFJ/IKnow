package org.gxfj.iknow.service;

import org.gxfj.iknow.pojo.User;

import java.util.Map;

/**
 * @author erniumo ， hhj
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

    /**
     * 查看全部回复
     * @param commentId 评论
     * @param visitor 浏览的用户
     * @return json格式的评论信息和所有回复信息
     */
    public Map<String , Object> showAllReplys(Integer commentId, User visitor);

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

}
