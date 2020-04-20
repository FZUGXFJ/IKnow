package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Reporttype;

public interface ReportTypeDAO {
    public void add(Reporttype bean);

    public Reporttype get(Integer id);

    public void update(Reporttype bean);
}
