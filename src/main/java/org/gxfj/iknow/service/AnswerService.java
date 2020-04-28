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
     * @param qId 问题Id
     * @return Map
     */
    public String getQuestiontitle(Integer qId);
    /**
     * 获取回答id
     * @param qId 问题Id
     * @param content 回答内容
     * @param isAnonmyous 是否匿名
     * @param user 用户
     * @return json数据
     */
    public  Map<String,Object> postAnswer(Integer qId,String content,Byte isAnonmyous,User user);
}
