package org.gxfj.iknow.service;

import org.gxfj.iknow.dao.*;
import org.gxfj.iknow.pojo.*;
import org.gxfj.iknow.util.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
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
    @Autowired
    ApprovalCommentDAO approvalCommentDAO;
    @Autowired
    ReplyDAO replyDAO;
    @Autowired
    ApprovalReplyDAO approvalReplyDAO;

    final static private int MAP_NUM = 30;
    final static private int REPLY_NUM = 2;

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

    @Override
    public Map<String, Object> getComments(Integer answerId){
        Map<String, Object> response = new HashMap<>(MAP_NUM);
        boolean userIdentify;
        //获取问题下的20条评论
        List<Comment> comments = commentDAO.listByAnswerId(answerId,0,20);
        List<Reply> replies;
        //json数组
        List<Map<String, Object>> commentListMap = new ArrayList<>();
        List<Map<String, Object>> replyListMap;
        //json数组元素
        Map<String, Object> commentMap;
        Map<String, Object> replyMap;

        //获取回答
        Answer answer = answerDAO.get(answerId);
        //获取问题
        Question question = answer.getQuestionByQuestionId();
        //获取回答者
        User answerOwner = answerDAO.get(answerId).getUserByUserId();
        //获取题主
        User questionOwner = answerDAO.get(answerId).getQuestionByQuestionId().getUserByUserId();

        //获取问题评论数
        Integer count = commentDAO.getCount(answerId);
        response.put("commentNum",count);

        for(Comment comment:comments){
            commentMap = new HashMap<>(MAP_NUM);
            commentMap.put("id",comment.getId());
            commentMap.put("userId",comment.getUserByUserId().getId());
            //评论者是题主或者回答者且匿名,则设置相应的头像和名称
            userIdentify = (questionOwner.getId().equals(comment.getUserByUserId().getId()) && question.getIsAnonymous() == 1)
                    ||(answerOwner.getId().equals(comment.getUserByUserId().getId()) && answer.getIsAnonymous() == 1);
            if(userIdentify){
                commentMap.put("head","<img src='../../head/0.jpg' width='100%' height='100%' style='border-radius: 100%' alt=''>" +
                        "alt=''>");
                commentMap.put("name","匿名用户");
            }else{
                commentMap.put("head","<img src='../../head/"+comment.getUserByUserId().getHead() +
                                "' width='100%' height='100%' style='border-radius: 100%' alt=''>");
                commentMap.put("name",comment.getUserByUserId().getName());
            }
            commentMap.put("content",comment.getContent());
            commentMap.put("approveNum",approvalCommentDAO.getCount(answerId));

            //评论回复列表
            replyListMap = new ArrayList<>();
            replies = replyDAO.listByCommentId(comment.getId(),0,REPLY_NUM);
            for(Reply reply:replies){
                replyMap = new HashMap<>(MAP_NUM);
                replyMap.put("id",reply.getId());
                replyMap.put("head",reply.getUserByUserId().getId());
                replyMap.put("name",reply.getUserByUserId().getName());
                replyMap.put("content",reply.getContent());
                replyMap.put("approveNum",reply.getCount());
                replyMap.put("isQuestionOwner",questionOwner.getId().equals(reply.getUserByUserId().getId())?1:0);
                replyMap.put("isAnswerer",answerOwner.getId().equals(reply.getUserByUserId().getId())?1:0);
                //回复对象的用户名,需判断示符是题主、回答者，判断是否匿名
                userIdentify = (questionOwner.getId().equals(reply.getUserByTargetUserId().getId()) && question.getIsAnonymous() == 1)
                        ||(answerOwner.getId().equals(reply.getUserByTargetUserId().getId()) && answer.getIsAnonymous() == 1);
                if(userIdentify){
                    commentMap.put("name","匿名用户");
                }else{
                    commentMap.put("name",reply.getUserByTargetUserId().getName());
                }
                replyMap.put("time",Time.getTime(reply.getDate()));

                replyListMap.add(replyMap);
            }
            commentMap.put("replies",replyListMap);
            commentMap.put("isQuestionOwner",questionOwner.getId().equals(comment.getUserByUserId().getId())?1:0);
            commentMap.put("isAnswerer",answerOwner.getId().equals(comment.getUserByUserId().getId())?1:0);
            commentMap.put("time", Time.getTime(comment.getDate()));
            commentMap.put("replyNum",replyDAO.getCount(comment.getId()));
            commentListMap.add(commentMap);
        }
        response.put("comments",commentListMap);
        return response;
    }




}
