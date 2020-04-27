package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Question;

public interface QuestionDAO {
    public Question get(Integer id);

    public void update(Question bean);

    public void add(Question bean);

    public void delete(Question bean);

    public Integer getQuestionStateId(Integer questionID);
}
