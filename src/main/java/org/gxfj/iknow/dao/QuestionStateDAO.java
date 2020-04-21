package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Questionstate;

public interface QuestionStateDAO {
    public void add(Questionstate bean);

    public Questionstate get(Integer id);

    public void update(Questionstate bean);
}
