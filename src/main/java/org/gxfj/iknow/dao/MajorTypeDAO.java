package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Majortype;

/**
 * @author qmbx
 */
public interface MajorTypeDAO {
    public void add(Majortype bean);

    public Majortype get(Integer id);

    public void update(Majortype bean);
}
