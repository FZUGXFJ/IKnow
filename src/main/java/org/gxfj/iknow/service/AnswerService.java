package org.gxfj.iknow.service;

import org.gxfj.iknow.pojo.Question;
import org.gxfj.iknow.pojo.User;

import java.util.Map;

/**
 * @author erniumo
 */
public interface AnswerService {
    /**
     * 获得问题标题
     * @param q 问题
     * @param u 用户
     * @return Map
     */
    public Map<String,Object> getQuestiontitle(Question q, User u);
    /**
     * 获取回答id
     * @param q 问题
     * @param content 回答内容
     * @param isAnonmyous 是否匿名
     * @param user 用户
     * @return json数据
     */
    public  Map<String,Object> postAnswer(Question q,String content,Byte isAnonmyous,User user);
}
