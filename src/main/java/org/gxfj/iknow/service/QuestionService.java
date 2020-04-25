package org.gxfj.iknow.service;


import org.gxfj.iknow.pojo.Question;
import org.gxfj.iknow.pojo.User;

/**
 * @author qmbx
 */
public interface QuestionService {
    public Question postQuestion(User user, String title, String context, Integer categoryType, Integer subjectType
        , Integer majorType, Byte isAnonymous);

}
