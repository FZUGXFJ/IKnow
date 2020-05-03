package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Categoriestype;

import java.util.List;

public interface CategoriesTypeDAO extends BaseDAO<Categoriestype>{

    /**
     * 列出所有的门类
     * @return 以List存放数据库中所有的CategoriesType对象
     */
    public List<Categoriestype> list();
}
