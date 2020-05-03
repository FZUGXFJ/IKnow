package org.gxfj.iknow.service;

import org.gxfj.iknow.dao.*;
import org.gxfj.iknow.pojo.*;
import org.gxfj.iknow.util.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
/**
 * @author hhj
 */
@Service("replyService")
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private CommentDAO commentDAO;
    @Autowired
    private ReplyDAO replyDAO;
    @Autowired
    private AnswerDAO answerDAO;
    @Autowired
    private ApprovalCommentDAO approvalCommentDAO;
    @Autowired
    private ApprovalReplyDAO approvalReplyDAO;

    private final int MAP_NUM = 20;

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

    @Override
    public Map<String, Object> showAllReplys(Integer commentId, User visitor) {
        Map<String , Object> resultMap = new HashMap<>(MAP_NUM);
        resultMap.put("comment" , getComment(commentId, visitor));
        resultMap.put("replies" , listReplies(commentId, visitor));
        return resultMap;

    }

    /**
     * 获得评论信息
     * @param commentId 评论id
     * @return 评论的信息
     */
    private Map<String , Object> getComment(Integer commentId, User visitor) {
        Map<String , Object> commentMap = new HashMap<>(MAP_NUM);
        Comment comment = commentDAO.get(commentId);
        commentMap = commenterIsQAOwner(commentId );
        commentMap.put("userId" , comment.getUserByUserId().getId());
        commentMap.put("content",comment.getContent());
        commentMap.put("replyNum" , replyDAO.getCount(commentId));
        commentMap.put("approveNum" , comment.getCount());

        //如果当前浏览者已登录，且评论有人点赞，且用户对该评论点过赞则为1,否则为0
        if (visitor != null && comment.getCount() != 0 &&
                approvalCommentDAO.get(visitor.getId(), comment.getId()) != null) {
            commentMap.put("isApproved", 1);
        } else {
            commentMap.put("isApproved", 0);
        }

        commentMap.put("time", Time.getTime(comment.getDate()));

        return commentMap;
    }

    /**
     * 获得指定评论的所有回复
     * @param commentId 评论id
     * @return 评论的所有回复
     */
    private List<Map<String , Object>> listReplies(Integer commentId, User visitor) {
        List<Map<String , Object>> repliesMap  = new ArrayList<>();
        List<Reply> replies = replyDAO.getAllReplies(commentId);
        for (Reply reply : replies) {
            Map<String ,Object> replyMap = replierIsQAOwner(reply.getId());
            replyMap.put("id" , reply.getId());
            replyMap.put("targetName" , reply.getUserByTargetUserId().getName());
            replyMap.put("targetId" , reply.getUserByTargetUserId().getId());
            replyMap.put("content" , reply.getContent());
            replyMap.put("approveNum" , reply.getCount());
            replyMap.put("time" , Time.getTime(reply.getDate()));
            //如果浏览者已登录，且有点赞记录，则isApproved为1，否则为0
            int isApproved = 0;
            if (visitor != null) {
                isApproved = approvalReplyDAO.searchByserIdandReplyId(visitor.getId(), reply.getId());
            }
            replyMap.put("isApproved", isApproved);

            repliesMap.add(replyMap);
        }
        return repliesMap;
    }

    /**
     * 判断评论者是否为答主或题主，是否匿名，并相应的对hash表赋值
     * @param commentId 评论id
     * @return commentMap 评论哈希表
     */
    public Map<String , Object> commenterIsQAOwner(Integer commentId){
        Map<String ,Object> commentMap = new HashMap<>(MAP_NUM);
        Comment comment = commentDAO.get(commentId);
        //得到评论的用户的id
        Integer commentOwnerId = comment.getUserByUserId().getId();
        //获取回答
        Answer answer = comment.getAnswerByAnswerId();
        //获取问题
        Question question = answer.getQuestionByQuestionId();
        //得到题主的id
        Integer questionOwnerId = answer.getQuestionByQuestionId().getUserByUserId().getId();
        //得到答主的id
        Integer answerOwnerId = question.getUserByUserId().getId();
        boolean userIdentify = (commentOwnerId.equals(questionOwnerId) && question.getIsAnonymous() == 1) ||
                (commentOwnerId.equals(answerOwnerId) && answer.getIsAnonymous() == 1);
        if(userIdentify) {
            commentMap.put("head","<img src='../../head/0.jpg' width='100%' height='100%' " +
                    "style='border-radius: 100%' alt=''>");
            commentMap.put("name","匿名用户");
        }else{
            commentMap.put("head","<img src='../../head/"+comment.getUserByUserId().getHead() +
                    "' width='100%' height='100%' style='border-radius: 100%' alt=''>");
            commentMap.put("name",comment.getUserByUserId().getName());
        }
        if(commentOwnerId.equals(questionOwnerId)){
            commentMap.put("isQuestionOwner" , 1);
        }
        else{
            commentMap.put("isQuestionOwner" , 0);
        }
        if(commentOwnerId.equals(answerOwnerId)){
            commentMap.put("isAnswerer" , 1);
        }
        else{
            commentMap.put("isAnswerer" , 0);
        }
        return commentMap;
    }

    /**
     * 判断回复者是否为答主或题主，是否匿名，并相应的对hash表赋值
     * @param replyId 评论id
     * @return commentMap 评论哈希表
     */
    public Map<String , Object> replierIsQAOwner(Integer replyId){
        Reply reply = replyDAO.get(replyId);
        Map<String , Object> replyMap = new HashMap<>(MAP_NUM);
        //获取回答
        Answer answer = reply.getCommentByCommentId().getAnswerByAnswerId();
        //获取问题
        Question question = answer.getQuestionByQuestionId();
        //得到题主的id
        Integer questionOwnerId = answer.getQuestionByQuestionId().getUserByUserId().getId();
        //得到答主的id
        Integer answerOwnerId = question.getUserByUserId().getId();
        Integer replierId =reply.getUserByUserId().getId();
        boolean userIdentify = (replierId.equals(questionOwnerId) && question.getIsAnonymous() == 1) ||
                (replierId.equals(answerOwnerId) && answer.getIsAnonymous() == 1);
        if(userIdentify){
            replyMap.put("head","<img src='../../head/0.jpg' width='100%' height='100%' " +
                    "style='border-radius: 100%' alt=''> alt=''>");
            replyMap.put("name","匿名用户");
        }else{
            replyMap.put("head","<img src='../../head/"+reply.getUserByUserId().getHead() +
                    "' width='100%' height='100%' style='border-radius: 100%' alt=''>");
            replyMap.put("name",reply.getUserByUserId().getName());
        }
        if(replierId.equals(questionOwnerId)){
            replyMap.put("isQuestionOwner" , 1);
        }
        else{
            replyMap.put("isQuestionOwner" , 0);
        }
        if(replierId.equals(answerOwnerId)){
            replyMap.put("isAnswerer" , 1);
        }
        else{
            replyMap.put("isAnswerer" , 0);
        }
        return replyMap;
    }

    @Override
    public boolean approveReply(Integer replyId, User user) {
        if(approvalReplyDAO.searchByserIdandReplyId(user.getId(),replyId)==-1){
            Reply reply=replyDAO.get(replyId);
            Comment comment=reply.getCommentByCommentId();
            Approvalreply approvalreply=new Approvalreply();
            approvalreply.setDate(new Date());
            approvalreply.setUserByUserId(user);
            approvalreply.setReplyByReplytId(reply);
            approvalreply.setCommentByCommentId(comment);
            approvalReplyDAO.add(approvalreply);
            reply.setCount(reply.getCount()+1);
            replyDAO.update(reply);
            return true;
        }
        return false;
    }

    @Override
    public boolean cancelApprove(Integer replyId, User user) {
        Integer x = approvalReplyDAO.searchByserIdandReplyId(user.getId(),replyId);
        if(x==-1){
            return false;
        }
        approvalReplyDAO.delete(approvalReplyDAO.get(x));
        Reply reply=replyDAO.get(replyId);
        reply.setCount(reply.getCount()-1);
        replyDAO.update(reply);
        return true;
    }
}
