package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Level;

import java.util.List;

public interface LevelDAO extends BaseDAO<Level>{

    /**
     * 通过经验获得等级
     * @param exp 经验
     * @return 等级
     */
    Integer getLevelByExp(Integer exp);

    List<Level> list();
}
