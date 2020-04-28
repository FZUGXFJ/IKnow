package org.gxfj.iknow.service;

import org.gxfj.iknow.dao.AnswerDAO;
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
    final static private int MAP_NUM = 20;
    @Override
    public Map<String,Object> getQuestiontitle(Question q, User u) {
        Map<String, Object> result= new HashMap<>(MAP_NUM);
        if(u==null) {
            result.put("resultCode",1);
            return result;
        }
        result.put("resultCode",0);
        result.put("title",q.getTitle());
        return result;
    }

    @Override
    public Map<String,Object> postAnswer(Question q, String content, Byte isAnonmyous,User user) {
        Answer answer=new Answer();
        answer.setIsAnonymous(isAnonmyous);
        answer.setDate(new Date());
        answer.setApprovalCount(0);
        answer.setUserByUserId(user);
        answer.setQuestionByQuestionId(q);
        answer.setIsDelete((byte)0);
        answer.setIsRoof((byte)0);
        answer.setContent(content);

        Integer id=answerDAO.add(answer);
        Map<String, Object> result= new HashMap<>(MAP_NUM);
        result.put("resultCode",0);
        result.put("answerID",id);
        return result;
    }
}
