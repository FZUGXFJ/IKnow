package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Collectionproblem;

public interface CollectionProblemDAO extends BaseDAO<Collectionproblem>{

    /**
     * 问题集合数量
     * @param questionId 问题id
     * @return  数量
     */
    Integer getCollectionCount(Integer questionId);
}
