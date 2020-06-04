package org.gxfj.iknow.service;

import org.gxfj.iknow.pojo.Collectionproblem;
import org.gxfj.iknow.pojo.User;

import java.util.Map;

public interface CollectionService {
    /**
     *查询收藏信息
     * @param userId
     * @param questionId
     * @return
     */
    public Collectionproblem getCollectionQuestion(Integer userId, Integer questionId);

    /**
     * 用户取消收藏问题
     * @param questionId 用户收藏的问题
     * @return 结果码
     */
    public Map<String, Object> cancelCollect( Integer questionId);

    /**
     * 用户收藏问题
     * @param questionId 用户想要收藏的问题
     * @return 结果码
     */
    public Map<String, Object> collectProblem(Integer questionId);
}
