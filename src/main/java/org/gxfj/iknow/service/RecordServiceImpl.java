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


    @Override
    public Map<String, Object> collectionRecord(User user, Integer start) {
        List<Collectionproblem> cproblems=collectionProblemDAO.getCollectionQuestionByUserId(user.getId(),start);
        List<Map<String,Object>> records=new ArrayList<>();
        Map<String,Object> record;
        for (Collectionproblem collectionProblem:cproblems){
            record=new HashMap<>(7);
            Question question=collectionProblem.getQuestionByQuestionId();

            record.put("questionId",question.getId());
            record.put("questionTitile",question.getTitle());
            record.put("answerNum",answerDAO.getAnswersbyQid(question.getId()).size());
            record.put("broswingNum",browsingHistoryDAO.getBrowsingCount(question.getId()));
            record.put("collectionNum",collectionProblemDAO.getCollectionCount(question.getId()));
            record.put("isSolved",question.getQuestionstateByStateId().getId()-1);
            record.put("time",Time.getNowDate(collectionProblem.getDate()));
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

            List<Answer> answers=answerDAO.getAnswersbyQid(question.getId());
            for (Answer answer:answers){
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
}
