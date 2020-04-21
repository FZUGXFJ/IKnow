package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Oppositionanswer;

public interface OppositionAnswerDAO {
    public void add(Oppositionanswer bean);

    public Oppositionanswer get(Integer id);

    public void update(Oppositionanswer bean);
}
