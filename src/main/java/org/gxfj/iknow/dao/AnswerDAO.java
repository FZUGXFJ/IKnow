package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Answer;

import java.util.List;

public interface AnswerDAO {
    public void add(Answer bean);

    public Answer get(Integer id);

    public void update(Answer bean);

    public List getAnswersbyQid(Integer qid);
}
