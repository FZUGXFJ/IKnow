package org.gxfj.iknow.util;

import org.gxfj.iknow.dao.AchievementDAO;
import org.gxfj.iknow.dao.AchievementRecordDAO;
import org.gxfj.iknow.pojo.Achievement;
import org.gxfj.iknow.pojo.Achievementrecord;
import org.gxfj.iknow.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class AchievementUtil {
    private List<Achievement> achievementList;

    @Autowired
    AchievementDAO achievementDAO;
    @Autowired
    AchievementRecordDAO achievementRecordDAO;

    /**
     * 获得成就列表
     * @return 内存中的成就
     */
    public List<Achievement> getAchievementList() {
        if (achievementList == null) {
            achievementList = achievementDAO.list();
        }
        return achievementList;
    }

    /**
     * 用户完成成就
     * @param user          完成的用户
     * @param achievementId 成就的id
     * @return 是否成功
     */
    public boolean completeAchievement(User user, Integer achievementId) {
        if (user == null || achievementId == null || achievementRecordDAO.get(user.getId(), achievementId) != null) {
            return false;
        }
        for (Achievement achievement : achievementList) {
            if (achievement.getId().equals(achievementId)) {
                Achievementrecord achievementrecord = new Achievementrecord();
                achievementrecord.setUserByUserId(user);
                achievementrecord.setDate(new Date());
                achievementrecord.setAchievementByAchievementId(achievement);

                return true;
            }
        }

        return false;
    }
}
