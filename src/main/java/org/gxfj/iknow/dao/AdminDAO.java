package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Admin;

public interface AdminDAO {
    public void add(Admin bean);

    public Admin get(Integer id);

    public void update(Admin bean);
}
