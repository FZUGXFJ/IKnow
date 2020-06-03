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
        if(type == 1){
            MessageUtil.newMessage(1,questionDAO.get(targetId).getUserByUserId(),
                    "<p>你发布的问题被举报了，快去看看吧</P><a href='../../mobile/question/question.html?" +
                            "questionId=" + targetId + "'>[问题链接]</a>");
        }
        else if (type == 2){
            MessageUtil.newMessage(1,answerDAO.get(targetId).getUserByUserId(),
                    "<p>你发布的回答被举报了，快去看看吧</P><a href='../../mobile/answer/answer.html?questionId="
                            + answerDAO.get(targetId).getQuestionByQuestionId().getId() + "&answerId=" + targetId +
                            "'>[回答链接]</a>");
        }
        else if (type == 3){
            MessageUtil.newMessage(1,commentDAO.get(targetId).getUserByUserId(),
                    "<p>你发表的评论被举报了，快去看看吧</P><a href='../../mobile/comment/comment.html?answerId="
                    + commentDAO.get(targetId).getAnswerByAnswerId().getId() + "'>[评论链接]</a>");
        }
        else {
            MessageUtil.newMessage(1,replyDAO.get(targetId).getUserByUserId(),
                    "<p>你发表的回复被举报了，快去看看吧</P><a href='../../mobile/comment/comment.html?answerId="
                            + replyDAO.get(targetId).getCommentByCommentId().getAnswerByAnswerId().getId() +
                            "'>[评论链接]</a>");
        }
        return true;
    }
}
