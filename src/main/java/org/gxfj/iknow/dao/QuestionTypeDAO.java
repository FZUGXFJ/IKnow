package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Questiontype;

/**
 * @author qmbx
 */
public interface QuestionTypeDAO extends BaseDAO<Questiontype>{


    /**
     * 根据问题的categoryTypeID、subjectTypeID、majorTypeID查询对应Questiontype对象
     * @param categoryTypeID 门类ID
     * @param subjectTypeID 专业类ID
     * @param majorTypeID 专业ID
     * @return 没有返回null
     */
    Questiontype get(Integer categoryTypeID, Integer subjectTypeID, Integer majorTypeID);

}
