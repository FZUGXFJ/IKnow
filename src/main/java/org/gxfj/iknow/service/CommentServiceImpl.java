package org.gxfj.iknow.service;

import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;
import org.gxfj.iknow.dao.*;
import org.gxfj.iknow.pojo.*;
import org.gxfj.iknow.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.*;

import static org.gxfj.iknow.util.ServiceConstantUtil.JSON_RESULT_CODE_VERIFY_TEXT_FAIL;

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
    @Autowired
    MessageUtil messageUtil;
    @Autowired
    AchievementUtil achievementUtil;

    final static private int MAP_NUM = 30;
    final static private int REPLY_NUM = 2;

    @Override
    public Map<String, Object> postComment(User user, Integer answerId, String content){

        Map<String , Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        if(user == null){
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.UN_LOGIN);
        }
        else if(content == null){
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.MISS_COMMENT_INF );
        }
        else{
            if (TextVerifyUtil.verifyCompliance(content)) {
                Comment comment = new Comment();
                Answer answer = answerDAO.getNotDelete(answerId);
                comment.setUserByUserId(user);
                comment.setContent(content);
                comment.setAnswerByAnswerId(answer);
                comment.setDate(new Date());
                comment.setIsDelete((byte)0);
                comment.setCount(0);

                commentDAO.add(comment);

                messageUtil.newMessage(3,answer.getUserByUserId(),"<p><a href='user.html?userId=" +
                        user.getId() +"'><i class=\"fas fa-link\">"+ user.getName() +
                        "</i></a>评论了你的回答，快去看看吧</P><a href='../../mobile/comment/comment.html?answerId="
                        + answerId + "'><i class=\"fas fa-link\">[评论链接]</i></a>");
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
            } else {
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.JSON_RESULT_CODE_VERIFY_TEXT_FAIL);
            }
        }
        return result;
    }

    @Override
    public Map<String, Object> getComments(User user, Integer answerId, Integer sort){
        Map<String , Object> result = new HashMap<>(ConstantUtil.HASH_MAP_NUM);
        Integer commentsSort;
        if (sort == null) {
            commentsSort = ConstantUtil.COMMENT_DEFAULT_SORT;
        }
        else {
            commentsSort = sort;
        }
        ActionContext.getContext().getSession().put("commentsort",commentsSort);
        //获取问题下的20条评论
        List<Comment> comments = commentDAO.listByAnswerIdSort(answerId,0,20,commentsSort);
        //json数组
        List<Map<String, Object>> commentListMap;
        //获取回答
        Answer answer = answerDAO.getNotDelete(answerId);
        //获取问题
        Question question = answer.getQuestionByQuestionId();
        //获取回答者
        User answerOwner = answer.getUserByUserId();
        //获取题主
        User questionOwner = question.getUserByUserId();
        //获取问题评论数
        Integer count = commentDAO.getCount(answerId);
        result.put("commentNum",count);
        if (count != 0) {
            commentListMap = getCommentsMapArray(comments, questionOwner, answerOwner, question, answer, user);
        } else {
            commentListMap = new ArrayList<>();
        }
        result.put("comments",commentListMap);
        result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        return result;
    }

    @Override
    public Map<String, Object> approveComment(User user, Integer commentId) {
        Map<String , Object> result = new HashMap<>(ConstantUtil.HASH_MAP_NUM);
        if (user != null) {
            Approvalcomment approvalcomment = approvalCommentDAO.get(user.getId(), commentId);
            if (approvalcomment == null) {
                //更新评论记录中评论点赞数
                Comment comment = commentDAO.getNotDelete(commentId);
                comment.setCount(comment.getCount() + 1);
                commentDAO.update(comment);

                //向评论点赞表中插入记录
                approvalcomment = new Approvalcomment();
                approvalcomment.setCommentByCommentId(comment);
                approvalcomment.setUserByUserId(user);
                approvalcomment.setDate(new Date());
                approvalCommentDAO.add(approvalcomment);

                messageUtil.newMessage(4,comment.getUserByUserId(),"<p><a href='user.html?userId=" +
                        user.getId() + "'><i class=\"fas fa-link\">"+
                        user.getName() + "</i></a>赞同了你的评论</P><a href='../../mobile/comment/comment.html?answerId="
                        + comment.getAnswerByAnswerId().getId() + "'><i class=\"fas fa-link\">[评论链接]</i></a>");

                //成就：我爱吃瓜
                if (approvalCommentDAO.getCountByUser(user).equals(100)) {
                    achievementUtil.completeAchievement(user, Achievement.ACHIEVEMENT_I_AM_ONLOOKER);
                }

                result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
            } else {
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.RESULT_CODE_APPROVED);
            }
        } else {
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.UN_LOGIN);
        }
        return result;
    }

    @Override
    public Map<String, Object> cancelApprove(User user, Integer commentId) {
        Map<String , Object> result = new HashMap<>(ConstantUtil.HASH_MAP_NUM);
        if (user != null) {
            Approvalcomment approvalcomment = approvalCommentDAO.get(user.getId(), commentId);
            if (approvalcomment != null) {
                Comment comment = commentDAO.getNotDelete(commentId);
                comment.setCount(comment.getCount() - 1);
                commentDAO.update(comment);
                approvalCommentDAO.delete(approvalcomment);
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
            } else {
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.RESULT_CODE_NOT_APPROVED);
            }
        } else {
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.UN_LOGIN);
        }
        return result;
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
    private Map<String, Object> commenterIsQAOwner(User questionOwner, User answerOwner, Comment comment,
                                                   Question question, Answer answer){
        User commentUser = comment.getUserByUserId();
        Map<String, Object> commentMap = new HashMap<>(MAP_NUM);
        boolean isAnonymous = (questionOwner.getId().equals(commentUser.getId()) && question.getIsAnonymous() == 1)
                ||(answerOwner.getId().equals(commentUser.getId()) && answer.getIsAnonymous() == 1);
        if(isAnonymous){
            commentMap.put("head", ImgUtil.changeAvatar(ConstantUtil.ANONYMOUS_USER_AVATAR, 2));
            commentMap.put("name",ConstantUtil.ANONYMOUS_USER_NAME);
        }else{
            commentMap.put("head",ImgUtil.changeAvatar(commentUser.getHead(), 2));
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
        //回复者
        boolean isAnonymous1 = (questionOwner.getId().equals(replyUser.getId()) && question.getIsAnonymous() == 1)
                ||(answerOwner.getId().equals(replyUser.getId()) && answer.getIsAnonymous() == 1);
        //回复对象
        boolean isAnonymous2 = (questionOwner.getId().equals(targetUser.getId()) && question.getIsAnonymous() == 1)
                ||(answerOwner.getId().equals(targetUser.getId()) && answer.getIsAnonymous() == 1);

        if(isAnonymous1){
            replyMap.put("name", ConstantUtil.ANONYMOUS_USER_NAME);
            replyMap.put("head",ImgUtil.changeAvatar(ConstantUtil.ANONYMOUS_USER_AVATAR, 2));
        }
        else{
            replyMap.put("name",replyUser.getName());
            replyMap.put("head",ImgUtil.changeAvatar(replyUser.getHead(), 2));
        }
        if(isAnonymous2) {
            replyMap.put("targetName", ConstantUtil.ANONYMOUS_USER_NAME);
        }
        else{
            replyMap.put("targetName",targetUser.getName());
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
            , Question question, Answer answer, User visitor) {
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

            //如果当前浏览者已登录，且评论有人点赞，且用户对该评论点过赞则为1,否则为0
            if (visitor != null) {
                //判断浏览者是否为该评论的评论者
                commentMap.put("viewerIsOwner",visitor.getId().equals(commentUser.getId())? 1 : 0);
                if (comment.getCount() != 0 && approvalCommentDAO.get(visitor.getId(), comment.getId()) != null) {
                    commentMap.put("isApproved", 1);
                } else {
                    commentMap.put("isApproved", 0);
                }
            }

            commentMap.put("time", Time.getTime(comment.getDate()));
            int num = replyDAO.getCount(comment.getId());
            commentMap.put("replyNum", num);
            if (num != 0) {
                //TODO:这里用了魔法值，后面考虑用枚举来解决
                 replies = replyDAO.listByCommentIdSort(comment.getId(), 0, REPLY_NUM,1);
                replyListMap = getCommentReplyMapArray(replies,questionOwner,answerOwner,question,answer, visitor);
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
    private List<Map<String, Object>> getCommentReplyMapArray(List<Reply> replies, User questionOwner, User answerOwner,
                                                              Question question, Answer answer, User visitor) {
        List<Map<String, Object>> replyListMap = new ArrayList<>();
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
            //如果浏览者已登录，且有点赞记录，则isApproved为1，否则为0
            int isApproved = 0;
            if (visitor != null) {
                isApproved = approvalReplyDAO.searchByUserIdandReplyId(visitor.getId(), reply.getId()) == -1 ? 0 : 1;
            }
            replyMap.put("isApproved", isApproved);
            if (visitor!=null){
                //是否是本身
                replyMap.put("viewerIsOwner",replyUser.getId().equals(visitor.getId()) ? 1 : 0);
            }
            replyListMap.add(replyMap);
        }
        return replyListMap;
    }
    @Override
    public Map<String, Object> moreComments(User user, Integer answerId, Integer start, Integer sort){
        Map<String , Object> result = new HashMap<>(ConstantUtil.HASH_MAP_NUM);
//        Integer sort =(Integer)ActionContext.getContext().getSession().get("commentsort");
        if(sort ==null){
            sort =ConstantUtil.COMMENT_DEFAULT_SORT;
        }
        //获取问题评论数
        Integer count = commentDAO.getCount(answerId);
        if (count == null){
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.NO_MORE);
        }
        else {
            //获取问题下的20条评论
            List<Comment> comments = commentDAO.listByAnswerIdSort(answerId,start,20,sort);
            
            //json数组
            List<Map<String, Object>> commentListMap;
            //获取回答
            Answer answer = answerDAO.getNotDelete(answerId);
            //获取问题
            Question question = answer.getQuestionByQuestionId();
            //获取回答者
            User answerOwner = answer.getUserByUserId();
            //获取题主
            User questionOwner = question.getUserByUserId();
            result.put("commentNum",count);
            commentListMap = getCommentsMapArray(comments, questionOwner, answerOwner, question, answer, user);
            result.put("comments",commentListMap);
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        }
        return result;
    }

    @Override
    public Map<String, Object> deleteComment(User user, Integer commentId) {
        Map<String , Object> result = new HashMap<>(ConstantUtil.HASH_MAP_NUM);
        if(user==null){
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.UN_LOGIN);
        }
        else {
            Comment comment=commentDAO.get(commentId);
            User commentUserByUserId = comment.getUserByUserId();
            if (commentUserByUserId.getId().equals(user.getId())){
                comment.setIsDelete(Comment.COMMENT_DELETED);
                for (Reply reply : comment.getRepliesById()) {
                    replyDAO.delete(reply);
                }
                commentDAO.delete(comment);
                commentDAO.update(comment);
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.SUCCESS);
            }
            else {
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.NO_COMMENTER);
            }
        }
        return result;
    }
}
