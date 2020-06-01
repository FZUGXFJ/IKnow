package org.gxfj.iknow.service;

import org.gxfj.iknow.dao.*;
import org.gxfj.iknow.pojo.*;
import org.gxfj.iknow.util.ConstantUtil;
import org.gxfj.iknow.util.SecurityUtil;
import static org.gxfj.iknow.util.ServiceConstantUtil.*;

import org.gxfj.iknow.util.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



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

    @Override
    public Admin login(Admin adminInf) {
        Admin admin = adminDAO.getAdminByCount(adminInf.getAccount());
        if(admin == null){
            return null;
        }
        else{
            if (adminInf.getPasswd().length() == MD5_PASSWORD_LENGTH) {
                if (adminInf.getPasswd().equals(admin.getPasswd())) {
                    return admin;
                } else {
                    return null;
                }
            } else {
                if (SecurityUtil.md5Compare(adminInf.getPasswd(),admin.getPasswd())) {
                    return admin;
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public Map<String, Object> getData(String dateNow, Integer typeSum) {
        Map<String,Object> record;
        List<Map<String,Object>> records = new ArrayList<>();
        Map<String,Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        if(typeSum == ConstantUtil.DAILY_ACTIVE_USER_STATICS_TYPE_CODE){
            List<Integer> date = adminDAO.getQuestionSum(dateNow);
            for (Integer i : date){
                record = new HashMap<>();
                record.put("sum",i);
                records.add(record);
            }
            result.put("questionSums",records);
        }
        else if (typeSum == ConstantUtil.MONTHLY_ACTIVATE_USER_STATICS_TYPE_CODE) {
            List<Integer> date = adminDAO.getUserSum(dateNow);
            for (Integer i : date){
                record = new HashMap<>();
                record.put("sum",i);
                records.add(record);
            }
            result.put("userSums",records);
        }
        return result;
    }
    @Autowired
    BrowsingHistoryDAO browsingHistoryDAO;

    @Override
    public Map<String, Object> getActiveData(String dateNow, Integer typeSum, Integer length) {
        Map<String,Object> record;
        List<Map<String,Object>> records = new ArrayList<>();
        Map<String,Object> result = new HashMap<>(MIN_HASH_MAP_NUM);

        //初始化日活跃列表
        for (int i = 0; i < length; i++) {
            record = new HashMap<>(MIN_HASH_MAP_NUM);
            record.put("sum", 0);
            records.add(record);
        }

        if(typeSum == 0){
            List<List> date = browsingHistoryDAO.getUserDailyActives(dateNow, length);

            for (List i : date){
                record = records.get(length - 1 - (Integer)i.get(1));
                record.put("sum",i.get(0));
            }
            result.put("userDayActives",records);
        }
        else {
            List<Object[]> date = browsingHistoryDAO.getUserMonthlyActives(dateNow, length);

            for (Object[] i : date){
                record = records.get(length - 1 - ((BigInteger)i[1]).intValue());
                record.put("sum",i[0]);
            }
            result.put("userMonActives",records);
        }
        return result;
    }
    @Autowired
    CategoriesTypeDAO categoriesTypeDAO;

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
        return result;
    }

    @Override
    public Map<String,Object> getReportByType(Integer reportType){
        Map<String,Object> reportMap;
        List<Map<String,Object>> reportListMap = new ArrayList<>();
        Map<String,Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        List<Report> reports = reportDAO.listReportByType(reportType);
        User targetUser;
        for (Report report : reports){
            reportMap = new HashMap<>(MIN_HASH_MAP_NUM);
            reportMap.put("id",report.getId());
            reportMap.put("userID",report.getUserByUserId().getId());
            switch(reportType){
                case 1 :
                    targetUser = questionDAO.get(report.getTargetId()).getUserByUserId();
                    break;
                case 2 :
                    targetUser = answerDAO.get(report.getTargetId()).getUserByUserId();
                    break;
                case 3 :
                    targetUser = commentDAO.get(report.getTargetId()).getUserByUserId();
                    break;
                case 4 :
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
            reportListMap.add(reportMap);
        }
        result.put("reportInfoList",reportListMap);
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
        return result;
    }

    @Override
    public Map<String,Object> getUserInfo(Integer userId){
        Map<String,Object> userMap = new HashMap<>(MIN_HASH_MAP_NUM);
        List<Map<String,Object>> userListMap = new ArrayList<>();
        Map<String,Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        User user = userDAO.get(userId);
        userMap.put("id",user.getId());
        userMap.put("email",user.getEmail());
        userMap.put("name",user.getName());
        userMap.put("isAttest",user.getIsAttest()==1?"已认证":"未认证");
        userMap.put("date",Time.getTime1(user.getDate()));
        userMap.put("badgeNum",user.getBadgeNum());
        userMap.put("state",user.getUserstateByStateId().getState());
        userMap.put("identity",user.getUseridentityByIdentityId().getType());
        userListMap.add(userMap);
        result.put("userInfos",userListMap);
        return result;
    }
}
