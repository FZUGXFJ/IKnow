package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Categoriestype;

public interface CategoriesTypeDAO {
    public void add(Categoriestype bean);

    public Categoriestype get(Integer id);

    public void update(Categoriestype bean);
}
