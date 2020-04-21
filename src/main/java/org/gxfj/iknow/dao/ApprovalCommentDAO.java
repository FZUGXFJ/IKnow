package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Approvalcomment;

public interface ApprovalCommentDAO {
    public void add(Approvalcomment bean);

    public Approvalcomment get(Integer id);

    public void update(Approvalcomment bean);
}
