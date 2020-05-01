package org.gxfj.iknow.service;

import org.gxfj.iknow.dao.CommentDAO;
import org.gxfj.iknow.dao.ReplyDAO;
import org.gxfj.iknow.dao.UserDAO;
import org.gxfj.iknow.pojo.Comment;
import org.gxfj.iknow.pojo.Reply;
import org.gxfj.iknow.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service("replyService")
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private CommentDAO commentDAO;
    @Autowired
    private ReplyDAO replyDAO;
    @Override
    public void postReply(Integer commentID, String content, Integer replyTarget, User user) {
        User tu= userDAO.get(replyTarget);
        Comment comment=commentDAO.get(commentID);

        Reply reply=new Reply();
        reply.setContent(content);
        reply.setIsDelete((byte)0);
        reply.setCount(0);
        reply.setDate(new Date());
        reply.setUserByTargetUserId(tu);
        reply.setCommentByCommentId(comment);
        reply.setUserByUserId(user);

        replyDAO.add(reply);
    }
}
