package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Reply;
import org.gxfj.iknow.pojo.Report;

import java.util.List;

public interface ReportDAO extends BaseDAO<Report>{
    /**
     * 获得指定类型的举报信息列表
     * @param reportType 举报类型
     * @return  指定类型举报信息列表
     */
    List<Report> listReportByType(Integer reportType);

}
