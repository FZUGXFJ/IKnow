package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Approvalreply;

import javax.persistence.criteria.CriteriaBuilder;

public interface ApprovalReplyDAO {
    public void add(Approvalreply bean);

    public Approvalreply get(Integer id);

    public void update(Approvalreply bean);

}
