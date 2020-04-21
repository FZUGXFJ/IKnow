package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.School;

public interface SchoolDAO {
    public void add(School bean);

    public School get(Integer id);

    public void update(School bean);
}
