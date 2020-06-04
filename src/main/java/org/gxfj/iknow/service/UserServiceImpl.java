package org.gxfj.iknow.service;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.dao.*;
import org.gxfj.iknow.pojo.*;
import org.gxfj.iknow.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.*;

import static org.gxfj.iknow.util.ConstantUtil.*;

/**
 * @author Administrator hhj
 */
@Service("userService")
@Transactional(readOnly = false)
public class UserServiceImpl<result> implements UserService{

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private LevelDAO levelDAO;
    @Autowired
    private CollectionProblemDAO collectionProblemDAO;
    @Autowired
    private BrowsingHistoryDAO browsingHistoryDAO;
    @Autowired
    private AchievementRecordDAO achievementRecordDAO;
    @Autowired
    private QuestionDAO questionDAO;
    @Autowired
    private AnswerDAO answerDAO;
    @Autowired
    private UserIdentityDAO userIdentityDAO;
    @Autowired
    private MailUtil mailUtil;
    @Autowired
    private ExpUtil expUtil;
    @Autowired
    MessageDAO messageDAO;
    @Autowired
    UserStateUtil userStateUtil;
    @Autowired
    ReplyDAO replyDAO;
    @Autowired
    CommentDAO commentDAO;

    private static int MAP_NUM = 20;

    @Override
    public Map<String,Object> logon(String username, String password, String email, String verifyCode) {
        Map<String,Object> resultMap = new HashMap<>(MIN_HASH_MAP_NUM);

        Map<String,Object> session = ActionContext.getContext().getSession();

        String sessionVerifyCode = (String)session.get(VERIFY_CODE);
        String sessionEmail = (String)session.get(EMAIL);
        session.put(VERIFY_CODE, null);
        session.put(EMAIL, null);

        if (!verifyCode.equals(sessionVerifyCode)) {
            resultMap.put("response", 3);
            return resultMap;
        } else if (!email.equals(sessionEmail)) {
            resultMap.put("response", 4);
            return resultMap;
        }


        User user = new User();
        if (userDAO.hasUsername(username)) {
            resultMap.put("response",1);
            return resultMap;
        }
        if (userDAO.hasUserEmail(email)) {
            resultMap.put("response",2);
            return resultMap;
        }
        user.setName(username);
        //使用md5加密
        user.setPasswd(SecurityUtil.md5(password));
        user.setEmail(email);
        /*默认信息*/
        //注册时间为当前系统时间
        user.setDate( new Timestamp(System.currentTimeMillis()));
        //初始徽章为0
        user.setBadgeNum(0);
        //初始经验为1
        user.setExp(1);
        //初始性别为保密
        user.setGender("保密");
        //初始用户无身份认证信息
        user.setIsAttest((byte) 0);
        Userstate userstate = new Userstate();
        userstate.setId(1);
        //初始用户状态为正常
        user.setUserstateByStateId(userstate);
        //初始头像
        user.setHead("1.jpg");
        user.setReportedTimes(0);
        userDAO.add(user);

        resultMap.put("response", SUCCESS);
        session.put("user", user);

        return resultMap;
    }

    @Override
    public Map<String,String> sendVerifyCode(String email) {
        Map<String,String> map = new HashMap<>(MAP_NUM);
        String subject = "IKnow验证邮件";
        String verifyCode = SecurityUtil.generatorVerifyCode(6);
        map.put("verifyCode",verifyCode);
        String content = "您的验证码是<h1>" +
                verifyCode +
                "</h1>请在15分钟内完成验证";
        String result;
        try {
            mailUtil.sendMail(email,subject,content);
        } catch (Exception e) {
            result = "{\"head\":\"发送失败\",\"body\":\"服务器异常\"}";
            map.put("result",result);
            e.printStackTrace();
            return map;
        }
        result = "{\"head\":\"发送成功\",\"body\":\"请进入邮箱查看验证码\"}";
        map.put("result",result);
        return map;
    }

