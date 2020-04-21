package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Message;

public interface MessageDAO {
    public void add(Message bean);

    public Message get(Integer id);

    public void update(Message bean);
}
