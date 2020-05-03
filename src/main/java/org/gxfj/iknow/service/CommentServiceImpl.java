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

        //json数组
        List<Map<String, Object>> commentListMap;

        //获取回答
        Answer answer = answerDAO.get(answerId);
        //获取问题
        Question question = answer.getQuestionByQuestionId();
        //获取回答者
        User answerOwner = answer.getUserByUserId();
        //获取题主
        User questionOwner = question.getUserByUserId();

        //获取问题评论数
        Integer count = commentDAO.getCount(answerId);
        response.put("commentNum",count);
        if (count != 0) {
            commentListMap = getCommentsMapArray(comments, questionOwner, answerOwner, question, answer);
        } else {
            commentListMap = new ArrayList<>();
        }

        response.put("comments",commentListMap);
        return response;
    }

    /**
     * 评论者是题主或者回答者且匿名判断
     * @param questionOwner 问题题主
     * @param answerOwner 回答者
     * @param comment 评论
     * @param question 问题
     * @param answer 回答
     * @return 返回根据判断获得的评论用户名与头像map值
     */
    private Map<String, Object> commenterIsQAOwner(User questionOwner,User answerOwner,Comment comment,
                                                   Question question, Answer answer){
        User commentUser = comment.getUserByUserId();
        Map<String, Object> commentMap = new HashMap<>(MAP_NUM);
        boolean isAnonymous = (questionOwner.getId().equals(commentUser.getId()) && question.getIsAnonymous() == 1)
                ||(answerOwner.getId().equals(commentUser.getId()) && answer.getIsAnonymous() == 1);
        if(isAnonymous){
            commentMap.put("head","<img src='../../head/0.jpg' width='100%' height='100%'" +
                    " style='border-radius: 100%' alt=''>");
            commentMap.put("name","匿名用户");
        }else{
            commentMap.put("head","<img src='../../head/"+commentUser.getHead() +
                    "' width='100%' height='100%' style='border-radius: 100%' alt=''>");
            commentMap.put("name",commentUser.getName());
        }
        return  commentMap;
    }

    /**
     * 回复者者是题主或者回答者且匿名判断
     * @param questionOwner 问题题主
     * @param answerOwner 回答者
     * @param reply 评论
     * @param question 问题
     * @param answer 回答
     * @return 返回根据判断获得的回复者用户名与头像、回复对象用户名map值
     */
    private Map<String, Object> replyIsQAOwner(User questionOwner, User answerOwner, Reply reply, Question question
            , Answer answer){
        User replyUser = reply.getUserByUserId();
        User targetUser = reply.getUserByTargetUserId();
        Map<String, Object> replyMap = new HashMap<>(MAP_NUM);
        boolean isAnonymous = (questionOwner.getId().equals(replyUser.getId()) && question.getIsAnonymous() == 1)
                ||(answerOwner.getId().equals(replyUser.getId()) && answer.getIsAnonymous() == 1);
        if(isAnonymous){
            replyMap.put("targetName","匿名用户");
            replyMap.put("name","匿名用户");
            replyMap.put("head","<img src='../../head/0.jpg' width='100%' height='100%' " +
                    "style='border-radius: 100%' alt=''> alt=''>");
        } else {
            replyMap.put("targetName",targetUser.getName());
            replyMap.put("name",replyUser.getName());
            replyMap.put("head","<img src='../../head/"+replyUser.getHead() +
                    "' width='100%' height='100%' style='border-radius: 100%' alt=''>");
        }
        return replyMap;
    }

    /**
     * 获取评论下评论的json数组
     * @param questionOwner 问题题主
     * @param answerOwner 回答者
     * @param comments 回答的评论列表
     * @param question 问题
     * @param answer 回答
     * @return List<Map<String, Object>>型的回答map数组
     */
    private List<Map<String, Object>> getCommentsMapArray(List<Comment> comments ,User questionOwner ,User answerOwner
            , Question question, Answer answer) {
        List<Map<String, Object>> commentListMap = new ArrayList<>();
        List<Map<String, Object>> replyListMap;

        List<Reply> replies;

        Map<String, Object> commentMap;

        User commentUser;
        for (Comment comment : comments) {
            commentUser = comment.getUserByUserId();
            //评论者是题主或者回答者且匿名,则设置相应的头像和名称
            commentMap = commenterIsQAOwner(questionOwner,answerOwner,comment,question,answer);
            commentMap.put("id",comment.getId());
            commentMap.put("userId",commentUser.getId());
            commentMap.put("content",comment.getContent());
            commentMap.put("approveNum",comment.getCount());
            commentMap.put("isQuestionOwner",questionOwner.getId().equals(commentUser.getId()) ? 1 : 0);
            commentMap.put("isAnswerer",answerOwner.getId().equals(commentUser.getId()) ? 1 : 0);
            commentMap.put("time", Time.getTime(comment.getDate()));
            int num = replyDAO.getCount(comment.getId());
            commentMap.put("replyNum", num);
            if (num != 0) {
                replies = replyDAO.listByCommentId(comment.getId(), 0, REPLY_NUM);
                replyListMap = getCommentReplyMapArray(replies,questionOwner,answerOwner,question,answer);
            } else {
                replyListMap = new ArrayList<>();
            }
            //评论回复列表

            commentMap.put("replies",replyListMap);
            commentListMap.add(commentMap);
        }
        return commentListMap;
    }

    /**
     * 获取评论下回复的json数组
     * @param questionOwner 问题题主
     * @param answerOwner 回答者
     * @param replies 评论下的回复列表
     * @param question 问题
     * @param answer 回答
     * @return List<Map<String, Object>>型的回复map数组
     */
    private List<Map<String, Object>> getCommentReplyMapArray(List<Reply> replies,User questionOwner,User answerOwner,
                                                              Question question,Answer answer) {
        List<Map<String, Object>> replyListMap = new ArrayList<>();;
        Map<String, Object> replyMap;
        User replyUser;
        User targetUser;
        for (Reply reply : replies) {
            //回复对象的用户名,需判断示符是题主、回答者，判断是否匿名
            replyMap = replyIsQAOwner(questionOwner,answerOwner,reply,question,answer);
            replyUser = reply.getUserByUserId();
            targetUser = reply.getUserByTargetUserId();
            replyMap.put("id",reply.getId());
            replyMap.put("userId",replyUser.getId());
            replyMap.put("targetId",targetUser.getId());
            replyMap.put("content",reply.getContent());
            replyMap.put("approveNum",reply.getCount());
            replyMap.put("isQuestionOwner",questionOwner.getId().equals(replyUser.getId()) ? 1 : 0);
            replyMap.put("isAnswerer",answerOwner.getId().equals(replyUser.getId()) ? 1 : 0);
            replyMap.put("time",Time.getTime(reply.getDate()));
            replyListMap.add(replyMap);
        }
        return replyListMap;
    }
}
