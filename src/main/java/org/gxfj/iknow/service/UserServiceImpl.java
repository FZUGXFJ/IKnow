package org.gxfj.iknow.service;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.dao.*;
import org.gxfj.iknow.pojo.*;
import org.gxfj.iknow.util.ConstantUtil;
import org.gxfj.iknow.util.ExpUtil;
import org.gxfj.iknow.util.MailUtil;
import org.gxfj.iknow.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static int MAP_NUM = 20;

    @Override
    public Map<String,Object> logon(String username, String password, String email) {
        Map<String,Object> resultMap = new HashMap<>(16);
        User user = new User();
        if (userDAO.hasUsername(username)) {
            resultMap.put("value",1);
            return resultMap;
        }
        if (userDAO.hasUserEmail(email)) {
            resultMap.put("value",2);
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
        userDAO.add(user);
        resultMap.put("value",0);
        resultMap.put("result","注册成功");
        resultMap.put("user",user);
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
            result.put("resultCode",ConstantUtil.SUCCESS);
            result.put("email",user.getEmail());
            result.put("password",user.getPasswd());
            ActionContext.getContext().getSession().put("user",user);
        }
        else {
            result.put("resultCode",ConstantUtil.WRONG_PASSWORD);
        }
        return result;
    }

    @Override
    public Map<String,Object> loginByNoPassword(String email, String sessionEmail, String verifyCode,
                                                String sessionVerifyCode) {
        Map<String,Object> result = new HashMap<>(16);
        if (!email.equals(sessionEmail)) {
            result.put("value",1);
        } else if (!verifyCode.equals(sessionVerifyCode)) {
            result.put("value",2);
        } else {
            User user = userDAO.getUserByEmail(email);
            if (user == null) {
                result.put("value",3);
            } else {
                result.put("value",0);
                result.put("user",user);
            }
        }
        return result;
    }

    @Override
    public String getSimpleUserInf(User user) {
        Map<String,Object> resultMap = new HashMap<>();
        if(user ==null) {
            resultMap.put("resultCode",1);
        }
        else {
            Map<String,Object> userMap = new HashMap<>();
            userMap.put("username",user.getName());
            userMap.put("gender",user.getGender());
            userMap.put("introduction",user.getIntroduction());
            userMap.put("head",user.getHead());
            resultMap.put("userInf",userMap);
            resultMap.put("resultCode",0);
        }
        return JSON.toJSONString(resultMap);
    }

    @Override
    public Map<String, Object> getAllUserInf(User user) {
        Map<String, Object> result = new HashMap<>(MAP_NUM);
        if(user == null){
            result.put("resultCode" , 1 );
        }
        else{
            result.put("resultCode" , 0);
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
            result.put("information" , userInf);
        }
        return result;
    }

    @Override
    public String editUserInf(String head, String username, String gender, String introduction,User userInf) {
        if (!username.equals(userInf.getName()) && userDAO.hasUsername(username)) {
            return "{\"resultCode\":1}";
        }
        userInf.setHead(head);
        userInf.setGender(gender);
        userInf.setName(username);
        userInf.setIntroduction(introduction);
        userDAO.update(userInf);
        return "{\"resultCode\":0}";
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
}
