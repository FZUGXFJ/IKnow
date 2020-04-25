package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Subjecttype;

import java.util.List;

public interface SubjectTypeDAO {
    /**
     * 插入SubjectType记录
     * @param bean 要插入的记录
     */
    public void add(Subjecttype bean);

    /**
     * 根据主键查询SubjectType记录
     * @param id 要查询的记录的主键
     * @return 查询到的记录，如果空，则为null
     */
    public Subjecttype get(Integer id);

    /**
     * 根据bean中的id，更新数据库中的记录
     * @param bean 更新后的记录
     */
    public void update(Subjecttype bean);

    /**
     * 根据categoryType的id获取该专业类下的所有专业
     * @param categoryTypeID 专业类id
     * @return 所有的专业，如果没有，则List中没有对象
     */
    public List<Subjecttype> list(Integer categoryTypeID);
}
