package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Browsinghistory;

public interface BrowsingHistoryDAO {
    public void add(Browsinghistory bean);

    public Browsinghistory get(Integer id);

    public void update(Browsinghistory bean);

    public Integer getBrowsingCount(Integer questionID);
}
