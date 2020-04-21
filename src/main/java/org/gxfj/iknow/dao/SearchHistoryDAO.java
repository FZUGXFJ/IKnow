package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Searchhistory;

public interface SearchHistoryDAO {
    public void add(Searchhistory bean);

    public Searchhistory get(Integer id);

    public void update(Searchhistory bean);
}
