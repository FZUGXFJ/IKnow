package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.User;

public interface UserDAO {

    public void add(User bean);

    public User get(Integer id);

    public void update(User bean);

    public User getUserByUsername(String username);

    public User getUserByEmail(String email);

    public boolean hasUserId(Integer id);

    public boolean hasUsername(String username);

    public boolean hasUserEmail(String email);
}