    @Override
    public Map<String, Object> loginByPassword(String email, String password) {
        Map<String, Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        User user = userDAO.getUserByEmail(email);
        if (user != null && (password.length() == 32 && password.equals(user.getPasswd()) ||
                SecurityUtil.md5Compare(password, user.getPasswd()))) {

            //判断用户是否处于封禁
            if (user.getUserstateByStateId().getId().equals(userStateUtil.getBanState().getId())) {
                Date now = new Date();
                if (user.getLastClosureTime().after(now)) {
                    result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.JSON_RESULT_CODE_BAN);
                    return result;
                } else {
                    user.setUserstateByStateId(userStateUtil.getStateByName(Userstate.NORMAL));
                }
            }
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
            result.put("email", user.getEmail());
            result.put("password", user.getPasswd());
            ActionContext.getContext().getSession().put("user", user);
        }
        else {
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.WRONG_PASSWORD);
        }
        return result;
    }

    @Override
    public Map<String,Object> loginByNoPassword(String email, String verifyCode) {
        Map<String,Object> session = ActionContext.getContext().getSession();
        String sessionEmail = (String)session.get(ConstantUtil.EMAIL);
        String sessionVerifyCode = (String)session.get(ConstantUtil.VERIFY_CODE);

        session.put(ConstantUtil.VERIFY_CODE, null);

        Map<String, Object> response = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        Map<String,Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        if (!email.equals(sessionEmail)) {
            response.put("response",1);
        } else if (!verifyCode.equals(sessionVerifyCode)) {
            response.put("response",2);
        } else {
            User user = userDAO.getUserByEmail(email);
            if (user == null) {
                response.put("response", 3);
            } else {
                //判断用户是否处于封禁
                if (user.getUserstateByStateId().getId().equals(userStateUtil.getBanState().getId())) {
                    Date now = new Date();
                    if (user.getLastClosureTime().after(now)) {
                        result.put("response", ConstantUtil.JSON_RESULT_CODE_BAN);
                        return result;
                    } else {
                        user.setUserstateByStateId(userStateUtil.getStateByName(Userstate.NORMAL));
                    }
                }
                response.put("response", 0);
                ActionContext.getContext().getSession().put("user", user);
                response.put("email", user.getEmail());
                response.put("password", user.getPasswd());
            }
        }
        return response;
    }

    @Override
    public String getSimpleUserInf(User user) {
        Map<String,Object> resultMap = new HashMap<>();
        if(user ==null) {
            resultMap.put(JSON_RETURN_CODE_NAME,1);
        }
        else {
            Map<String,Object> userMap = new HashMap<>();
            userMap.put("username",user.getName());
            userMap.put("gender",user.getGender());
            userMap.put("introduction",user.getIntroduction());
            userMap.put("head",user.getHead());
            resultMap.put("userInf",userMap);
            resultMap.put(JSON_RETURN_CODE_NAME,0);
        }
        return JSON.toJSONString(resultMap);
    }

    @Override
    public Map<String, Object> getAllUserInf(User user) {
        Map<String, Object> result = new HashMap<>(MAP_NUM);
        if(user == null){
            result.put(JSON_RETURN_CODE_NAME , 1 );
        }
        else{
            result.put(JSON_RETURN_CODE_NAME , 0);
            Integer userId = user.getId();
            Map<String, Object> userInf = new HashMap<>(MAP_NUM);
            userInf.put("name" , user.getName());
            userInf.put("head" , "<img src='../head/" + user.getHead() +
                    "' width='100%' height='100%' style='border-radius: 100%' alt=''>");
            userInf.put("badgeNum" , user.getBadgeNum());
            userInf.put("level" , expUtil.getLevelLabel(user.getExp()));
            userInf.put("postQueNum" , questionDAO.getUserQuestionCount(userId));
            userInf.put("postAnsNum" , answerDAO.getUserAnswersCount(userId));
            userInf.put("collectNum" , collectionProblemDAO.getUserCollectCount(userId));
            userInf.put("browseNum" , browsingHistoryDAO.getUserBrowseCount(userId));
            userInf.put("achievementList" , listUserAchievements(userId));
            userInf.put("identity" , getUserIdentity(userId));

            List<Message> messageList = messageDAO.listUnReadMessageByUserId(userId);
            if(messageList.size()>0){
                userInf.put("hasNotReadMsg",1);
            }
            else {
                userInf.put("hasNotReadMsg",0);
            }
            result.put("information" , userInf);
        }
        return result;
    }

    @Override
    public Map<String, Object> editUserInf(String head, String username, String gender, String introduction) {
        Map<String,Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        Map<String,Object> session = ActionContext.getContext().getSession();
        User user = (User)session.get(ConstantUtil.SESSION_USER);
        //TODO: 统一接口势不可挡
        if (user == null) {
            //TODO: 未登录和用户名被占用是一样的返回码
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.UN_LOGIN);
            return result;
        }
        if (!username.equals(user.getName()) && userDAO.hasUsername(username)) {
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, 1);
            return result;
        }
        if (!TextVerifyUtil.verifyCompliance(username) || !TextVerifyUtil.verifyCompliance(introduction)) {
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.JSON_RESULT_CODE_VERIFY_TEXT_FAIL);
            return result;
        }

        user.setHead(head);
        user.setGender(gender);
        user.setName(username);
        user.setIntroduction(introduction);
        userDAO.update(user);
        result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        return result;
    }

    @Override
    public Boolean resetPassword(User user, String newPassword) {
        if (user == null) {
            return false;
        }
        user.setPasswd(SecurityUtil.md5(newPassword));
        userDAO.update(user);

        return true;
    }

    @Override
    public boolean reBindEmail(User user , String newEmail) {
        if(user == null){
            return false;
        }
        user.setEmail(newEmail);
        userDAO.update(user);
        return true;
    }

    @Override
    public boolean existEmail(String email) {
        if(userDAO.getUserByEmail(email)==null){
            return false;
        }
        return true;
    }

    /**
     * 获取用户获得的成就
     * @return 哈希表的列表形式的用户成就
     */
    private List<Map> listUserAchievements(Integer userId){
        List<Achievement> achievements = achievementRecordDAO.listAchievementsByUserId(userId , 0);
        List<Map> achievementsMap = new ArrayList<>();
        Map<String, Object> achievementMap = new HashMap<>(MAP_NUM);
        for(Achievement achievement :achievements){
            achievementMap.put("achievementName" , achievement.getName());
            achievementMap.put("achievementId" , achievement.getId());
            achievementsMap.add(achievementMap);
        }
        return  achievementsMap;
    }

    /**
     * 获得用户的身份（暂时不允许多身份）
     * @param userId 用户id
     * @return 用户的真实姓名，学校，学院;没有身份返回空
     */
    private Map<String, Object> getUserIdentity(Integer userId){
        Map<String, Object> userIdentityMap = new HashMap<>(MAP_NUM);
        List<Useridentity> userIdentities = userIdentityDAO.listUserIdentitiesByUserId(userId);
        if(userIdentities == null || userIdentities.size() ==0){
            return null;
        }
        else{
           Useridentity useridentity = userIdentities.get(0);
           userIdentityMap.put("realName" , useridentity.getName());
           userIdentityMap.put("school" , useridentity.getSchoolBySchoolId().getName());
           userIdentityMap.put("college" , useridentity.getCollegeByCollegeId().getName());
           return userIdentityMap;
        }
    }

    @Override
    public Map<String, String> sendVerifyCoderesetemail(String email) {
        Map<String,String> map = new HashMap<>(MAP_NUM);
        String subject = "IKnow验证邮件";
        String verifyCode = SecurityUtil.generatorVerifyCode(6);
        map.put("verifyCode",verifyCode);
        String content = "您的验证码是<h1>" +
                verifyCode +
                "</h1>请在15分钟内完成验证";
        String result;
        try {
            mailUtil.sendMail(email,subject,content);
        } catch (Exception e) {
            result = "{\"head\":\"发送失败\",\"body\":\"服务器异常\"}";
            map.put("result",result);
            e.printStackTrace();
            return map;
        }
        result = "{\"head\":\"发送成功\",\"body\":\"请进入邮箱查看验证码\"}";
        map.put("result",result);
        return map;
    }

    @Override
    public Map<String, Object> getHomedata(Integer userId) {
        Map<String, Object> result = new HashMap<>(MAP_NUM);
        User user = userDAO.get(userId);
        if(user == null){
            result.put(JSON_RETURN_CODE_NAME , 1 );
        }
        else{
            result.put(JSON_RETURN_CODE_NAME , 0);
            Map<String, Object> userInf = new HashMap<>(MAP_NUM);
            userInf.put("name" , user.getName());
            userInf.put("head" , "<img src='../head/" + user.getHead() +
                    "' width='100%' height='100%' style='border-radius: 100%' alt=''>");
            userInf.put("introduction",user.getIntroduction());
            userInf.put("gender",user.getGender());
            int sum = 0;
            List<Answer> answerList = answerDAO.listPartByUserIdNodelete(userId);
            List<Comment> commentList = commentDAO.listByuserId(userId);
            List<Reply> replyList = replyDAO.listByuserId(userId);
            for (Answer answer:answerList){
                sum += answer.getApprovalCount();
            }
            for (Comment comment:commentList){
                sum += comment.getCount();
            }
            for (Reply reply:replyList){
                sum += reply.getCount();
            }
            userInf.put("gainApproveNum",sum);
            userInf.put("badgeNum" , user.getBadgeNum());

            List<Question> questionList = questionDAO.listPartByUserId(userId,0,10);
            List<Map<String,Object>> questions = new ArrayList<>();
            Map<String,Object> question;
            for (Question question1:questionList){
                question = new HashMap<>(5);
                question.put("id",question1.getId());
                question.put("title",question1.getTitle());
                question.put("browsingNum",browsingHistoryDAO.getBrowsingCount(question1.getId()));
                question.put("answerNum",answerDAO.getAnswersbyQid(question1.getId()).size());
                question.put("collectionNum",collectionProblemDAO.getCollectionCount(question1.getId()));

                questions.add(question);
            }
            userInf.put("questionDynamic",questions);

            List<Answer> answerList1 = answerDAO.listPartByUserId(userId,0,10);
            List<Map<String,Object>> answers = new ArrayList<>();
            Map<String,Object> answer;
            for (Answer answer1:answerList1){
                answer = new HashMap<>(6);
                answer.put("id",answer1.getId());
                answer.put("content",answer1.getContentText());
                answer.put("questionTitle",answer1.getQuestionByQuestionId().getTitle());
                answer.put("time",Time.getTime(answer1.getDate()));
                answer.put("approvNum",answer1.getApprovalCount());
                answer.put("commentNum",commentDAO.getCount(answer1.getId()));

                answers.add(answer);
            }

            userInf.put("answerDynamic",answers);
            result.put("information" , userInf);
        }
        return result;
    }
}
