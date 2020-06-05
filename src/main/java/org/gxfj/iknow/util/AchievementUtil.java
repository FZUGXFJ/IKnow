package org.gxfj.iknow.util;

import org.gxfj.iknow.dao.AchievementDAO;
import org.gxfj.iknow.pojo.Achievement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AchievementUtil {
    private List<Achievement> achievementList;

    @Autowired
    AchievementDAO achievementDAO;

    public List<Achievement> getAchievementList() {
        if (achievementList == null) {
            achievementList = achievementDAO.list();
        }
        return achievementList;
    }
}
