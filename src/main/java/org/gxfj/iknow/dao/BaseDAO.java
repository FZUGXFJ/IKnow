package org.gxfj.iknow.dao;
/**
 * @author qmbx
 */
public interface BaseDAO<T> {
    /**
     * 添加记录到数据库中
     * @param bean 要添加的bean
     */
    void add(T bean);

    /**
     * 根据主键获取对象
     * @param id 主键
     * @return 不存在返回null
     */
    T get(Integer id);

    /**
     * 根据JavaBean更新数据库记录
     * @param bean 要更新的JavaBean
     */
    void update(T bean);
}