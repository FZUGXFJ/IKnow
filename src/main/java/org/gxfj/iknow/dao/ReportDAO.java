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

    /**
     * 删除举报
     * @param bean 要删除的举报
     */
    void delete(Report bean);

    /**
     * 删掉所有问题举报记录
     */
    void deleteAllQueReport();

    /**
     * 删掉所有回答举报记录
     */
    void deleteAllAnsReport();

    /**
     * 删掉所有评论举报记录
     */
    void deleteAllComReport();

    /**
     * 删掉所有回复举报记录
     */
    void deleteAllRepReport();
}
