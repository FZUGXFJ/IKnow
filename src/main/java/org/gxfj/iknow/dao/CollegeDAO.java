package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.College;

public interface CollegeDAO {
    public void add(College bean);

    public College get(Integer id);

    public void update(College bean);
}
