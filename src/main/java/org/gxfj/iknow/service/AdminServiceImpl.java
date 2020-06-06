package org.gxfj.iknow.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.dao.*;
import org.gxfj.iknow.pojo.*;
import org.gxfj.iknow.util.*;

import static org.gxfj.iknow.pojo.Questionstate.QUESTION_STATE_UN_SOLVE_ID;
import static org.gxfj.iknow.util.ServiceConstantUtil.*;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigInteger;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;



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
    @Autowired
    ReportTypeDAO reportTypeDAO;
    @Autowired
    MessageUtil messageUtil;
    @Autowired
    SchoolDAO schoolDAO;
    @Autowired
    CollegeDAO collegeDAO;
    @Autowired
    MajorDAO majorDAO;
    @Autowired
    UserIdentityDAO userIdentityDAO;

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
    public Map<String, Object> getReportedQuestion(Integer questionId) {
        Map<String, Object> questionMap = new HashMap<>(MIN_HASH_MAP_NUM);
        Map<String,Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        Question question = questionDAO.get(questionId);
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

        result.put("answerReported",map);
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
        if (question != null && question.getIsDelete() == Question.QUESTION_UN_DELETED) {
            Collection<Answer> answerCollection = question.getAnswersById();

            for (Answer answer : answerCollection) {
                answerDel(answer.getId());
            }
            if (question.getAnswerByAdoptId() != null) {
                User user = question.getAnswerByAdoptId().getUserByUserId();
                user.setBadgeNum(user.getBadgeNum() - 1);
            }

            questionDAO.delete(question);
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);

            messageUtil.newMessage(1,question.getUserByUserId(),
                    "<p>你发布的问题<b>\""+ question.getTitle() +"\"<b>被举报了，快去看看吧</P>");

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
            if (answer != null && answer.getIsDelete() == Answer.ANSWER_UN_DELETED) {
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
                    //减少被采纳用户的徽章数
                    User user = question.getUserByUserId();
                    user.setBadgeNum(user.getBadgeNum() - 1);
                }
                //删除回答下的所有评论及回复
                for (Comment comment : answer.getCommentsById()) {
                    comment.getId();
                }
                //删除回答
                answerDAO.delete(answer);

                messageUtil.newMessage(1,answer.getUserByUserId(),
                        "<p>你发布的回答被举报了，快去看看吧</p>");

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
            if (comment != null && comment.getIsDelete() == Comment.COMMENT_UN_DELETED) {
                for (Reply reply : comment.getRepliesById()) {
                    replyDAO.delete(reply);
                }
                commentDAO.delete(comment);
                messageUtil.newMessage(1,comment.getUserByUserId(),
                        "<p>你发布的评论被举报了，快去看看吧</p>");
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
            if (reply != null && reply.getIsDelete() == Reply.REPLY_UN_DELETE) {
                replyDAO.delete(reply);
            }
            messageUtil.newMessage(1,reply.getUserByUserId(),
                    "<p>你发布的回复被举报了，快去看看吧</p>");
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        } else {
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.RESULT_CODE_PARAMS_ERROR);
        }

        return result;
    }

    @Override
    public Map<String, Object> deleteAllQueReport(){
        Map<String, Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        reportDAO.deleteAllQueReport();
        result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        return result;
    }

    @Override
    public Map<String, Object> deleteAllAnsReport(){
        Map<String, Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        reportDAO.deleteAllAnsReport();
        result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        return result;
    }

    @Override
    public Map<String, Object> deleteAllComReport(){
        Map<String, Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        reportDAO.deleteAllComReport();
        result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        return result;
    }

    @Override
    public Map<String, Object> deleteAllRepReport(){
        Map<String, Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        reportDAO.deleteAllRepReport();
        result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        return result;
    }

    @Override
    public Map<String, Object> saveSchool(String schoolName) {
        Map<String, Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        School school1 = schoolDAO.getSchoolByName(schoolName);
        if(school1 != null){
            result.put("schoolID", school1.getId());
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SCHOOL_IS_EXIST);
        }
        else{
            School school = new School();
            school.setName(schoolName);
            schoolDAO.add(school);
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
            result.put("schoolID", schoolDAO.getSchoolByName(schoolName).getId());
        }
        return result;
    }

    @Override
    public Map<String, Object> saveStudents(String studentsInfo, Integer schoolId) {
        Map<String, Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        List<Map<String, Object>> collegeInfoList = new ArrayList<>();
        List<Map<String, Object>> majorInfoList = new ArrayList<>();
        if(!JsonUtil.isJsonArray(studentsInfo)){
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.IS_NOT_JSON_ARRAY);
        }else {
            //将字符串转化为json数组
            JSONArray studentsInfoArray = JSONArray.parseArray(studentsInfo);

            if(studentsInfoArray.size()>0){
                for(int i = 0;i < studentsInfoArray.size(); i ++){
                    Map<String, Object> studentInfoMap = studentsInfoArray.getJSONObject(i);
                    String collegeName = (String) studentInfoMap.get("学院");
                    String majorName = (String) studentInfoMap.get("专业");
                    Integer studentNum = (Integer) studentInfoMap.get("学号");
                    String realname = (String) studentInfoMap.get("姓名");

                    if(collegeDAO.getCollegeByName(collegeName) == null){
                        College college = new College();
                        college.setSchoolBySchoolId(schoolDAO.get(schoolId));
                        college.setName(collegeName);
                        collegeDAO.add(college);
                    }

                    if(majorDAO.getMajorByName(majorName) == null){
                        Major major = new Major();
                        major.setCollegeByCollegeId(collegeDAO.getCollegeByName(collegeName));
                        major.setName(majorName);
                        majorDAO.add(major);
                    }

                    if(userIdentityDAO.getStudentIdentity(schoolId, studentNum) == null){
                        Useridentity useridentity = new Useridentity();
                        useridentity.setSchoolBySchoolId(schoolDAO.get(schoolId));
                        useridentity.setCollegeByCollegeId(collegeDAO.getCollegeByName(collegeName));
                        useridentity.setMajorByMajorId(majorDAO.getMajorByName(majorName));
                        useridentity.setStudentNum(studentNum);
                        useridentity.setName(realname);
                        useridentity.setType("学生");
                        userIdentityDAO.add(useridentity);
                    }
                }
            }
            School school = schoolDAO.get(schoolId);
            List<College> colleges = (List<College>)school.getCollegesById();

            for(College college:colleges){
                Map<String, Object> collegeInfo = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
                collegeInfo.put("name" , college.getName());
                collegeInfo.put("id", college.getId());
                collegeInfoList.add(collegeInfo);
                List<Major> majors = (List<Major>)college.getMajorsById();
                for(Major major:majors){
                    Map<String, Object> majorInfo = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
                    majorInfo.put("name", major.getName());
                    majorInfo.put("id", major.getId());
                    majorInfoList.add(majorInfo);
                }
            }

            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
            result.put("collegeInfo", collegeInfoList);
            result.put("majorInfo", majorInfoList);
        }

        return result;
    }

    @Override
    public Map<String, Object> saveStudent(String studentInfo){
        Map<String, Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        Map<String, Object> studentInfoMap = (Map<String, Object>)JSON.parse(studentInfo);

        String studentNO = (String)studentInfoMap.get("studentNO");
        String name = (String)studentInfoMap.get("name");
        Integer school = (Integer) studentInfoMap.get("school");
        String college = (String)studentInfoMap.get("college");
        String major = (String)studentInfoMap.get("major");

        Useridentity useridentity = new Useridentity();
        useridentity.setName(name);
        useridentity.setSchoolBySchoolId(schoolDAO.get(school));
        useridentity.setCollegeByCollegeId(collegeDAO.getCollegeByName(college));
        useridentity.setMajorByMajorId(majorDAO.getMajorByName(major));
        useridentity.setStudentNum(Integer.parseInt(studentNO));
        useridentity.setType("学生");
        userIdentityDAO.add(useridentity);
        result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        return result;
    }

    @Override
    public Map<String, Object> saveTeachers(String teachersInfo, Integer schoolID){
        Map<String, Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        Useridentity useridentity;
        if(!JsonUtil.isJsonArray(teachersInfo)){
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.IS_NOT_JSON_ARRAY);
        }else {
            List<Map<String, Object>> teachersInfoMap = (List<Map<String,Object>>) JSONArray.parse(teachersInfo);

            for(Map<String, Object> teacherInfoMap:teachersInfoMap){
                String teacherNO = (String)teacherInfoMap.get("工号");
                String name = (String)teacherInfoMap.get("姓名");
                String college = (String)teacherInfoMap.get("学院");

                useridentity = new Useridentity();
                useridentity.setName(name);
                useridentity.setSchoolBySchoolId(schoolDAO.get(schoolID));
                useridentity.setCollegeByCollegeId(collegeDAO.getCollegeByName(college));
                useridentity.setJobNum(Integer.parseInt(teacherNO));
                useridentity.setType("教师");
                userIdentityDAO.add(useridentity);
            }
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        }
        return result;
    }

    @Override
    public Map<String, Object> saveTeacher(String teacherNO, String name, Integer schoolId, String college){
        Map<String, Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);

        Useridentity useridentity = new Useridentity();
        useridentity.setName(name);
        useridentity.setSchoolBySchoolId(schoolDAO.get(schoolId));
        useridentity.setCollegeByCollegeId(collegeDAO.getCollegeByName(college));
        useridentity.setJobNum(Integer.parseInt(teacherNO));
        useridentity.setType("教师");
        userIdentityDAO.add(useridentity);
        result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        return result;
    }

    @Override
    public Map<String, Object> getSchools(){
        Map<String, Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        List<Map<String, Object>> schoolMapList = new ArrayList<>();
        Map<String, Object> schoolMap;
        List<School> schools = schoolDAO.listAllSchool();
        for(School school:schools){
            int collegeNum = 0;
            int majorNum = 0;
            //获取专业数
            List<College> colleges = (List<College>)school.getCollegesById();
            collegeNum = colleges.size();
            for(College college:colleges){
                majorNum += college.getMajorsById().size();
            }
            schoolMap = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
            schoolMap.put("schoolID",school.getId());
            schoolMap.put("schoolName",school.getName());
            schoolMap.put("collegeNums",collegeNum);
            schoolMap.put("majorNums",majorNum);
            schoolMapList.add(schoolMap);
        }
        result.put("schoolInfos", schoolMapList);
        result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        return result;
    }

    @Override
    public Map<String, Object> getColleges(Integer schoolId){
        Map<String, Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        School school = schoolDAO.get(schoolId);
        List<College> colleges = (List<College>)school.getCollegesById();
        List<Map<String, Object>> collegeMapList = new ArrayList<>();
        Map<String, Object> collegeMap;
        for(College college:colleges){
            collegeMap = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
            collegeMap.put("schoolID",school.getId());
            collegeMap.put("schoolName",school.getName());
            collegeMap.put("collegeID",college.getId());
            collegeMap.put("collegeName",college.getName());
            collegeMap.put("majorNums",college.getMajorsById().size());
            collegeMap.put("teacherNums",userIdentityDAO.getCollegeTeaNum(college.getId()));
            collegeMap.put("studentNums",userIdentityDAO.getCollegeStuNum(college.getId()));
            collegeMapList.add(collegeMap);
        }
        result.put("collegeInfos", collegeMapList);
        result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        return result;
    }

    @Override
    public Map<String, Object> getMajor(Integer collegeId){
        Map<String, Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        College college = collegeDAO.get(collegeId);
        List<Major> majors = (List<Major>)college.getMajorsById();
        List<Map<String, Object>> majorMapList = new ArrayList<>();
        School school = college.getSchoolBySchoolId();
        Map<String, Object> majorMap;
        for(Major major:majors){
            majorMap = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
            majorMap.put("schoolID",school.getId());
            majorMap.put("schoolName",school.getName());
            majorMap.put("collegeID",college.getId());
            majorMap.put("collegeName",college.getName());
            majorMap.put("majorID",major.getId());
            majorMap.put("majorName",major.getName());
            majorMap.put("teacherNums",userIdentityDAO.getMajorTeaNum(major.getId()));
            majorMap.put("studentNums",userIdentityDAO.getMajorStuNum(major.getId()));
            majorMapList.add(majorMap);
        }
        result.put("majorInfos", majorMapList);
        result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        return result;
    }

    @Override
    public Map<String, Object> getStudent(Integer majorId){
        Map<String, Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        List<Useridentity> useridentities = userIdentityDAO.listStuIdentity(majorId);
        List<Map<String, Object>> studentMapList = new ArrayList<>();
        Map<String, Object> studentMap;
        for(Useridentity useridentity:useridentities){
            studentMap = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
            studentMap.put("studentID",useridentity.getStudentNum());
            studentMap.put("studentName",useridentity.getName());
            studentMap.put("collegeID",useridentity.getCollegeByCollegeId().getId());
            studentMap.put("collegeName",useridentity.getCollegeByCollegeId().getName());
            studentMap.put("majorID",useridentity.getMajorByMajorId().getId());
            studentMap.put("majorName",useridentity.getMajorByMajorId().getName());
            studentMapList.add(studentMap);
        }
        result.put("studentInfos", studentMapList);
        result.put(ConstantUtil.JSON_RETURN_CODE_NAME, ConstantUtil.SUCCESS);
        return result;
    }
}
