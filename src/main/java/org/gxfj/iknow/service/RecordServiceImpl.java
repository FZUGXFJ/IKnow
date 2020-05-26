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
            record.put("time",collectionProblem.getDate());

            records.add(record);
        }
        Map<String,Object> response=new HashMap<>(20);
        response.put("records",records);
        response.put("Num",cproblems.size());
        return response;
    }
}
