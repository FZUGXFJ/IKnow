package org.gxfj.iknow.service;

import org.gxfj.iknow.pojo.Collectionproblem;
import org.gxfj.iknow.pojo.User;

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
     * @param user 浏览问题的用户
     * @param questionId 用户收藏的问题
     */
    public void cancelCollect(User user, Integer questionId);

    /**
     * 用户收藏问题
     * @param user 浏览问题的用户
     * @param questionId 用户想要收藏的问题
     */
    public void collectProblem(User user,Integer questionId);
}
