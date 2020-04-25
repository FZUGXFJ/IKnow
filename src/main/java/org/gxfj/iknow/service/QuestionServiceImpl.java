package org.gxfj.iknow.service;

import org.gxfj.iknow.dao.QuestionDAO;
import org.gxfj.iknow.dao.QuestionTypeDAO;
import org.gxfj.iknow.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * @author qmbx
 */
@Service("questionService")
public class QuestionServiceImpl implements QuestionService{
    @Autowired
    QuestionDAO questionDAO;
    @Autowired
    QuestionTypeDAO questionTypeDAO;

    @Override
    public Question postQuestion(User user, String title, String context, Integer categoryType, Integer subjectType
            , Integer majorType, Byte isAnonymous) {
        Question question = new Question();
        Questiontype questiontype = questionTypeDAO.get(categoryType,subjectType,majorType);
        Questionstate questionstate = new Questionstate();
        Questionscenario questionscenario = new Questionscenario();
        Answer answer = new Answer();

        //TODO 修正魔法值的存在
        questionstate.setId(1);
        questionscenario.setId(1);
        answer.setId(0);

        question.setUserByUserId(user);
        question.setTitle(title);
        question.setContent(context);
        question.setQuestiontypeByTypeId(questiontype);
        question.setQuestionstateByStateId(questionstate);
        question.setQuestionscenarioByScenarioId(questionscenario);
        question.setDate(new Date());
        question.setIsDelete((byte)0);
        question.setIsAnonymous(isAnonymous);


        questionDAO.add(question);

        return null;
    }
}
