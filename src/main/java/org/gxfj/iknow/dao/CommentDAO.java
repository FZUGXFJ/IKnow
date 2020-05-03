package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Answer;
import org.gxfj.iknow.pojo.Comment;

import java.util.List;

public interface CommentDAO extends BaseDAO<Comment>{

    /**
     * 获得问题评论数
     * @param answerId 回答id
     * @return  问题评论数
     */
    Integer getCount(Integer answerId);

    /**
     * 获得问题评论列表
     * @param answerId 回答id
     * @param start 开始index
     * @param length 记录数
     * @return  评论列表
     */
    List<Comment> listByAnswerId(int answerId, int start, int length);

    /**
     * 获得问题所有评论列表
     * @param answerId 回答id
     * @return  评论列表
     */
    List<Comment> getCommentsByAnswerId(Integer answerId);

}
