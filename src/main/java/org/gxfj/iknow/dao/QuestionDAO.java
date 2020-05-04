package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Question;

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
     * 问题数量
     * @param id 问题id
     * @return  数量
     */
    Integer getAnswerCount(Integer id);
}
