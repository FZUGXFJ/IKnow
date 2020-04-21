package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Questiontype;

public interface QuestionTypeDAO {
    public void add(Questiontype bean);

    public Questiontype get(Integer id);

    public void update(Questiontype bean);
}
