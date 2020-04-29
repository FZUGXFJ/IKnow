package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Answer;

import java.util.List;

public interface AnswerDAO {
    public Integer add(Answer bean);

    public Answer get(Integer id);

    public void update(Answer bean);

    public List getAnswersbyQid(Integer qid);

    /**
     * 获得回答列表
     * @param questionId 问题id
     * @param start 开始index
     * @param length 记录数
     * @return  回答列表
     */
    public List<Answer> listByQuestionId(int questionId,int start,int length);
}
