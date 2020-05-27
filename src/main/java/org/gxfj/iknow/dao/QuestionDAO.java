package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Comment;
import org.gxfj.iknow.pojo.Question;

import java.util.List;

public interface QuestionDAO extends BaseDAO<Question>{

    /**
     * 删除
     * @param bean 类
     */
    void delete(Question bean);
    /**
     * 获得问题状态
     * @param questionId 问题id
     * @return  状态码
     */
    Integer getQuestionStateId(Integer questionId);

    /**
     * 获得未删除的问题的数量
     * @param id 问题id
     * @return  数量
     */
    Integer getAnswerCount(Integer id);

    /**
     * 在未删除的数据中查找记录
     * @param id 要查找的记录的主键
     * @return 没有则为null
     */
    Question getNotDelete(Integer id);

    /**
     * 列出指定用户id提出的问题
     * @param userId 用户ID
     * @param start 起始位置
     * @param length 问题个数
     * @return 问题列表没有则为null
     */
    List<Question> listPartByUserId(Integer userId, Integer start, Integer length);

    /**
     * 活得用户发布的所有问题
     * @param userId 用户id
     * @return 问题列表，没有为null
     */
    List<Question> listPartByUserId(Integer userId);
}
