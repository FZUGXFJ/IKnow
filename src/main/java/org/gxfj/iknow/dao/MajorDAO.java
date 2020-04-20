package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Major;

public interface MajorDAO {
    public void add(Major bean);

    public Major get(Integer id);

    public void update(Major bean);
}
