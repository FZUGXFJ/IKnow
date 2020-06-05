package org.gxfj.iknow.service;

import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;
import org.gxfj.iknow.dao.*;
import org.gxfj.iknow.pojo.Report;
import org.gxfj.iknow.pojo.Reportreason;
import org.gxfj.iknow.pojo.Reporttype;
import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.util.ConstantUtil;
import org.gxfj.iknow.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.gxfj.iknow.util.ConstantUtil.*;

/**
 * @author erniumo
 */
@Service("reportService")
public class ReportServiceImpl implements ReportService{

    @Autowired
    UserDAO userDAO;
    @Autowired
    QuestionDAO questionDAO;
    @Autowired
    AnswerDAO answerDAO;
    @Autowired
    CommentDAO commentDAO;
    @Autowired
    ReplyDAO replyDAO;
    @Autowired
    ReportReasonDAO reportReasonDAO;
    @Autowired
    ReportTypeDAO reportTypeDAO;
    @Autowired
    ReportDAO reportDAO;
    @Override
    public Map<String, Object> reportReasonmap() {
        User user = (User) ActionContext.getContext().getSession().get("user");
        Map<String , Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        if(user == null){
            result.put(JSON_RETURN_CODE_NAME, UN_LOGIN);
        }
        else {
            Map<String,Object> reason;
            List<Map<String,Object>> reasons = new ArrayList<>();
            List<Reportreason> reportreasons = reportReasonDAO.list();
            for (Reportreason reportreason : reportreasons){
                reason = new HashMap<>(2);
                reason.put("id",reportreason.getId());
                reason.put("reason",reportreason.getContent());
                reasons.add(reason);
            }
            result.put("reasons",reasons);
            result.put(JSON_RETURN_CODE_NAME,SUCCESS);
        }
        return result;
    }

    @Override
    public Map<String, Object> doReport(Integer type, Integer reason, String description, Integer targetId) {
        User user = (User) ActionContext.getContext().getSession().get("user");
        Map<String , Object> result = new HashMap<>(MIN_HASH_MAP_NUM);
        if(user == null){
            result.put(JSON_RETURN_CODE_NAME, UN_LOGIN);
        }
        else {
            Reportreason reportreason = reportReasonDAO.get(reason);
            Reporttype reporttype = reportTypeDAO.get(type);
            Report report = new Report();
            report.setUserByUserId(user);
            report.setDate(new Date());
            report.setDescription(description);
            report.setReportreasonByReasonId(reportreason);
            report.setReporttypeByTypeId(reporttype);
            report.setTargetId(targetId);

            reportDAO.add(report);
            result.put(JSON_RETURN_CODE_NAME,SUCCESS);
        }
        return result;
    }
}
