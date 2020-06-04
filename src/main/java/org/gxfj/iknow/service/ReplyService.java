package org.gxfj.iknow.service;

import org.gxfj.iknow.pojo.User;

import java.util.Map;

/**
 * @author erniumo ， hhj
 */
public interface ReplyService {


    /**
     * 提交回复
     * @param commentId 评论的id
     * @param content 评论的内容
     * @param replyTarget **
     * @return 回复id
     */
    Map<String, Object> postReply(Integer commentId, String content, Integer replyTarget);

    /**
     * 查看全部回复
     * @param commentId 评论
     * @param sortType 排序的方式
     * @return json格式的评论信息和所有回复信息
     */
    Map<String , Object> showAllReplys(Integer commentId, Integer sortType);

    /**
     * 点赞回复
     * @param replyId 回复id
     * @return 是否点赞
     */
    Map<String, Object> approveReply(Integer replyId);

    /**
     * 取消点赞
     * @param replyId 回复id
     * @return 是否取消成功
     */
    Map<String, Object> cancelApprove(Integer replyId);

    /**
     * 删除回复
     * @param replyId 回复id
     * @return 是否删除
     */
    Map<String, Object> deleteReply(Integer replyId);
}
