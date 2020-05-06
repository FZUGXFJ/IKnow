package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Answer;
import org.gxfj.iknow.pojo.Comment;

import java.util.List;

public interface AnswerDAO extends BaseDAO<Answer>{

    /**
     * 获得未删除的回答列表
     * @param questionId 问题id
     * @return  回答列表(所有回答）
     */
    List<Answer> getAnswersbyQid(Integer questionId);

    /**
     * 获得未删除的回答列表
     * @param questionId 问题id
     * @param start 开始index
     * @param length 记录数
     * @return  回答列表（部分回答）
     */
    List<Answer> listByQuestionId(int questionId,int start,int length);

    /**
     * 得到未删除的问题列表
     * @param count 数量
     * @param start 开始元素
     * @return 问题列表
     */
    List<Answer> list(Integer start,Integer count);

    /**
     * 在未删除的数据中查找记录
     * @param id 要查找的记录的主键
     * @return 没有则为null
     */
    Answer getNotDelete(Integer id);
}
