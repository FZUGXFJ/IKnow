package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Questiontype;

import java.util.List;

/**
 * @author qmbx
 */
public interface QuestionTypeDAO extends BaseDAO<Questiontype>{


    /**
     * 根据问题的categoryTypeID、subjectTypeId、majorTypeID查询对应Questiontype对象
     * @param categoryTypeId 门类ID
     * @param subjectTypeId 专业类ID
     * @param majorTypeId 专业ID
     * @return 没有返回null
     */
    Questiontype get(Integer categoryTypeId, Integer subjectTypeId, Integer majorTypeId);

    /**
     * 列出所有的QuestionType
     * @return 用List存储所有的QuestionType对象
     */
    List<Questiontype> list();

}
