package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Report;

public interface ReportDAO {
    public void add(Report bean);

    public Report get(Integer id);

    public void update(Report bean);
}
