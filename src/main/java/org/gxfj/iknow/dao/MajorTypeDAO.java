package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Majortype;

import java.util.List;


public interface MajorTypeDAO extends BaseDAO<Majortype>{

    /**
     * 根据SubjectType的id获取该专业类下的所有专业
     * @param subjectTypeID 专业类id
     * @return 所有的专业，如果没有，则List中没有对象
     */
    public List<Majortype> list(Integer subjectTypeID);
}
