package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Question;

public interface QuestionDAO extends BaseDAO<Question>{

    /**
     * 获得问题状态
     * @param questionID 问题id
     * @return  状态码
     */
    Integer getQuestionStateId(Integer questionID);

    /**
     * 问题数量
     * @param id 问题id
     * @return  数量
     */
    Integer getAnswerCount(Integer id);
}
