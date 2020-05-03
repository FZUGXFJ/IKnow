package org.gxfj.iknow.service;

import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.dao.*;
import org.gxfj.iknow.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author erniumo ,hhj
 */
@Service("answerService")
public class AnswerServiceImpl implements AnswerService{
    @Autowired
    private AnswerDAO answerDAO;
    @Autowired
    private QuestionDAO questionDAO;
    @Autowired
    private CommentDAO commentDAO;
    @Autowired
    private LevelDAO levelDAO;
    @Autowired
    private ApprovalCommentDAO approvalCommentDAO;
    @Autowired
    private UserDAO userDAO;




    final static private int MAP_NUM = 20;
    final static private int COMMENT_NUM = 2;
    final static private int QUESTION_STATE_SOLVE = 2;
    @Override
    public String getQuestiontitle(Integer qId) {
        Question question = questionDAO.get(qId);
        return question.getTitle();
    }

    @Override
    public Map<String,Object> postAnswer(Integer qId, String content, Byte isAnonymous,User user) {
        Answer answer=new Answer();
        answer.setIsAnonymous(isAnonymous);
        answer.setDate(new Date());
        answer.setApprovalCount(0);
        answer.setUserByUserId(user);
        Question q = questionDAO.get(qId);
        answer.setQuestionByQuestionId(q);
        answer.setIsDelete((byte)0);
        answer.setIsRoof((byte)0);
        answer.setContent(content);

        answerDAO.add(answer);
        Map<String, Object> result= new HashMap<>(MAP_NUM);
        result.put("answerID",answer.getId());
        return result;
    }


    @Override
    public Map<String, Object> getAnswer(Integer qId, Integer aId , User user) {
        Map<String , Object> resultMap = new HashMap<>(MAP_NUM);

        //获得回答关联的问题
        resultMap.put("question" , getQuestionMap(qId));
        //获得回答答主
        resultMap.put("answerer" , getAnswererMap(aId));
        //回答的信息转化为json格式
        resultMap.put("answer" , getAnswerMap(qId , aId));
        //获得一个存储评论列表
        resultMap.put("comments" , getCommentsMap(qId, aId ,user));
        //获得查看回答的用户的头像
        //未登录
        if(user == null){
            resultMap.put("userHead" , "<img src='../../head/0.jpg' width='100%' height='100%' alt=''>");
        }
        //已登录
        else{
            resultMap.put("userHead" , "<img src='../../head/" + user.getHead() + "' width='100%' height='100%' alt=''>");
        }
        Answer answer=answerDAO.get(aId);
        int viewerIsAnswerer = 0;
        int viewerIsQuestionOwner = 0;
        if (user != null) {
            if (user.getId().equals(answer.getQuestionByQuestionId().getUserByUserId().getId())) {
                viewerIsQuestionOwner = 1;
            }
            if (user.getId().equals(answer.getUserByUserId().getId())) {
                viewerIsAnswerer = 1;
            }
        }
        resultMap.put("viewerIsAnswerer", viewerIsAnswerer);
        resultMap.put("viewerIsQuestionOwner", viewerIsQuestionOwner);
        return resultMap;
    }

    /**
     * 获取回答相关问题一部分信息
     * @param qId 问题id
     * @return 问题信息的json
     */
    private Map<String , Object> getQuestionMap(Integer qId){
        Question question = questionDAO.get(qId);
        Map<String , Object> questionMap = new HashMap<>(MAP_NUM);
        //将问题变成json格式
        questionMap.put("id" , question.getId());
        questionMap.put("title" , question.getTitle());
        questionMap.put("content" , question.getContent());
        questionMap.put("answerCount",questionDAO.getAnswerCount(qId));
        return questionMap;
    }

    /**
     * 获取答主的一部分信息
     * @param aId 问题id
     * @return json形式的答主信息
     */
    private Map<String , Object> getAnswererMap(Integer aId){
        Answer answer = answerDAO.get(aId);
        User answerer = answer.getUserByUserId();
        Map<String , Object> answererMap = new HashMap<>(MAP_NUM);
        //答主的信息转化为Json格式
        //匿名设置
        if(answer.getIsAnonymous() == 1){
            answererMap.put("id" , 0);
            answererMap.put("head" , "<img src='../../head/0.jpg' width='100%' height='100%' alt=''>");
            answererMap.put("name" , "匿名用户");
            answererMap.put("level" , 0);
            answererMap.put("badgeNum" , 0);
            /*
             * TODO β版本实现回答者的身份
             */
        }
        else {
            answererMap.put("id" , answerer.getId());
            answererMap.put("head" , "<img src='../../head/" + answer.getUserByUserId().getHead() + "' width='100%' height='100%' alt=''>");
            answererMap.put("name" , answerer.getName());
            answererMap.put("level",levelDAO.getLevelByExp(answer.getUserByUserId().getExp()));
            answererMap.put("badgeNum" , answerer.getBadgeNum());
            /*
             * TODO β版本实现回答者的身份
             */
        }
        return answererMap;
    }

