package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Categoriestype;

import java.util.List;

public interface CategoriesTypeDAO {
    /**
     * 向数据库中添加对象
     * @param bean 要添加的对象
     */
    public void add(Categoriestype bean);


    /**
     * 根据id获得门类对象
     * @param id Categories
     * @return 不存在返回null
     */
    public Categoriestype get(Integer id);

    /**
     * 更新数据库中门类
     * @param bean 要更新的CategoriesType
     */
    public void update(Categoriestype bean);

    /**
     * 列出所有的门类
     * @return 以List存放数据库中所有的CategoriesType对象
     */
    public List<Categoriestype> list();
}
