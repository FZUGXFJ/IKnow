package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Achievement;

import java.util.List;

public interface AchievementDAO extends BaseDAO<Achievement> {

    /**
     *
     * @return
     */
    List<Achievement> list();
}
