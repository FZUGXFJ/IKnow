package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Major;


public interface MajorDAO extends BaseDAO<Major>{

    public Major getMajorByName(String majorName);

}
