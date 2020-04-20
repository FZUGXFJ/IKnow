package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Useridentity;

public interface UserIdentityDAO {
    public void add(Useridentity bean);

    public Useridentity get(Integer id);

    public void update(Useridentity bean);
}
