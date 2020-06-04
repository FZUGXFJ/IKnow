package org.gxfj.iknow.service;

import org.gxfj.iknow.pojo.User;

import java.util.Map;


/**
 * @author erniumo
 */
public interface ReportService {
    /**
     * 获取举报原因列表
     * @return 列表
     */
    Map<String,Object> reportReasonmap();

    /**
     * 举报
     * @param description 描述
     * @param reason 原因类型
     * @param targetId 目标id
     * @param type 举报类型
     *
     */
    Map<String, Object> doReport(Integer type, Integer reason, String description, Integer targetId);
}
