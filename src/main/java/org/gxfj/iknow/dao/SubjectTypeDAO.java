package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Subjecttype;

import java.util.List;

public interface SubjectTypeDAO extends BaseDAO<Subjecttype>{

    /**
     * 根据categoryType的id获取该专业类下的所有专业
     * @param categoryTypeID 专业类id
     * @return 所有的专业，如果没有，则List中没有对象
     */
    List<Subjecttype> list(Integer categoryTypeID);
}
