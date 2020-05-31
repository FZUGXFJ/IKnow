package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Reportreason;

import java.util.List;

public interface ReportReasonDAO extends BaseDAO<Reportreason>{

    List<Reportreason> list();

}
