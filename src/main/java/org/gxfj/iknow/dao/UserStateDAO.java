package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Userstate;

import java.util.List;

public interface UserStateDAO extends BaseDAO<Userstate> {

    /**
     * 获取数据库中所有的 UserState
     * @return userState列表
     */
    List<Userstate> list();
}
