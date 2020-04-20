package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Achievement;

public interface AchievementDAO {
    public void add(Achievement bean);

    public Achievement get(Integer id);

    public void update(Achievement bean);
}
