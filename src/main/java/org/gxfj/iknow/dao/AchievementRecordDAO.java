package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Achievement;
import org.gxfj.iknow.pojo.Achievementrecord;

import java.util.List;

public interface AchievementRecordDAO extends BaseDAO<Achievementrecord>{

    /**
     * 获得用户的成就
     * @param userId 用户id
     * @param start 开始索引
     * @return 成就列表
     */
    List<Achievement> listAchievementsByUserId(Integer userId, Integer start );

    Achievementrecord get(Integer userId, Integer achievementeId);

}
