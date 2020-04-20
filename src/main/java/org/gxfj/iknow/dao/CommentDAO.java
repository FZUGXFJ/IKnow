package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Comment;

public interface CommentDAO {
    public void add(Comment bean);

    public Comment get(Integer id);

    public void update(Comment bean);
}
