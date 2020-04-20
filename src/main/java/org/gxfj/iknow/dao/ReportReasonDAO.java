package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Reportreason;

public interface ReportReasonDAO {
    public void add(Reportreason bean);

    public Reportreason get(Integer id);

    public void update(Reportreason bean);
}
