package org.gxfj.iknow.service;

import org.gxfj.iknow.dao.AnswerDAO;
import org.gxfj.iknow.dao.QuestionDAO;
import org.gxfj.iknow.pojo.Answer;
import org.gxfj.iknow.pojo.Question;
import org.gxfj.iknow.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author erniumo
 */
@Service("answerService")
public class AnswerServiceImpl implements AnswerService{
    @Autowired
    private AnswerDAO answerDAO;
    @Autowired
    private QuestionDAO questionDAO;
    final static private int MAP_NUM = 20;
    @Override
    public String getQuestiontitle(Integer qId) {
        Question question = questionDAO.get(qId);
        return question.getTitle();
    }

    @Override
    public Map<String,Object> postAnswer(Integer qId, String content, Byte isAnonymous,User user) {
        Answer answer=new Answer();
        answer.setIsAnonymous(isAnonymous);
        answer.setDate(new Date());
        answer.setApprovalCount(0);
        answer.setUserByUserId(user);
        Question q = questionDAO.get(qId);
        answer.setQuestionByQuestionId(q);
        answer.setIsDelete((byte)0);
        answer.setIsRoof((byte)0);
        answer.setContent(content);

        answerDAO.add(answer);
        Map<String, Object> result= new HashMap<>(MAP_NUM);
        result.put("answerID",answer.getId());
        return result;
    }
}
