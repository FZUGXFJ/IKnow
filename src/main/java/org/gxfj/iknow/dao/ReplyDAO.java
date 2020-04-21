package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Reply;

public interface ReplyDAO {
    public void add(Reply bean);

    public Reply get(Integer id);

    public void update(Reply bean);
}
