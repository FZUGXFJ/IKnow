package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Achievementrecord;

public interface AchievementRecordDAO {
    public void add(Achievementrecord bean);

    public Achievementrecord get(Integer id);

    public void update(Achievementrecord bean);
}
