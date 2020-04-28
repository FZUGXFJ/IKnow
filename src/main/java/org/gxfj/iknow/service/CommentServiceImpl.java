package org.gxfj.iknow.service;

import org.gxfj.iknow.dao.*;
import org.gxfj.iknow.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
/**
 * @author 爱学习的水先生
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentDAO commentDAO;
    @Autowired
    AnswerDAO answerDAO;

    @Override
    public void postComment(User user, Integer answerId, String content){
        Comment comment = new Comment();

        comment.setUserByUserId(user);
        comment.setContent(content);
        comment.setAnswerByAnswerId(answerDAO.get(answerId));
        comment.setDate(new Date());
        comment.setIsDelete((byte)0);
        comment.setCount(0);

        commentDAO.add(comment);
    }
}
