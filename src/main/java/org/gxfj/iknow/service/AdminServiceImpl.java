package org.gxfj.iknow.service;

import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.dao.*;
import org.gxfj.iknow.pojo.*;
import org.gxfj.iknow.util.ConstantUtil;
import org.gxfj.iknow.util.SecurityUtil;

import static org.gxfj.iknow.pojo.Questionstate.QUESTION_STATE_UN_SOLVE_ID;
import static org.gxfj.iknow.util.ServiceConstantUtil.*;

import org.gxfj.iknow.util.Time;
import org.gxfj.iknow.util.UserStateUtil;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigInteger;
import java.util.*;


/**
 * @author hhj
 */
@Service("adminService")
@Transactional(readOnly = false)
public class AdminServiceImpl implements AdminService{

    @Autowired
    private AdminDAO adminDAO;
    @Autowired
    private ReportDAO reportDAO;
    @Autowired
    private QuestionDAO questionDAO;
    @Autowired
    private AnswerDAO answerDAO;
    @Autowired
    private CommentDAO commentDAO;
    @Autowired
    private ReplyDAO replyDAO;
    @Autowired
    private ReportReasonDAO reportReasonDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    CategoriesTypeDAO categoriesTypeDAO;

    @Override
    public Map<String, Object> login(Integer accountNum, String password) {
        Map<String, Object> resultMap = new HashMap<>(ConstantUtil.HASH_MAP_NUM);

        Admin admin = adminDAO.getAdminByCount(accountNum);
        if(admin == null){
            return null;
        }
        else{
            if (password.length() == MD5_PASSWORD_LENGTH) {
                if (password.equals(admin.getPasswd())) {
                    ActionContext.getContext().getSession().put(ConstantUtil.LOGIN_ADMIN_SESSION_NAME,admin);
                    resultMap.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);

                } else {
                    resultMap.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.UN_LOGIN);
                }
            } else {
                if (SecurityUtil.md5Compare(password,admin.getPasswd())) {
                    ActionContext.getContext().getSession().put(ConstantUtil.LOGIN_ADMIN_SESSION_NAME,admin);
                    resultMap.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
                } else {
                    resultMap.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.UN_LOGIN);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> isLogin() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map<String, Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        if (session.get(ConstantUtil.LOGIN_ADMIN_SESSION_NAME) == ConstantUtil.NO_ADMIN) {
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.UN_LOGIN);
        } else {
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        }
        return result;
    }

    @Override
    public Map<String, Object> getSumData(String dateNow, Integer typeSum) {
        Map<String,Object> record;
        List<Map<String,Object>> records = new ArrayList<>();
        Map<String,Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        if(typeSum == ConstantUtil.DAILY_QUESTION_SUM_STATICS_TYPE_CODE){
            List<Integer> date = adminDAO.getQuestionSum(dateNow);
            for (Integer i : date){
                record = new HashMap<>();
                record.put("sum",i);
                records.add(record);
            }
            result.put("questionSums",records);
        }
        else if (typeSum == ConstantUtil.DAILY_USER_SUM_STATICS_TYPE_CODE) {
            List<Integer> date = adminDAO.getUserSum(dateNow);
            for (Integer i : date){
                record = new HashMap<>();
                record.put("sum",i);
                records.add(record);
            }
            result.put("userSums",records);
        }

        result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);

        return result;
    }
    @Autowired
    BrowsingHistoryDAO browsingHistoryDAO;

    @Override
    public Map<String, Object> getDailyActiveUserData(String dateNow, Integer length) {
        Map<String,Object> record;
        List<Map<String,Object>> records = new ArrayList<>();
        Map<String,Object> result = new HashMap<>(MIN_HASH_MAP_NUM);

        //初始化日活跃列表
        for (int i = 0; i < length; i++) {
            record = new HashMap<>(MIN_HASH_MAP_NUM);
            record.put("sum", 0);
            records.add(record);
        }

        List<List> date = browsingHistoryDAO.getUserDailyActives(dateNow, length);

        for (List i : date){
            record = records.get(length - 1 - (Integer)i.get(1));
            record.put("sum",i.get(0));
        }
        result.put("userDayActives",records);

        result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        return result;
    }

    @Override
    public Map<String, Object> getMonthlyActiveUserData(String dateNow, Integer length) {
        Map<String,Object> record;
        List<Map<String,Object>> records = new ArrayList<>();
        Map<String,Object> result = new HashMap<>(MIN_HASH_MAP_NUM);

        //初始化日活跃列表
        for (int i = 0; i < length; i++) {
            record = new HashMap<>(MIN_HASH_MAP_NUM);
            record.put("sum", 0);
            records.add(record);
        }

        List<Object[]> date = browsingHistoryDAO.getUserMonthlyActives(dateNow, length);

        for (Object[] i : date){
            record = records.get(length - 1 - ((BigInteger)i[1]).intValue());
            record.put("sum",i[0]);
        }
        result.put("userMonActives",records);
        result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);

        return result;
    }




    @Override
    public Map<String, Object> getQuestionTypeSumData() {
        Map<String,Object> record;
        List<Map<String,Object>> records = new ArrayList<>();
        Map<String,Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        List<Categoriestype> categoriestypeList = categoriesTypeDAO.list();
        for (Categoriestype categoriestype : categoriestypeList){
            record = new HashMap<>(MIN_HASH_MAP_NUM);
            int sum = 0;
            for (Questiontype questiontype : categoriestype.getQuestiontypesById()) {
                sum += questiontype.getQuestionsById().size();
            }
            if (sum > 0) {
                record.put("value", sum);
                record.put("name", categoriestype.getName());
                records.add(record);
            }
        }
        result.put("questionTypeSums",records);
        result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        return result;
    }

    @Override
    public Map<String,Object> getReportByType(Integer reportType){
        Map<String,Object> reportMap;
        List<Map<String,Object>> reportListMap = new ArrayList<>();
        Map<String,Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        List<Report> reports = reportDAO.listReportByType(reportType+1);
        User targetUser;
        for (Report report : reports){
            reportMap = new HashMap<>(MIN_HASH_MAP_NUM);
            reportMap.put("id",report.getId());
            reportMap.put("userID",report.getUserByUserId().getId());
            switch(reportType){
                case 0 :
                    targetUser = questionDAO.get(report.getTargetId()).getUserByUserId();
                    break;
                case 1 :
                    targetUser = answerDAO.get(report.getTargetId()).getUserByUserId();
                    break;
                case 2 :
                    targetUser = commentDAO.get(report.getTargetId()).getUserByUserId();
                    break;
                case 3 :
                    targetUser = replyDAO.get(report.getTargetId()).getUserByUserId();
                    break;
                default :
                    targetUser = null;
            }
            reportMap.put("targetID",targetUser.getId());
            reportMap.put("type",report.getReporttypeByTypeId().getType());
            reportMap.put("description",report.getDescription());
            reportMap.put("typeID",report.getTargetId());
            reportMap.put("date", Time.getTime1(report.getDate()));
            reportMap.put("reason",report.getReportreasonByReasonId().getContent());
            reportMap.put("isDeal",0);
            reportListMap.add(reportMap);
        }
        result.put("reportInfoList",reportListMap);
        result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        return result;
    }

    @Override
    public Map<String,Object> getReportReason(){
        Map<String,Object> reportReasonMap;
        List<Map<String,Object>> reportReasonListMap = new ArrayList<>();
        Map<String,Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        List<Reportreason> reportreasons = reportReasonDAO.list();
        for(Reportreason reportreason:reportreasons){
            reportReasonMap = new HashMap<>(MIN_HASH_MAP_NUM);
            reportReasonMap.put("id",reportreason.getId());
            reportReasonMap.put("content",reportreason.getContent());
            reportReasonListMap.add(reportReasonMap);
        }
        result.put("reportReason",reportReasonListMap);
        result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        return result;
    }

    @Override
    public Map<String,Object> getUserInfo(Integer userId){
        Map<String,Object> userMap = new HashMap<>(MIN_HASH_MAP_NUM);
        Map<String,Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        User user = userDAO.get(userId);
        Useridentity useridentity = user.getUseridentityByIdentityId();
        userMap.put("id",user.getId());
        userMap.put("email",user.getEmail());
        userMap.put("name",user.getName());
        userMap.put("isAttest",user.getIsAttest()==1?"已认证":"未认证");
        userMap.put("date",Time.getTime1(user.getDate()));
        userMap.put("badgeNum",user.getBadgeNum());
        userMap.put("state",user.getUserstateByStateId().getState());
        userMap.put("identity",useridentity==null?"普通用户":useridentity.getType());
        userMap.put("reportedTimes",user.getReportedTimes());
        result.put("userInfos",userMap);
        result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        return result;
    }

    @Override
    public Map<String, Object> getReportedQuestion(Integer questioinId) {
        Map<String, Object> questionMap = new HashMap<>(MIN_HASH_MAP_NUM);
        Map<String,Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        Question question = questionDAO.get(questioinId);
        questionMap.put("id", question.getId());
        questionMap.put("userID", question.getUserByUserId().getId());
        questionMap.put("title", question.getTitle());
        questionMap.put("content", question.getContentHtml());
        questionMap.put("date", Time.getTime1(question.getDate()));
        if(question.getIsDelete().equals(1)){
            questionMap.put("isDelete", "是");
        }
        else {
            questionMap.put("isDelete", "否");
        }
        //获得问题类别，存储在map中
        String categoriesType = question.getQuestiontypeByTypeId()
                .getCategoriestypeByCategoryId().getName();
        String majorType = question.getQuestiontypeByTypeId().getMajortypeByMajorId().getName();
        String subjectType = question.getQuestiontypeByTypeId().getSubjecttypeBySubjectId().getName();
        questionMap.put("type", categoriesType+"-"+majorType+"-"+subjectType);
        result.put("questionReported", questionMap);
        result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        return result;
    }

    @Override
    public Map<String, Object> getAnswerReported(Integer typeId, Integer type) {
        Map<String,Object> map;
        List<Map<String,Object>> answerListMap = new ArrayList<>();
        Map<String,Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        switch(type){
            case 0 :
                map = getAnswerInfoMap(typeId);
                break;
            case 1 :
                map = getCommentInfoMap(typeId);
                break;
            case 2 :
                map = getReplyInfoMap(typeId);
                break;
            default :
                map = null;
        }
        answerListMap.add(map);
        result.put("answerReported",answerListMap);
        result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        return result;
    }

    private Map<String,Object> getCommentInfoMap(Integer commentId){
        Comment comment = commentDAO.get(commentId);
        Map<String,Object> commentMap = new HashMap<>(MIN_HASH_MAP_NUM);
        commentMap.put("id",comment.getId());
        commentMap.put("userID",comment.getUserByUserId().getId());
        commentMap.put("content",comment.getContent());
        commentMap.put("date",Time.getTime1(comment.getDate()));
        commentMap.put("isDelete",comment.getIsDelete() == 1 ? "是" : "否");
        return commentMap;
    }
    private Map<String,Object> getAnswerInfoMap(Integer answerId){
        Answer answer = answerDAO.get(answerId);
        Map<String,Object> answerMap = new HashMap<>(MIN_HASH_MAP_NUM);
        answerMap.put("id",answer.getId());
        answerMap.put("userID",answer.getUserByUserId().getId());
        answerMap.put("content",answer.getContentHtml());
        answerMap.put("date",Time.getTime1(answer.getDate()));
        answerMap.put("isDelete",answer.getIsDelete() == 1 ? "是" : "否");
        return answerMap;
    }
    private Map<String,Object> getReplyInfoMap(Integer replyId){
        Reply reply = replyDAO.get(replyId);
        Map<String,Object> replyMap = new HashMap<>(MIN_HASH_MAP_NUM);
        replyMap.put("id",reply.getId());
        replyMap.put("userID",reply.getUserByUserId().getId());
        replyMap.put("content",reply.getContent());
        replyMap.put("date",Time.getTime1(reply.getDate()));
        replyMap.put("isDelete",reply.getIsDelete() == 1 ? "是" : "否");
        return replyMap;
    }

    @Override
    public Map<String, Object> deleteReport(Integer reportId){
        Report report = reportDAO.get(reportId);
        reportDAO.delete(report);
        Map<String, Object> result = new HashMap<>();
        result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        return result;
    }

    @Autowired
    UserStateUtil userStateUtill;

    @Override
    public Map<String, Object> ban(Integer userID, Integer days) {
        Map<String, Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);;

        if (userID != null || days != null) {
            User user = userDAO.get(userID);
            if (user != null) {
                Date lastDate = user.getLastClosureTime();

                Date date = new Date();
                //如果在惩罚期间了，那在原有的惩罚基础上延长时间
                if (lastDate != null && lastDate.after(date)) {
                    date = lastDate;
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_YEAR, days);

                date = calendar.getTime();
                user.setLastClosureTime(date);
                user.setUserstateByStateId(userStateUtill.getBanState());
                user.setReportedTimes(user.getReportedTimes() + 1);
                userDAO.update(user);
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
            }
        } else {
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.RESULT_CODE_PARAMS_ERROR);
        }

        return result;
    }

    @Override
    public Map<String, Object> estoppel(Integer userID, Integer days) {
        Map<String, Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);;

        if (userID != null || days != null) {
            User user = userDAO.get(userID);
            if (user != null) {
                Date lastDate = user.getLastClosureTime();

                Date date = new Date();
                //如果在惩罚期间了，那在原有的惩罚基础上延长时间
                if (lastDate != null && lastDate.after(date)) {
                    date = lastDate;
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_YEAR, days);

                Userstate userstate = user.getUserstateByStateId();
                if (userstate != null ) {
                    Integer userStateId = userstate.getId();
                    if (!userStateId.equals(userStateUtill.getEstoppelState().getId()) &&
                            !userStateId.equals(userStateUtill.getBanState().getId())) {
                        user.setUserstateByStateId(userStateUtill.getEstoppelState());
                    }
                }

                date = calendar.getTime();
                user.setLastClosureTime(date);
                user.setReportedTimes(user.getReportedTimes() + 1);
                userDAO.update(user);
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
            }
        } else {
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.RESULT_CODE_PARAMS_ERROR);
        }

        return result;
    }

    @Override
    public Map<String, Object> questionDel(Integer questionID) {
        Map<String, Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);;

        Question question = questionDAO.get(questionID);
        if (question != null && question.getIsDelete() == 1) {
            Collection<Answer> answerCollection = question.getAnswersById();

            for (Answer answer : answerCollection) {
                ((AdminService)AopContext.currentProxy()).answerDel(answer.getId());
            }
            questionDAO.delete(question);
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        } else {
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.RESULT_CODE_PARAMS_ERROR);
        }
        return result;
    }

    @Override
    public Map<String, Object> answerDel(Integer answerID) {
        Map<String, Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        if (answerID != null) {
            Answer answer = answerDAO.get(answerID);
            if (answer != null) {
                //如果被删除的回答所属的问题没有被删除，那么就需要判断回答是否被采纳，如果被采纳就需要删除采纳
                if (answer.getQuestionByQuestionId().getIsDelete() == Question.QUESTION_UN_DELETED
                        && answer.getQuestionByQuestionId().getAnswerByAdoptId() != null
                        && answer.getQuestionByQuestionId().getAnswerByAdoptId().getId().equals(answerID)) {
                    Question question = answer.getQuestionByQuestionId();
                    Answer answerNull = null;
                    question.setAnswerByAdoptId(answerNull);
                    //构造未解决的问题状态
                    Questionstate questionstate = new Questionstate();
                    questionstate.setId(Questionstate.QUESTION_STATE_UN_SOLVE_ID);
                    question.setQuestionstateByStateId(questionstate);
                    questionDAO.update(question);
                }
                //删除回答下的所有评论及回复
                for (Comment comment : answer.getCommentsById()) {
                    ((AdminService)AopContext.currentProxy()).commentDel(comment.getId());
                }
                //删除回答
                answerDAO.delete(answer);
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
            }
        } else {
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.RESULT_CODE_PARAMS_ERROR);
        }
        return result;
    }


    @Override
    public Map<String, Object> commentDel(Integer commentID) {
        Map<String, Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);

        if (commentID != null) {
            Comment comment = commentDAO.get(commentID);
            if (comment != null) {
                for (Reply reply : comment.getRepliesById()) {
                    replyDAO.delete(reply);
                }
                commentDAO.delete(comment);
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
            }
        } else {
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.RESULT_CODE_PARAMS_ERROR);
        }
        return result;
    }

    @Override
    public Map<String, Object> replyDel(Integer replyID) {
        Map<String, Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        if (replyID != null) {
            Reply reply =replyDAO.get(replyID);
            if (reply != null) {
                replyDAO.delete(reply);
            }
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        } else {
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.RESULT_CODE_PARAMS_ERROR);
        }

        return result;
    }
}
