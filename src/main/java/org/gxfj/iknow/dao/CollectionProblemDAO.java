package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Approvalreply;
import org.gxfj.iknow.pojo.Collectionproblem;

public interface CollectionProblemDAO extends BaseDAO<Collectionproblem>{

    /**
     * 问题集合数量
     * @param questionId 问题id
     * @return  数量
     */
    Integer getCollectionCount(Integer questionId);

    /**
     * 根据用户id与问题id获取问题收藏记录
     * @param userId 用户id
     * @param questionId 问题id
     * @return  收藏问题记录
     */
    Collectionproblem getCollectionQuestion(Integer userId,Integer questionId);

    /**
     * 删除收藏的问题
     * @param bean  需要删除的元素
     */
    void delete(Collectionproblem bean);

}
