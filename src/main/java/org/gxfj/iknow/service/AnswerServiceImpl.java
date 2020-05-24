package org.gxfj.iknow.service;

import org.gxfj.iknow.dao.*;
import org.gxfj.iknow.pojo.*;
import org.gxfj.iknow.util.HtmlUtil;
import org.gxfj.iknow.util.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
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
    @Autowired
    private ApprovalAnswerDAO approvalAnswerDAO;
    @Autowired
    private OppositionAnswerDAO oppositionAnswerDAO;

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
    public Map<String, Object> getRecommendAnswer(Integer userId, Integer count) {
        List<Answer> answers = selectRecommendAnswer(userId, count);
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
            Integer x=approvalAnswerDAO.searchByUserIdandAnswerId(userId,answer.getId());
            Integer y=oppositionAnswerDAO.searchByUserIdandAnswerId(userId,answer.getId());
            Integer z=0;
            if (x != -1) {
                z=1;
            }
            else if(y!=-1){
                z=2;
            }
            recommend.put("approveState",z);
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
    private List<Answer> selectRecommendAnswer(Integer userId, Integer count) {
        List<Answer> result = new ArrayList<>();
        if (userId == null) {
            // 对于未登录的浏览者，依旧采用推荐最先的回答
            return answerDAO.list(count);
        } else {
            List<Answer> answerList = null;
            if (recommentAnswerMap.containsKey(userId)) {
                answerList = (List<Answer>) recommentAnswerMap.get(userId);
            } else {
                answerList = (List<Answer>) recommentAnswerMap.get(NEW_USER_RECOMMEND_KEY);
            }
            for (int i = 0; i < count && answerList.size() != 0; i++) {
                result.add(answerList.get(0));
                answerList.remove(0);
            }
        }
        return result;
    }

    private final static int BIG_HASH_MAP_NUM = 655535;
    private final static int SMALL_HASH_MAP_NUM = 100;
    /**
     * 根据多少天的浏览记录来生成推荐
     */
    private final static int RECOMMENT_BY_DAYS = 60;
    /**
     * 预生成多少个推荐回答
     */

    private final static int PRE_RECOMMENT_NUM = 100;
    /**
     * 推荐需要满足相关性下限
     */
    private final static double CORRELATION_LOWER_LIMIT = 0.5;

    @Autowired
    QuestionTypeDAO questionTypeDAO;
    @Autowired
    BrowsingHistoryDAO browsingHistoryDAO;

    /**
     * 存储所有用户的推荐回答表
     */
    private Map<Integer, Object> recommentAnswerMap;
    /**
     * 推荐回答表黎没有的用户，统一获取该key
     */
    private final static int NEW_USER_RECOMMEND_KEY = 0;


    /**
     * 推荐问题，计划每十分钟执行一次，更新所有用户的推荐问题表
     * TODO: 现在采用的算法是基于物品的协同过滤算法，但评价采用的是回答浏览次数，出现冷启动。
     * @return
     */
    @Scheduled(cron = "0/10 * * * * *")
    @Override
    public void createRecommendAnswer() {
        if (recommentAnswerMap == null) {
            recommentAnswerMap = new HashMap<>(BIG_HASH_MAP_NUM);
        }

        //获得所有的回答
        List<Answer> answerList = answerDAO.list();
        int answersLength = answerList.size();
        //获得所有的非封禁用户uid
        List<Integer> userIdList = userDAO.getActiveUserId();
        int usersLength = userIdList.size();
        //获得所有用户的近RECOMMENT_BY_DAYS天的浏览记录
        List<Browsinghistory> browsinghistoryList = browsingHistoryDAO.listInLastDay(RECOMMENT_BY_DAYS);
        //记录用户最后的回答
        Map<Integer, Answer> lastBrowsinghistoryMap = new HashMap<>(BIG_HASH_MAP_NUM);

        //将回答的id应映射到answerList的下标
        Map<Integer, Integer> answerMap = new HashMap<>(BIG_HASH_MAP_NUM);
        //将用户的uid映射到userIdList下标
        Map<Integer, Integer> userIdMap = new HashMap<>(BIG_HASH_MAP_NUM);
        //初始化映射
        for (int i = 0; i < answersLength; i++) {
            answerMap.put(answerList.get(i).getId(), i);
        }
        for (int i = 0; i < usersLength; i++) {
            userIdMap.put(userIdList.get(i), i);
        }

        //List存储回答浏览记录的表
        List<List<Integer>> browsingHistoryStatistic = new ArrayList<>();
        //初始化
        for (int i = 0; i < answersLength; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < usersLength; j++) {
                row.add(0);
            }
            browsingHistoryStatistic.add(row);
        }

        //构建浏览表，行是回答id，列是用户id
        for (Browsinghistory browsinghistory : browsinghistoryList) {
            //通过浏览记录获得浏览的问题的问题分类id
            Integer questiontypeId = browsinghistory.getQuestionByQuestionId().getQuestiontypeByTypeId().getId();
            //通过浏览记录获得浏览的回答的id
            Integer answerId = browsinghistory.getAnswerByAnswerId().getId();
            //通过浏览记录获得浏览的用户的uid
            Integer userId = browsinghistory.getUserByUserId().getId();
            //将用户uid转化为List中的下标
            Integer userIdListPoint = userIdMap.get(userId);
            //获得回答对应的行的List
            List<Integer> row = browsingHistoryStatistic.get(answerMap.get(answerId));
            //将行中用户的浏览记录+1
            row.set(userIdListPoint, row.get(userIdListPoint) + 1);
            lastBrowsinghistoryMap.put(userId, browsinghistory.getAnswerByAnswerId());
        }

        //DEBUG：查看构建的浏览表
        int indextmp = 0;
        String tmp_str = "    ";
        for (Integer userid : userIdList) {
            tmp_str += userid + ",";
        }
//        System.out.println(tmp_str);
        for (List<Integer> list : browsingHistoryStatistic) {
            String strout = answerList.get(indextmp).getId() + " : ";

            for (Integer integer : list) {
                strout += integer + ",";
            }
//            System.out.println(strout);
            indextmp++;
        }

        //2) 计算回答与回答之间的pearson系数
        double pearsonMatrix[][] = getPearsonMatrix(answersLength,usersLength,browsingHistoryStatistic);

        //DEBUG：查看计算完的Pearson矩阵
//        System.out.println("\n\n");
        for (int i = 0; i < answersLength; i++) {
            String str_tmp = "";
            for (int j = 0; j < answersLength; j++) {
                str_tmp += pearsonMatrix[i][j] + ", ";
            }
//            System.out.println(str_tmp);
        }

        generateRecommendAnswerTable(usersLength,answersLength,userIdList,answerList,pearsonMatrix,
                browsingHistoryStatistic);

        //通知完成推荐更新
//        System.out.println("推荐表更新完毕");
    }

    /**
     * 计算问题之间的Pearson
     * @param answersLength 问题的个数，即矩阵的长度
     * @param usersLength 用户的个数
     * @param browsingHistoryStatistic 问题被用户浏览的记录表，问题为行，用户为列
     * @return 问题的Pearson矩阵
     */
    private double[][] getPearsonMatrix(int answersLength, int usersLength,
                                        List<List<Integer>> browsingHistoryStatistic) {
        double[][] pearsonMatrix = new double[answersLength][answersLength];
        Map<BigInteger, Double> mapX = new HashMap<>(BIG_HASH_MAP_NUM);
        Map<BigInteger, Double> mapY = new HashMap<>(BIG_HASH_MAP_NUM);
        //初始化为0
        for (int i = 0; i < usersLength; i++) {
            mapX.put(BigInteger.valueOf(i),0.0);
            mapY.put(BigInteger.valueOf(i), 0.0);
        }
        //两重for循环，计算整个矩阵
        for (int i = 0; i < answersLength; i++) {
            for (int j = 0; j < answersLength; j++) {
                if (i < j) {
                    for (int k = 0; k < usersLength; k++) {

                        mapX.put(BigInteger.valueOf(k), Double.valueOf(browsingHistoryStatistic.get(i).get(k)));
                    }
                    for (int k = 0; k < usersLength; k++) {
                        mapY.put(BigInteger.valueOf(k), Double.valueOf(browsingHistoryStatistic.get(j).get(k)));
                    }
                    pearsonMatrix[i][j] = MathUtil.caculatePearson(mapX, mapY);
                } else if (i == j) {
                    //自己与自己的为0
                    pearsonMatrix[i][j] = 0;
                } else if (i > j) {
                    //计算结果上i与j的Pearson跟j与i的Pearson相同
                    pearsonMatrix[i][j] = pearsonMatrix[j][i];
                }
            }
        }
        return pearsonMatrix;
    }

    private void generateRecommendAnswerTable(int usersLength, int answersLength, List<Integer> userIdList,
                                              List<Answer> answerList, double [][] pearsonMatrix,
                                              List<List<Integer>> browsingHistoryStatistic) {
        //TODO: 当前推荐仅根据最多浏览的一个回答推荐，后面可以修改成多个不同的回答来推荐。
        //对所有用户，根据浏览记录顺序推荐
        for (int i = 0; i < usersLength; i++) {
            Integer userId = userIdList.get(i);

            //找出浏览最多的回答，根据改回答进行推荐，不足 PRE_RECOMMENT_NUM 个数时，推荐最新的回答
            int max = 0;
            for (int j = 1; j < answersLength; j++) {
                if (browsingHistoryStatistic.get(j).get(i) > browsingHistoryStatistic.get(max).get(i)) {
                    max = j;
                }
            }

            //已推荐数
            int recommendnum = 0;
            List<Answer> recommendList = new ArrayList<>();
            if (browsingHistoryStatistic.get(max).get(i) != 0) {
                //对所有的问题与用户浏览最多的回答进行计算，并推荐
                for (int j = 0; j < answersLength; j++) {
                    //这里采用了加权总分大于一定值就推荐。改进：相关系数前 PRE_RECOMMENT_NUM 个推荐
                    if (browsingHistoryStatistic.get(j).get(i) == 0 &&
                            pearsonMatrix[max][j] > CORRELATION_LOWER_LIMIT && recommendnum < PRE_RECOMMENT_NUM) {
                        recommendList.add(answerList.get(j));
                        recommendnum++;
                    }
                }
            }

            List<Answer> lastNewAnswer = answerDAO.list(PRE_RECOMMENT_NUM - recommendnum);
            for (Answer answer : lastNewAnswer) {
                recommendList.add(answer);
            }
            recommentAnswerMap.put(userId, recommendList);
        }

        //对新注册的用户由于历史浏览为空，所以推荐只能统一用最新的数据，存储在key为0的list中
        List<Answer> lastNewAnswer = answerDAO.list(PRE_RECOMMENT_NUM);
        recommentAnswerMap.put(NEW_USER_RECOMMEND_KEY, lastNewAnswer);

        //DEBUG：查看生成的推荐表
        for (Map.Entry entry : recommentAnswerMap.entrySet()) {
            String tmp_str2 = entry.getKey().toString() + " : " ;
            List<Answer> list = (List)entry.getValue();

            for (Answer answer : list) {
                tmp_str2 += answer.getId() + ",";
            }
//            System.out.println(tmp_str2);
        }
    }

    @Override
    public boolean approveAnswer(Integer answerId, User user) {
        if(approvalAnswerDAO.searchByUserIdandAnswerId(user.getId(),answerId) == -1){
            Answer answer=answerDAO.get(answerId);
            Approvalanswer approvalanswer=new Approvalanswer();
            approvalanswer.setDate(new Date());
            approvalanswer.setUserByUserId(user);
            approvalanswer.setAnswerByAnswerId(answer);

            approvalAnswerDAO.add(approvalanswer);
            answer.setApprovalCount(answer.getApprovalCount()+1);
            answerDAO.update(answer);
            return true;
        }
        return false;
    }

    @Override
    public boolean cancelApprove(Integer answerId, User user) {
        Integer x = approvalAnswerDAO.searchByUserIdandAnswerId(user.getId(),answerId);
        if (x == -1) {
            return false;
        }
        approvalAnswerDAO.delete(approvalAnswerDAO.get(x));
        Answer answer=answerDAO.get(answerId);
        answer.setApprovalCount(answer.getApprovalCount()-1);
        answerDAO.update(answer);
        return true;
    }

    @Override
    public boolean oppositionAnswer(Integer answerId, User user) {
        if(oppositionAnswerDAO.searchByUserIdandAnswerId(user.getId(),answerId) == -1){
            Answer answer=answerDAO.get(answerId);
            Oppositionanswer oppositionanswer=new Oppositionanswer();
            oppositionanswer.setDate(new Date());
            oppositionanswer.setUserByUserId(user);
            oppositionanswer.setAnswerByAnswerId(answer);

            oppositionAnswerDAO.add(oppositionanswer);
            return true;
        }
        return false;
    }

    @Override
    public boolean cancelOppose(Integer answerId, User user) {
        Integer x = oppositionAnswerDAO.searchByUserIdandAnswerId(user.getId(),answerId);
        if (x == -1) {
            return false;
        }
        oppositionAnswerDAO.delete(oppositionAnswerDAO.get(x));
        return true;
    }
}