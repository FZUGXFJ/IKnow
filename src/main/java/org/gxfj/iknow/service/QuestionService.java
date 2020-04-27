package org.gxfj.iknow.service;



import org.gxfj.iknow.pojo.User;

import java.util.Map;

/**
 * @author qmbx
 */
public interface QuestionService {
    public void postQuestion(User user, String title, String context, Integer categoryType, Integer subjectType
        , Integer majorType, Byte isAnonymous);

    public Map<String, Object>getQuestionType();

    public Map<String, Object>getQuestion(Integer questionId);
}
