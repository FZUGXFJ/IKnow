package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Questionscenario;

public interface QuestionScenarioDAO {
    public void add(Questionscenario bean);

    public Questionscenario get(Integer id);

    public void update(Questionscenario bean);
}
