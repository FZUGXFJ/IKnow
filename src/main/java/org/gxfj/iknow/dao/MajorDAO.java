package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Major;


public interface MajorDAO extends BaseDAO<Major>{

    public Major getMajorByName(String majorName);

    /**
     * 删除专业
     * @param majorId 专业Id
     * @return 是否删除成功
     */
    boolean delete(Integer majorId);

}