    /**
     * 获取回答的信息
     * @param aId 回答id
     * @return json形式的回答信息
     */
    private Map<String , Object> getAnswerMap(Integer qId , Integer aId){
        Answer answer = answerDAO.get(aId);
        Question question = questionDAO.get(qId);
        Map<String , Object> answerMap = new HashMap<>(MAP_NUM);
        answerMap.put("content" , answer.getContent());
        answerMap.put("approveNum" , answer.getApprovalCount());
        answerMap.put("commentNum" , commentDAO.getCount(aId));
        if(question.getAnswerByAdoptId() != null && question.getAnswerByAdoptId().getId().equals(answer.getId())){
            answerMap.put("isAdopt" , 1);
        }
        else{
            answerMap.put("isAdopt" , 0);
        }
        return answerMap;
    }

    /**
     * 获取回答关联的一部分评论的信息
     * @param qId 问题id
     * @param aId 回答id
     * @param user 操作的用户
     * @return Json形式的评论信息
     */
    private List<Map<String , Object>> getCommentsMap(Integer qId , Integer aId , User user){

        List<Map<String , Object>> commmentsMap = new ArrayList<>();
        List<Comment> comments = commentDAO.listByAnswerId(aId , 0 , COMMENT_NUM);
        for(Comment comment : comments){
            Map<String , Object> commentMap = new HashMap<>(MAP_NUM);
            commentMap.put("uid" , comment.getUserByUserId().getId());
            commentMap.put("uName" , comment.getUserByUserId().getName());
            commentMap.put("uHead" , "<img src='../../head/" + comment.getUserByUserId().getHead() + "' width='100%' height='100%'  style='border-radius:100%' alt=''>");
            commentMap.put("content" , comment.getContent());
            commentMap.put("approveNum" , comment.getCount());
            int isQuestionOwner = 0;
            int isAnswerer = 0;
            if(comment.getUserByUserId().getId().equals(questionDAO.get(qId).getUserByUserId().getId())){
                isQuestionOwner = 1;
                if (comment.getAnswerByAnswerId().getQuestionByQuestionId().getIsAnonymous() == 1) {
                    commentMap.put("uName","匿名用户");
                    commentMap.put("uHead" , "<img src='../../head/0.jpg' width='100%' height='100%' style='border-radius:100%' alt=''>");
                }
            }
            if(comment.getUserByUserId().getId().equals(answerDAO.get(aId).getUserByUserId().getId())){
                isAnswerer = 1;
                if (comment.getAnswerByAnswerId().getIsAnonymous() == 1) {
                    commentMap.put("uName","匿名用户");
                    commentMap.put("uHead" , "<img src='../../head/0.jpg' width='100%' height='100%' style='border-radius:100%' alt=''>");
                }
            }
            commentMap.put("isQuestionOwner" , isQuestionOwner);
            commentMap.put("isAnswerer" , isAnswerer);
            commmentsMap.add(commentMap);
        }
        return commmentsMap;
    }

    @Override
    public Boolean adoptAnswer(User user, Integer answerId) {
        Answer answer = answerDAO.get(answerId);
        Question question = answer.getQuestionByQuestionId();

        //构造已解决的问题状态对象
        Questionstate questionstate = new Questionstate();
        questionstate.setId(QUESTION_STATE_SOLVE);

        if (user.getId().equals(question.getUserByUserId().getId())) {
            //更新问题的采纳回答id
            question.setAnswerByAdoptId(answer);
            //更新问题状态为已解决
            question.setQuestionstateByStateId(questionstate);
            questionDAO.update(question);

            //增加用户的徽章数
            user.setBadgeNum(user.getBadgeNum() + 1);
            userDAO.update(user);
            
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean cancelAdopt(User user, Integer answerId) {
        Answer answer = answerDAO.get(answerId);
        if (answer.getUserByUserId().getId().equals(user.getId())){
            answer.setIsAnonymous((byte)1);
            answerDAO.update(answer);
            return true;
        }
        return false;
    }
}