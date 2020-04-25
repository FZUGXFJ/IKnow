package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Questiontype;

/**
 * @author qmbx
 */
public interface QuestionTypeDAO {
    /**
     * 向数据库中添加问题类别
     * @param bean 添加的类别
     */
    public void add(Questiontype bean);

    public Questiontype get(Integer id);

    /**
     * 根据问题的categoryTypeID、subjectTypeID、majorTypeID查询对应Questiontype对象
     * @param categoryTypeID 门类ID
     * @param subjectTypeID 专业类ID
     * @param majorTypeID 专业ID
     * @return 没有返回null
     */
    public Questiontype get(Integer categoryTypeID, Integer subjectTypeID, Integer majorTypeID);

    public void update(Questiontype bean);
}
