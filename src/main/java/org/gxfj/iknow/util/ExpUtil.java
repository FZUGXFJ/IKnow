package org.gxfj.iknow.util;

import org.gxfj.iknow.dao.LevelDAO;
import org.gxfj.iknow.dao.UserDAO;
import org.gxfj.iknow.pojo.Level;
import org.gxfj.iknow.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author qmbx
 */
@Component
public class ExpUtil {
    @Autowired
    UserDAO userDAO;
    @Autowired
    LevelDAO levelDAO;

    List<Level> levels;

    private  List<Level> getLevels() {
        if (levels == null) {
            levels = levelDAO.list();
        }
        return levels;
    }

    /**
     * 添加用户的经验
     * @param user 添加的用户
     * @param exp 经验值
     */
    public void addExp(User user, Integer exp) {
        if (user == null || user.getId() == null || exp == null || exp.equals(0)) {
            return ;
        }

        user.setExp(user.getExp() + exp);

        userDAO.update(user);
    }

    /**
     * 根据用户获得用户的等级
     * @param user 要获取等级的用户
     * @return 用户的等级，如果用户对象为空 或者 用户对象中的经验为空 或者 用户的经验不在等级表中，则返回null
     */
    public Level getLevel(User user) {
        if (user == null || user.getExp() == null) {
            return null;
        }

        return getLevel(user.getExp());
    }

    /**
     * 根据经验值获得用户的等级
     * @param exp 要判断的经验
     * @return 经验对应的等级，如果经验为空 或者 经验的值不在所有等级的范围中，则返回null
     */
    public Level getLevel(Integer exp) {
        if (exp == null) {
            return null;
        }
        List<Level> levels = getLevels();
        for (Level level : levels) {
            if (exp >= level.getExpBotLimit() && exp < level.getExpTopLimit()) {
                return level;
            }
        }
        return null;
    }
}
