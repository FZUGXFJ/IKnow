package org.gxfj.iknow.service;

import org.gxfj.iknow.dao.*;
import org.gxfj.iknow.pojo.*;
import org.gxfj.iknow.util.HtmlUtil;
import org.gxfj.iknow.util.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


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
    public Map<String, Object> collectionRecord(User user, Integer start) {
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
        Map<String,Object> response=new HashMap<>(20);
        response.put("records",records);
        response.put("Num",cproblems.size());
        return response;
    }

    @Override
    public Map<String, Object> browsingRecord(User user, Integer start) {
        List<Browsinghistory> bHistorys=browsingHistoryDAO.getBrowsingHistoryByUserId(user.getId(),start);
        List<Map<String,Object>> records=new ArrayList<>();
        List<Map<String,Object>> answerRecords=new ArrayList<>();
        Map<String,Object> record;
        Map<String,Object> answerRecord;
        for (Browsinghistory browsinghistory:bHistorys){
            record=new HashMap<>(3);
            Question question=browsinghistory.getQuestionByQuestionId();

            record.put("questionId",question.getId());
            record.put("questionTitile",question.getTitle());

            List<Browsinghistory> browsinghistory1s=browsingHistoryDAO.getBrowsingHistoryByUserIdAndquestionId(user.getId(),
                    question.getId());
            for (Browsinghistory browsinghistory1:browsinghistory1s){
                Answer answer=browsinghistory1.getAnswerByAnswerId();
                answerRecord=new HashMap<>(3);
                answerRecord.put("answerId",answer.getId());
                answerRecord.put("answererName",answer.getUserByUserId().getName());
                answerRecord.put("answerContent",answer.getContent());

                answerRecords.add(answerRecord);
            }
            record.put("answerRecords",answerRecords);
            records.add(record);
        }
        Map<String,Object> response=new HashMap<>(20);
        response.put("records",records);
        response.put("Num",bHistorys.size());
        return response;
    }

    @Override
    public List<Map<String,Object>> listPostQuestionRecord(User user, Integer start){
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
        return records;
    }

    @Override
    public List<Map<String,Object>> listPostAnswerRecord(User user, Integer start){
        List<Answer> answers  = answerDAO.listPartByUserId(user.getId(),start,20);
        List<Map<String, Object>> records = new ArrayList<>();

        for(Answer answer:answers){
            Map<String, Object> record = new HashMap<>(MAP_NUM);
            record.put("id",answer.getId());
            record.put("questionId",answer.getQuestionByQuestionId().getId());
            record.put("questionTitle",answer.getQuestionByQuestionId().getTitle());
            record.put("answerContent",HtmlUtil.Html2Text(HtmlUtil.changeImgTag(answer.getContent())));
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
        return records;
    }
}