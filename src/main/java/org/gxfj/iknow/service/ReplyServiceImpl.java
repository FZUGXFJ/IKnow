package org.gxfj.iknow.service;

import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.dao.*;
import org.gxfj.iknow.pojo.*;
import org.gxfj.iknow.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.gxfj.iknow.util.ConstantUtil.*;

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
    @Autowired
    private MessageUtil messageUtil;

    private final int MAP_NUM = 20;
    private final int NO_SPECIAL_IDENTIFY = 0;
    private final int IS_QUESTION_OWNER = 1;
    private final int IS_ANSWERER = 2;

    @Override
    public Map<String, Object> postReply(Integer commentId, String content, Integer replyTarget) {
        User user = (User) ActionContext.getContext().getSession().get("user");
        Map<String , Object> result = new HashMap<>(HASH_MAP_NUM);
        if(user == null){
            result.put(JSON_RETURN_CODE_NAME , UN_LOGIN);
        } else if(content == null){
            result.put(JSON_RETURN_CODE_NAME , MISS_COMMENT_INF );
        } else{
            if (TextVerifyUtil.verifyCompliance(content)) {
                User targetUser= userDAO.get(replyTarget);
                Comment comment = commentDAO.getNotDelete(commentId);

                Reply reply = new Reply();
                reply.setContent(content);
                reply.setIsDelete((byte)0);
                reply.setCount(0);
                reply.setDate(new Date());
                reply.setUserByTargetUserId(targetUser);
                reply.setCommentByCommentId(comment);
                reply.setUserByUserId(user);
                replyDAO.add(reply);
                messageUtil.newMessage(3,comment.getUserByUserId(),"<p><a href='user.html?userId=" +
                        user.getId() +"'><i class=\"fas fa-link\">"+ user.getName() +
                        "</i></a>回复了你的评论，快去看看吧</P><a href='../../mobile/comment/comment.html?answerId="
                        + reply.getCommentByCommentId().getAnswerByAnswerId().getId()
                        + "'><i class=\"fas fa-link\">[回复链接]</i></a>");
                result.put(JSON_RETURN_CODE_NAME, SUCCESS);
            } else {
                result.put(JSON_RETURN_CODE_NAME, JSON_RESULT_CODE_VERIFY_TEXT_FAIL);
            }
        }
        return result;
    }

    @Override
    public Map<String, Object> showAllReplys(Integer commentId, Integer sortType) {
        User user = (User) ActionContext.getContext().getSession().get("user");
        Map<String , Object> result = new HashMap<>(HASH_MAP_NUM);

        Comment comment = commentDAO.getNotDelete(commentId);
        if (comment == null) {
            result.put(JSON_RETURN_CODE_NAME, JSON_RESULT_CODE_NON_EXISTENT);
        } else {
            result.put("comment", getComment(comment, user));
            result.put("replies", listReplies(comment, user, sortType));
            result.put(JSON_RETURN_CODE_NAME, SUCCESS);
            //在session中保存排序的方式
            ActionContext.getContext().getSession().put("sortType", sortType);
        }
        return result;

    }

    /**
     * 获得评论信息
     * @param comment 评论
     * @return 评论的信息
     */
    private Map<String , Object> getComment(Comment comment, User visitor) {
        Map<String , Object> commentMap = new HashMap<>(MAP_NUM);
        Integer commentId = comment.getId();
        commentMap = commenterIsQAOwner(commentId );
//        commentMap.put("userId" , comment.getUserByUserId().getId());
        commentMap.put("content",comment.getContent());
        commentMap.put("replyNum" , replyDAO.getCount(commentId));
        commentMap.put("approveNum" , comment.getCount());
        commentMap.put("id", comment.getId());

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
     * @param comment 评论id
     * @param visitor 查看者
     * @param sortType 排序方式
     * @return 评论的所有回复
     */
    private List<Map<String , Object>> listReplies(Comment comment, User visitor, Integer sortType) {
        List<Map<String , Object>> repliesMap  = new ArrayList<>();
        List<Reply> replies = replyDAO.getAllRepliesSort(comment.getId(), sortType);
        boolean isAnonymousOwner = (getUserIdentify(comment) != NO_SPECIAL_IDENTIFY && isAnonymous(comment));
        for (Reply reply : replies) {
            Map<String ,Object> replyMap = replierIsQAOwner(reply.getId());
            replyMap.put("id", reply.getId());
            replyMap.put("userId" , reply.getUserByUserId().getId());
            if(isAnonymousOwner){
                replyMap.put("targetName" ,ConstantUtil.ANONYMOUS_USER_NAME);
                replyMap.put("targetId" , "0");
            }
            else {
                replyMap.put("targetName" , reply.getUserByTargetUserId().getName());
                replyMap.put("targetId" , reply.getUserByTargetUserId().getId());
            }
            replyMap.put("content" , reply.getContent());
            replyMap.put("approveNum" , reply.getCount());
            replyMap.put("time" , Time.getTime(reply.getDate()));
            //如果浏览者已登录，且有点赞记录，则isApproved为1，否则为0
            int isApproved = 0;
            if (visitor != null && comment.getCount() != 0 &&
                    approvalCommentDAO.get(visitor.getId(), comment.getId()) != null) {
                replyMap.put("isApproved", 1);
            } else {
                replyMap.put("isApproved", 0);
            }
            if(visitor == null) {
                replyMap.put("viewerIsOwner", 0);
            } else {
                replyMap.put("viewerIsOwner",visitor.getId().equals(reply.getUserByUserId().getId()) ? 1 : 0);
            }
            repliesMap.add(replyMap);
        }
        return repliesMap;
    }

    /**
     * 判断评论者是否为答主或题主，是否匿名，并相应的对hash表赋值
     * @param commentId 评论id
     * @return commentMap 评论哈希表
     */
    public Map<String , Object> commenterIsQAOwner(Integer commentId) {
        Map<String ,Object> commentMap = new HashMap<>(MAP_NUM);
        Comment comment = commentDAO.getNotDelete(commentId);
        //获得用户的身份
        Integer userIdentify = getUserIdentify(comment);
        if (userIdentify != NO_SPECIAL_IDENTIFY && isAnonymous(comment)){
            commentMap.put("head", ImgUtil.changeAvatar(ConstantUtil.ANONYMOUS_USER_AVATAR, 2));
            commentMap.put("name",ConstantUtil.ANONYMOUS_USER_NAME);
            commentMap.put("userId" , 0);

        } else {
            commentMap.put("head",ImgUtil.changeAvatar(comment.getUserByUserId().getHead(), 2));
            commentMap.put("name",comment.getUserByUserId().getName());
            commentMap.put("userId" , comment.getUserByUserId().getId());
        }
        if (userIdentify == IS_QUESTION_OWNER) {
            commentMap.put("isQuestionOwner" , 1);
        } else {
            commentMap.put("isQuestionOwner" , 0);
        }
        if (userIdentify == IS_ANSWERER) {
            commentMap.put("isAnswerer" , 1);
        } else {
            commentMap.put("isAnswerer" , 0);
        }
        return commentMap;
    }

    /**
     * 获取用户的身份，是否为题主/答主
     * @param comment 评论
     * @return 用户的身份（用int表示） NO_SPECIAL_IDENTIFY为普通用户，IS_QUESTION_OWNER为题主，IS_ANSWERER为答主
     */
    private Integer getUserIdentify( Comment comment){
        //得到评论的用户的id
        Integer commentOwnerId = comment.getUserByUserId().getId();
        //获取回答
        Answer answer = comment.getAnswerByAnswerId();
        //获取问题
        Question question = answer.getQuestionByQuestionId();
        //得到题主的id
        Integer questionOwnerId = answer.getQuestionByQuestionId().getUserByUserId().getId();
        //得到答主的id
        Integer answerOwnerId = answer.getUserByUserId().getId();
        if(commentOwnerId.equals(questionOwnerId)){
            return IS_QUESTION_OWNER;
        }
        if(commentOwnerId.equals(answerOwnerId)){
            return IS_ANSWERER;
        }
        return NO_SPECIAL_IDENTIFY;
    }

    /**
     * 判断用户是否匿名
     * @param comment 评论
     * @return 是否匿名（boolean）
     */
    private boolean isAnonymous(Comment comment){
        Integer commentOnwerUserId = comment.getUserByUserId().getId();
        Answer answer = comment.getAnswerByAnswerId();
        Question question = answer.getQuestionByQuestionId();
        if ((answer.getUserByUserId().getId().equals(commentOnwerUserId)
                && answer.getIsAnonymous() == Answer.ANSWER_ANONYMOUS)) {
            return true;
        }
        if ((question.getUserByUserId().getId().equals(commentOnwerUserId)
                && question.getIsAnonymous() == Question.QUESTION_ANONYMOUS)) {
            return true;
        }
        return false;
    }
    /**
     * 判断回复者是否为答主或题主，是否匿名，并相应的对hash表赋值
     * @param replyId 评论id
     * @return commentMap 评论哈希表
     */
    public Map<String , Object> replierIsQAOwner(Integer replyId) {
        Reply reply = replyDAO.getNotDelete(replyId);
        Map<String , Object> replyMap = new HashMap<>(MAP_NUM);
        //获取回答
        Answer answer = reply.getCommentByCommentId().getAnswerByAnswerId();
        //获取问题
        Question question = answer.getQuestionByQuestionId();
        //得到题主的id
        Integer questionOwnerId = answer.getQuestionByQuestionId().getUserByUserId().getId();
        //得到答主的id
        Integer answerOwnerId = answer.getUserByUserId().getId();
        Integer replierId =reply.getUserByUserId().getId();
        boolean userIdentify = (replierId.equals(questionOwnerId) && question.getIsAnonymous() == 1) ||
                (replierId.equals(answerOwnerId) && answer.getIsAnonymous() == 1);
        if (userIdentify) {
            replyMap.put("head",ImgUtil.changeAvatar(ConstantUtil.ANONYMOUS_USER_AVATAR, 2));
            replyMap.put("name", ConstantUtil.ANONYMOUS_USER_NAME);
        } else {
            replyMap.put("head",ImgUtil.changeAvatar(reply.getUserByUserId().getHead(), 2));
            replyMap.put("name",reply.getUserByUserId().getName());
        }
        if(replierId.equals(questionOwnerId)){
            replyMap.put("isQuestionOwner" , 1);
        } else {
            replyMap.put("isQuestionOwner" , 0);
        }
        if(replierId.equals(answerOwnerId)){
            replyMap.put("isAnswerer" , 1);
        } else {
            replyMap.put("isAnswerer" , 0);
        }
        return replyMap;
    }

    @Override
    public Map<String, Object> approveReply(Integer replyId) {
        User user = (User) ActionContext.getContext().getSession().get("user");
        Map<String , Object> result = new HashMap<>(HASH_MAP_NUM);
        if(user == null){
            result.put(JSON_RETURN_CODE_NAME , UN_LOGIN);
        }
        else if(approvalReplyDAO.searchByUserIdandReplyId(user.getId(),replyId) != -1){
            result.put(JSON_RETURN_CODE_NAME , 2 );
        }
        else{
            Reply reply=replyDAO.getNotDelete(replyId);
            Comment comment=reply.getCommentByCommentId();
            Approvalreply approvalreply=new Approvalreply();
            approvalreply.setDate(new Date());
            approvalreply.setUserByUserId(user);
            approvalreply.setReplyByReplytId(reply);
            approvalreply.setCommentByCommentId(comment);
            approvalReplyDAO.add(approvalreply);
            reply.setCount(reply.getCount()+1);
            replyDAO.update(reply);

            messageUtil.newMessage(4,reply.getUserByUserId(),"<p><a href='user.html?userId=" +
                    user.getId() + "'><i class=\"fas fa-link\">"+
                    user.getName() + "</i></a>赞同了你的回复</P><a href='../../mobile/comment/comment.html?answerId=" +
                    reply.getCommentByCommentId().getAnswerByAnswerId().getId()
                    + "'><i class=\"fas fa-link\">[回复链接]</i></a>");
            result.put(JSON_RETURN_CODE_NAME , SUCCESS);
        }
        return result;
    }

    @Override
    public Map<String, Object> cancelApprove(Integer replyId) {
        User user = (User) ActionContext.getContext().getSession().get("user");
        Map<String , Object> result = new HashMap<>(HASH_MAP_NUM);
        if(user == null){
            result.put(JSON_RETURN_CODE_NAME , UN_LOGIN);
        }
        else if(approvalReplyDAO.searchByUserIdandReplyId(user.getId(),replyId) == -1){
            result.put(JSON_RETURN_CODE_NAME , 2 );
        }
        else{
            approvalReplyDAO.delete(approvalReplyDAO.get
                    (approvalReplyDAO.searchByUserIdandReplyId(user.getId(),replyId)));
            Reply reply = replyDAO.getNotDelete(replyId);
            reply.setCount(reply.getCount()-1);
            replyDAO.update(reply);
            result.put(JSON_RETURN_CODE_NAME , SUCCESS);
        }
        return result;
    }

    @Override
    public Map<String, Object> deleteReply(Integer replyId) {
        User user = (User) ActionContext.getContext().getSession().get("user");
        Map<String , Object> result = new HashMap<>(HASH_MAP_NUM);
        if(user==null){
            result.put(JSON_RETURN_CODE_NAME,UN_LOGIN);
        }
        else {
            Reply reply=replyDAO.get(replyId);
            if (user.getId().equals(reply.getUserByUserId().getId())){
                reply.setIsDelete((byte)1);
                replyDAO.update(reply);
                result.put(JSON_RETURN_CODE_NAME,SUCCESS);
            }
            else {
                result.put(JSON_RETURN_CODE_NAME,NO_REPLYER);
            }
        }
        return result;
    }
}
