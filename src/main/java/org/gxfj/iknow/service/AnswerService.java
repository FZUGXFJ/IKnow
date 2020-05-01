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
    public Map<String,Object> postAnswer(Integer qId,String content,Byte isAnonmyous,User user);

    /**
     * 查看回答
     * @param qId 问题id
     * @param id 回答的id
     * @param user 当前用户
     * @return json数据（答案的信息，关联的用户，评论，问题的信息）
     */
    public Map<String,Object> getAnswer(Integer qId , Integer id , User user);

    /**
     * 执行用户采纳问题操作。如果用户是题主，采纳成功，否则采纳失败
     * @param user 执行操作的用户
     * @param answerId 要采纳的回答的id
     * @return 成功为true,失败为false
     */
    public Boolean adoptAnswer(User user, Integer answerId);
}
