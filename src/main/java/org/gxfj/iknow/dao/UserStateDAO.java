package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Userstate;

public interface UserStateDAO {
    public void add(Userstate bean);

    public Userstate get(Integer id);

    public void update(Userstate bean);
}
