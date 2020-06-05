package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.College;

public interface CollegeDAO extends BaseDAO<College>{

    public College getCollegeByName(String collegeName);

}
