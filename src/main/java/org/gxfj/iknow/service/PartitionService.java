package org.gxfj.iknow.service;

import java.util.Map;

public interface PartitionService {

    /**
     * 获得门类json
     * @return 数据
     */
    public Map<String,Object> getCategories();

    /**
     * 获得门类下所有学科json
     * @param cid 门类id
     * @return 数据
     */
    public Map<String,Object> getSubjects(Integer cid);

    /**
     * 获得学科下所有专业json
     * @param sid 学科id
     * @return 数据
     */
    public Map<String,Object> getMajors(Integer sid);
}
