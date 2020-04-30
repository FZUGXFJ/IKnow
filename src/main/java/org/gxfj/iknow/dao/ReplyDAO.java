package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Reply;

import java.util.List;

public interface ReplyDAO {
    public void add(Reply bean);

    public Reply get(Integer id);

    public void update(Reply bean);

    public List<Reply> listByCommentId(Integer commentId,Integer start,Integer count);
}
