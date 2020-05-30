package org.gxfj.iknow.service;

import java.util.Map;

public interface PartitionService {

    /**
     * 获得门类json
     * @return 数据
     */
    Map<String,Object> getCategories();

    /**
     * 获得门类下所有学科json
     * @param cid 门类id
     * @return 数据
     */
    Map<String,Object> getSubjects(Integer cid);

    /**
     * 获得学科下所有专业json
     * @param sid 学科id
     * @return 数据
     */
    Map<String,Object> getMajors(Integer sid);

    /**
     * 获取分区下的问题(暂时没有推荐，直接获取最新的问题)，如果有参数为空，返回null
     * @param categoryId 门类ID
     * @param subjectId 学科ID
     * @param majorId 专业ID
     * @param start 问题开始
     * @return 问题列表
     */
    Map<String, Object> getQuestion(Integer categoryId, Integer subjectId, Integer majorId, Integer start,
                                    Integer count);
}
