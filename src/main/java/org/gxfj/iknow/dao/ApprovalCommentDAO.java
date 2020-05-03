package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Approvalcomment;

public interface ApprovalCommentDAO {
    public void add(Approvalcomment bean);

    /**
     * 根据评论点赞记录的id查询评论点赞记录
     * @param id 评论点赞记录的id
     * @return
     */
    public Approvalcomment get(Integer id);


    /**
     * 根据用户id和评论id获得对应的点赞记录
     * @param userId 用户id
     * @param commentId 评论id
     * @return 查询到的记录，如果没有，则为null
     */
    public Approvalcomment get(Integer userId, Integer commentId);

    public void delete(Approvalcomment approvalcomment);

    public void update(Approvalcomment bean);

    /**
     * 获得指定评论id的赞同数
     * @param commentId 评论id
     * @return  评论赞同数
     */
    public Integer getCount(Integer commentId);


}
