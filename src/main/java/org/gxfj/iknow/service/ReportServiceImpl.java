package org.gxfj.iknow.service;

import org.apache.struts2.ServletActionContext;
import org.gxfj.iknow.dao.*;
import org.gxfj.iknow.pojo.Report;
import org.gxfj.iknow.pojo.Reportreason;
import org.gxfj.iknow.pojo.Reporttype;
import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        Map<String,Object> reason;
        List<Map<String,Object>> reasons = new ArrayList<>();
        List<Reportreason> reportreasons = reportReasonDAO.list();
        for (Reportreason reportreason : reportreasons){
            reason = new HashMap<>(2);
            reason.put("id",reportreason.getId());
            reason.put("reason",reportreason.getContent());
            reasons.add(reason);
        }
        Map<String,Object> result = new HashMap<>(2);
        result.put("reasons",reasons);
        return result;
    }

    @Override
    public boolean doReport(Integer type, Integer reason, String description, Integer targetId, User user) {
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

        return true;
    }
}
