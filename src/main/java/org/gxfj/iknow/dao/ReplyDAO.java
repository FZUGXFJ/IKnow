package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Reply;

import java.util.List;

public interface ReplyDAO extends BaseDAO<Reply>{

    /**
     * 获得指定评论下的部分回复
     * @param commentId 评论id
     * @param start
     * @param count
     * @return  回复列表
     */
    List<Reply> listByCommentId(Integer commentId,Integer start,Integer count);

    /**
     * 获得指定评论下的全部回复
     * @param commentId 评论id
     * @return  回复列表
     */
    List<Reply> getAllReplies(Integer commentId);

    /**
     * 获得指定评论下的回复数
     * @param commentId 评论id
     * @return  回复数
     */
    Integer getCount(Integer commentId);
}
