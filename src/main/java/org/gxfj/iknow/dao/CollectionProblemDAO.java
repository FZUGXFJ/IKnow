package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Collectionproblem;

public interface CollectionProblemDAO {
    public void add(Collectionproblem bean);

    public Collectionproblem get(Integer id);

    public void update(Collectionproblem bean);

    public Integer getCollectionCount(Integer questionId);
}
