package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Answer;

import java.util.List;

public interface AnswerDAO {
    public Integer add(Answer bean);

    public Answer get(Integer id);

    public void update(Answer bean);

    /**
     * 获得回答列表
     * @param questionId 问题id
     * @return  回答列表(所有回答）
     */
    public List<Answer> getAnswersbyQid(Integer questionId);

    /**
     * 获得回答列表
     * @param questionId 问题id
     * @param start 开始index
     * @param length 记录数
     * @return  回答列表（部分回答）
     */
    public List<Answer> listByQuestionId(int questionId,int start,int length);
}
