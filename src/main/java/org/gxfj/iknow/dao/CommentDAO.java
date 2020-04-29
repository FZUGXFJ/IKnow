package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Answer;
import org.gxfj.iknow.pojo.Comment;

import java.util.List;

public interface CommentDAO {
    public void add(Comment bean);

    public Comment get(Integer id);

    public void update(Comment bean);

    /**
     * 获得问题评论数
     * @param answerId 回答id
     * @return  问题评论数
     */
    public Integer getCount(Integer answerId);

    /**
     * 获得评论列表
     * @param answerId 回答id
     * @param start 开始index
     * @param length 记录数
     * @return  评论列表
     */
    public List<Comment> listByAnswerId(int answerId, int start, int length);
}
