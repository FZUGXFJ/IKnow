package org.gxfj.iknow.service;

import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.dao.*;
import org.gxfj.iknow.pojo.*;
import org.gxfj.iknow.util.ConstantUtil;
import org.gxfj.iknow.util.HtmlUtil;
import org.gxfj.iknow.util.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.gxfj.iknow.util.ConstantUtil.*;


/**
 * @author 爱学习的水先生
 */
@Service("recordService")
public class RecordServiceImpl implements RecordService {
    @Autowired
    CollectionProblemDAO collectionProblemDAO;
    @Autowired
    AnswerDAO answerDAO;
    @Autowired
    BrowsingHistoryDAO browsingHistoryDAO;
    @Autowired
    QuestionDAO questionDAO;

    final static private int MAP_NUM = 20;
    @Override
    public Map<String, Object> collectionRecord(Integer start) {
        User user = (User) ActionContext.getContext().getSession().get("user");
        Map<String , Object> result = new HashMap<>(HASH_MAP_NUM);
        if(user == null){
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME , UN_LOGIN);
        }
        else {
            //result=recordService.collectionRecord(user,start);
            List<Collectionproblem> cproblems=collectionProblemDAO.getCollectionQuestionByUserId(user.getId(),start);
            List<Map<String,Object>> records=new ArrayList<>();
            Map<String,Object> record;
            for (Collectionproblem collectionProblem:cproblems){
                record=new HashMap<>(7);
                Question question=collectionProblem.getQuestionByQuestionId();

                record.put("questionId",question.getId());
                record.put("questionTitle",question.getTitle());
                record.put("answerNum",answerDAO.getAnswersbyQid(question.getId()).size());
                record.put("browsingNum",browsingHistoryDAO.getBrowsingCount(question.getId()));
                record.put("collectionNum",collectionProblemDAO.getCollectionCount(question.getId()));
                record.put("isSolved",question.getQuestionstateByStateId().getId()-1);
                record.put("time",Time.getTime1(collectionProblem.getDate()));
                records.add(record);
            }
            result.put("records",records);
            result.put("Num",cproblems.size());
            Integer x=cproblems.size();
            if(x<20){
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME,NO_MORE);
            }
            else
            {
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME,SUCCESS);
            }
        }
        return result;
    }

    @Override
    public Map<String, Object> browsingRecord(Integer start) {
        User user = (User) ActionContext.getContext().getSession().get("user");
        Map<String , Object> result = new HashMap<>(HASH_MAP_NUM);
        if(user == null){
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME , UN_LOGIN);
        }
        else {
            List<Browsinghistory> bHistories=browsingHistoryDAO.getBrowsingHistoryByUserId(user.getId(),start);
            List<Map<String,Object>> records=new ArrayList<>();
            List<Map<String,Object>> answerRecords=new ArrayList<>();
            Map<String,Object> record = new HashMap<>(3);
            Map<String,Object> answerRecord;
            Integer curQueId = -1;
            for (Browsinghistory browsinghistory:bHistories) {
                answerRecord = new HashMap<>(3);
                if (!browsinghistory.getQuestionByQuestionId().getId().equals(curQueId)) {
                    record.put("answerRecords",answerRecords);
                    records.add(record);
                    record = new HashMap<>(3);
                    answerRecords = new ArrayList<>();
                    curQueId = browsinghistory.getQuestionByQuestionId().getId();
                    Question question=browsinghistory.getQuestionByQuestionId();
                    record.put("questionId",question.getId());
                    record.put("questionTitle",question.getTitle());
                }
                Answer answer = browsinghistory.getAnswerByAnswerId();
                if (answer !=null) {
                    answerRecord.put("answerId",answer.getId());
                    answerRecord.put("answerContent",HtmlUtil.delHtmlTag(HtmlUtil.changeImgTag(answer.getContentHtml())));
                    answerRecord.put("answererName",answer.getUserByUserId().getName());
                    answerRecords.add(answerRecord);
                }
            }
            if (records.size() > 0) {
                records.remove(0);
            }
            result.put("records",records);
            result.put("Num",bHistories.size());
            Integer x=bHistories.size();
            if(x<20){
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME,NO_MORE);
            }
            else {
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME,SUCCESS);
            }
        }
        return result;
    }

    @Override
    public Map<String,Object> listPostQuestionRecord(Integer start){
        User user = (User) ActionContext.getContext().getSession().get("user");
        Map<String , Object> result = new HashMap<>(HASH_MAP_NUM);
        if(user == null){
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME , UN_LOGIN_TWO);
        }
        else {
            List<Question> questions  = questionDAO.listPartByUserId(user.getId(),start,20);
            List<Map<String, Object>> records = new ArrayList<>();

            for(Question question:questions){
                Map<String, Object> record = new HashMap<>(MAP_NUM);
                record.put("id",question.getId());
                record.put("title",question.getTitle());
                record.put("answerNum",question.getAnswersById().size());
                record.put("isSolved",question.getAnswerByAdoptId() == null?1:0);
                record.put("collectNum",question.getCollectionproblemsById().size());
                record.put("browsingNum",question.getBrowsinghistoriesById().size());
                record.put("time",Time.getTime1(question.getDate()));
                records.add(record);
            }
            result.put("records",records);
            Integer x=(Integer)records.size();
            if(x<20){
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME,NO_MORE);
            }
            else
            {
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME,SUCCESS);
            }
        }
        return result;
    }

    @Override
    public Map<String,Object> listPostAnswerRecord(Integer start){
        User user = (User) ActionContext.getContext().getSession().get("user");
        Map<String , Object> result = new HashMap<>(HASH_MAP_NUM);
        if(user == null){
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME , UN_LOGIN);
        }
        else {
            //List<Map<String, Object>> records = recordService.listPostAnswerRecord(user,start);
            List<Answer> answers  = answerDAO.listPartByUserId(user.getId(),start,20);
            List<Map<String, Object>> records = new ArrayList<>();

            for(Answer answer:answers){
                Map<String, Object> record = new HashMap<>(MAP_NUM);
                record.put("id",answer.getId());
                record.put("questionId",answer.getQuestionByQuestionId().getId());
                record.put("questionTitle",answer.getQuestionByQuestionId().getTitle());
                record.put("answerContent",HtmlUtil.delHtmlTag(HtmlUtil.changeImgTag(answer.getContentHtml())));
                record.put("commentNum",answer.getCommentsById().size());
                record.put("approveNum",answer.getApprovalanswersById().size());
                int isAdopt = 0;
                if (answer.getQuestionByQuestionId().getAnswerByAdoptId() != null &&
                        answer.getQuestionByQuestionId().getAnswerByAdoptId().getId().equals(user.getId())) {
                    isAdopt = 1;
                }
                record.put("isAdopt",isAdopt);
                record.put("time",Time.getTime1(answer.getDate()));
                records.add(record);
            }
            result.put("records",records);
            Integer x=(Integer)records.size();
            if(x<20){
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME,NO_MORE);
            }
            else
            {
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME,SUCCESS);
            }
        }
        
        return result;
    }
}
