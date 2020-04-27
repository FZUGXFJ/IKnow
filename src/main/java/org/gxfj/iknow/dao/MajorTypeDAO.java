package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Majortype;

import java.util.List;

/**
 * @author qmbx
 */
public interface MajorTypeDAO {
    /**
     * 插入MajorType记录
     * @param bean 要插入的记录
     */
    public void add(Majortype bean);

    /**
     * 根据主键查询MajorType记录
     * @param id 要查询的记录的主键
     * @return 查询到的记录，如果空，则为null
     */
    public Majortype get(Integer id);

    /**
     * 根据bean中的id，更新数据库中的记录
     * @param bean 更新后的记录
     */
    public void update(Majortype bean);

    /**
     * 根据SubjectType的id获取该专业类下的所有专业
     * @param subjectTypeID 专业类id
     * @return 所有的专业，如果没有，则List中没有对象
     */
    public List<Majortype> list(Integer subjectTypeID);
}
