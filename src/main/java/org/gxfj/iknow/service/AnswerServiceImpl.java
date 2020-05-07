package org.gxfj.iknow.service;

import org.gxfj.iknow.dao.*;
import org.gxfj.iknow.pojo.*;
import org.gxfj.iknow.util.HtmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
    final static private int ANONYMOUS = 1;
    @Override
    public String getQuestiontitle(Integer questionId) {
        Question question = questionDAO.getNotDelete(questionId);
        return question.getTitle();
    }

    @Override
    public Map<String,Object> postAnswer(Integer questionId, String content, Byte isAnonymous, User user) {
        Answer answer=new Answer();
        answer.setIsAnonymous(isAnonymous);
        answer.setDate(new Date());
        answer.setApprovalCount(0);
        answer.setUserByUserId(user);
        Question q = questionDAO.getNotDelete(questionId);
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
    public Map<String, Object> getRecommendAnswer(Integer questionId, Integer answerId, User user) {
        Map<String , Object> resultMap = new HashMap<>(MAP_NUM);

        //获得回答关联的问题
        resultMap.put("question" , getQuestionMap(questionId));
        //获得回答答主
        resultMap.put("answerer" , getAnswererMap(answerId));
        //回答的信息转化为json格式
        resultMap.put("answer" , getAnswerMap(questionId, answerId));
        //获得一个存储评论列表
        resultMap.put("comments" , getCommentsMap(questionId, answerId,user));
        //获得查看回答的用户的头像
        //未登录
        if(user == null){
            resultMap.put("userHead" , "<img src='../../head/0.jpg' width='100%' height='100%' alt=''>");
        }
        //已登录
        else{
            resultMap.put("userHead" , "<img src='../../head/" + user.getHead() + "' width='100%'  height='100%' alt=''>");
        }

        Answer answer=answerDAO.getNotDelete(answerId);
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
     * @param questionId 问题id
     * @return 问题信息的json
     */
    private Map<String , Object> getQuestionMap(Integer questionId){
        Question question = questionDAO.getNotDelete(questionId);
        Map<String , Object> questionMap = new HashMap<>(MAP_NUM);
        //将问题变成json格式
        questionMap.put("id" , question.getId());
        questionMap.put("title" , question.getTitle());
        questionMap.put("content" , question.getContent());
        questionMap.put("answerCount",questionDAO.getAnswerCount(questionId));
        questionMap.put("isSolved",question.getQuestionstateByStateId().getId() == 1 ? 0 : 1);
        return questionMap;
    }

    /**
     * 获取答主的一部分信息
     * @param answerId 问题id
     * @return json形式的答主信息
     */
    private Map<String , Object> getAnswererMap(Integer answerId){
        Answer answer = answerDAO.getNotDelete(answerId);
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
            answererMap.put("head" , "<img src='../../head/" + answer.getUserByUserId().getHead() + "' width='100%'" +
                    " height='100%' alt=''>");
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
     * @param answerId 回答id
     * @return json形式的回答信息
     */
    private Map<String , Object> getAnswerMap(Integer questionId, Integer answerId){
        Answer answer = answerDAO.getNotDelete(answerId);
        Question question = questionDAO.getNotDelete(questionId);
        Map<String , Object> answerMap = new HashMap<>(MAP_NUM);
        answerMap.put("content" , answer.getContent());
        answerMap.put("approveNum" , answer.getApprovalCount());
        answerMap.put("commentNum" , commentDAO.getCount(answerId));
        if(question.getAnswerByAdoptId() != null && question.getAnswerByAdoptId().getId().equals(answer.getId())) {
            answerMap.put("isAdopt" , 1);
        } else {
            answerMap.put("isAdopt" , 0);
        }
        answerMap.put("isAnonymous",answer.getIsAnonymous() == 0 ? 0 : 1);
        return answerMap;
    }

    /**
     * 获取回答关联的一部分评论的信息
     * @param questionId 问题id
     * @param answerId 回答id
     * @param user 操作的用户
     * @return Json形式的评论信息
     */
    private List<Map<String , Object>> getCommentsMap(Integer questionId , Integer answerId , User user){

        List<Map<String , Object>> commmentsMap = new ArrayList<>();
        List<Comment> comments = commentDAO.listByAnswerId(answerId , 0 , COMMENT_NUM);
        for(Comment comment : comments){
            Map<String , Object> commentMap = new HashMap<>(MAP_NUM);
            commentMap.put("id", comment.getId());
            commentMap.put("uid" , comment.getUserByUserId().getId());
            commentMap.put("uName" , comment.getUserByUserId().getName());
            commentMap.put("uHead" , "<img src='../../head/" + comment.getUserByUserId().getHead() + "' width='100%' " +
                    "height='100%'  style='border-radius:100%' alt=''>");
            commentMap.put("content" , comment.getContent());
            commentMap.put("approveNum" , comment.getCount());

            //如果当前浏览者已登录，且评论有人点赞，且用户对该评论点过赞则为1,否则为0
            if (user != null && comment.getCount() != 0 &&
                    approvalCommentDAO.get(user.getId(), comment.getId()) != null) {
                commentMap.put("isApproved", 1);
            } else {
                commentMap.put("isApproved", 0);
            }

            int isQuestionOwner = 0;
            int isAnswerer = 0;
            if(comment.getUserByUserId().getId().equals(questionDAO.getNotDelete(questionId).getUserByUserId().getId())){
                isQuestionOwner = 1;
                if (comment.getAnswerByAnswerId().getQuestionByQuestionId().getIsAnonymous() == 1) {
                    commentMap.put("uName","匿名用户");
                    commentMap.put("uHead" , "<img src='../../head/0.jpg' width='100%' height='100%' " +
                            "style='border-radius:100%' alt=''>");
                }
            }
            if(comment.getUserByUserId().getId().equals(answerDAO.getNotDelete(answerId).getUserByUserId().getId())){
                isAnswerer = 1;
                if (comment.getAnswerByAnswerId().getIsAnonymous() == 1) {
                    commentMap.put("uName","匿名用户");
                    commentMap.put("uHead" , "<img src='../../head/0.jpg' width='100%' height='100%'" +
                            " style='border-radius:100%' alt=''>");
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
        Answer answer = answerDAO.getNotDelete(answerId);
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
        Answer answer = answerDAO.getNotDelete(answerId);
        if (answer.getUserByUserId().getId().equals(user.getId())){
            answer.setIsAnonymous((byte)1);
            answerDAO.update(answer);
            return true;
        }
        return false;
    }

    @Override
    public Map<String, Object> getRecommendAnswer(Integer count) {
        List<Answer> answers = selectRecommendAnswer(count);
        List<Map<String,Object>> recommendList = new ArrayList<>();
        Map<String,Object> recommend;
        for(Answer answer:answers){
            recommend = new HashMap<>(MAP_NUM);
            Question question = answer.getQuestionByQuestionId();
            User user = answer.getUserByUserId();
            User quser = question.getUserByUserId();
            recommend.put("questionId",question.getId());
            recommend.put("questionTitle",question.getTitle());
            recommend.put("answererId",user.getId());
            //判断答主是否匿名
//            boolean isAnonymous = (quser.getId().equals(user.getId()) && question.getIsAnonymous() == 1) ;
            boolean isAnonymous = (answer.getIsAnonymous() == ANONYMOUS);
            if(isAnonymous) {
                recommend.put("answererHead","<img src='../head/0.jpg' width='100%' height='100%'" +
                        " style='border-radius: 100%' alt=''>");
                recommend.put("answererName","匿名用户");
                recommend.put("answererLevel",0);
                recommend.put("answererBadge",0);
            } else {
                recommend.put("answererHead","<img src='../head/" + user.getHead() +
                        "' width='100%' height='100%' style='border-radius: 100%' alt=''>");
                recommend.put("answererName",user.getName());
                recommend.put("answererLevel",levelDAO.getLevelByExp(user.getExp()));
                recommend.put("answererBadge",user.getBadgeNum());
            }
            recommend.put("answerId",answer.getId());
            //使用HtmlUtil工具类，将图片转换掉
            recommend.put("content", HtmlUtil.changeImgTag(answer.getContent()));
            recommend.put("approveNum",answer.getApprovalCount());
            recommend.put("commentNum",commentDAO.getCount(answer.getId()));
            Answer au=question.getAnswerByAdoptId();
            if (au!=null&&au.getId().equals(answer.getId())) {
                recommend.put("isAdopt",1);
            } else {
                recommend.put("isAdopt",0);
            }
            recommendList.add(recommend);
        }
        Map<String,Object> result = new HashMap<>(MAP_NUM);
        result.put("recommends",recommendList);
        return result;
    }

    /**
     * 从数据库中获取推荐的问题
     * @param count 推荐问题的条数
     * @return 推荐的问题，没有则内容为空
     */
    private List<Answer> selectRecommendAnswer(Integer count) {
        return answerDAO.list(0,count);
    }

    private final static int BIG_HASH_MAP_NUM = 655535;
    //根据多少天的浏览记录来生成推荐
    private final static int RECOMMENT_BY_DAYS = 3;

    @Autowired
    QuestionTypeDAO questionTypeDAO;
    @Autowired
    BrowsingHistoryDAO browsingHistoryDAO;

    //存储所有用户的推荐回答表
    private Map<Integer, Object> recommentAnswerMap;


    /**
     * 推荐问题，计划每十五分钟执行一次，更新所有用户的推荐问题表
     */
    @Scheduled(cron = "0/5 * * * * *")
    private void createRecommendAnswer() {
        if (recommentAnswerMap == null) {
            recommentAnswerMap = new HashMap<>(BIG_HASH_MAP_NUM);
        } else {
            recommentAnswerMap.clear();
        }

        List<Questiontype> questiontypeList = questionTypeDAO.list();

        List<Browsinghistory> browsinghistoryList = browsingHistoryDAO.listInLastDay(RECOMMENT_BY_DAYS);

        //TODO: 完成剩下的推荐算法


        System.out.println("定时器");
    }
}