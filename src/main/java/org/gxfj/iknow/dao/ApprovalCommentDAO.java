package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Approvalcomment;

public interface ApprovalCommentDAO extends BaseDAO<Approvalcomment>{

    /**
     * 根据用户id和评论id获得对应的点赞记录
     * @param userId 用户id
     * @param commentId 评论id
     * @return 查询到的记录，如果没有，则为null
     */
    Approvalcomment get(Integer userId, Integer commentId);

    /**
     * 删除
     * @param approvalcomment 赞同的id
     */
    void delete(Approvalcomment approvalcomment);

    /**
     * 获得指定评论id的赞同数
     * @param commentId 评论id
     * @return  评论赞同数
     */
    Integer getCount(Integer commentId);


}
