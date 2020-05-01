package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Approvalcomment;

public interface ApprovalCommentDAO {
    public void add(Approvalcomment bean);

    public Approvalcomment get(Integer id);

    public void update(Approvalcomment bean);

    /**
     * 获得指定评论id的赞同数
     * @param commentId 评论id
     * @return  评论赞同数
     */
    public Integer getCount(Integer commentId);
}
