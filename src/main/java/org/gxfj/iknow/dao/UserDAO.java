package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.User;

public interface UserDAO {

    public void add(User bean);

    public User get(Integer id);

    public void update(User bean);

}
