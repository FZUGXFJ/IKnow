package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Approvalanswer;

public interface ApprovalAnswerDAO {
    public void add(Approvalanswer bean);

    public Approvalanswer get(Integer id);

    public void update(Approvalanswer bean);
}
