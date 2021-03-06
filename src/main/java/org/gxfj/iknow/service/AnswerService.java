package org.gxfj.iknow.service;

import org.gxfj.iknow.pojo.Answer;
import org.gxfj.iknow.pojo.Question;
import org.gxfj.iknow.pojo.User;

import java.util.Map;

/**
 * @author erniumo
 */
public interface AnswerService {
    /**
     * 获得问题标题
     * @param questionId 问题Id
     * @return Map
     */
    Map<String, Object> getQuestiontitle(User user, Integer questionId);
    /**
     * 获取回答id
     * @param questionId 问题Id
     * @param content 回答内容
     * @param isAnonmyous 是否匿名
     * @return json数据
     */
    Map<String,Object> postAnswer(User user, Integer questionId,String content,Byte isAnonmyous);

    /**
     * 查看回答
     * @param questionId 问题id
     * @param answerId 回答的id
     * @return json数据（答案的信息，关联的用户，评论，问题的信息）
     */
    Map<String,Object> viewAnswer(User user, Integer questionId , Integer answerId );

    /**
     * 执行用户采纳问题操作。如果用户是题主，采纳成功，否则采纳失败
     * @param answerId 要采纳的回答的id
     * @return 成功为true,失败为false
     */
    Map<String, Object> adoptAnswerService(User user, Integer answerId);

    /**
     * 执行回答取消匿名
     * @param answerId 要采纳的回答的id
     * @return 成功为true,失败为false
     */
    Map<String, Object> cancelAnonymous(User user, Integer answerId);

    /**
     * 获得count条问题信息
     * @param count 推荐数
     * @return 问题表
     */
    Map<String,Object> getRecommendAnswerForUser(User user, Integer count);


    void createRecommendAnswer();

    /**
     * 点赞回答
     * @param answerId 回答id
     * @return 是否点赞
     */
    Map<String, Object> approveAnswer(User user, Integer answerId);

    /**
     * 取消点赞
     * @param answerId 回答id
     * @return 是否取消成功
     */
    Map<String, Object> cancelApprove(User user, Integer answerId);

    /**
     * 反对回答
     * @param answerId 回答id
     * @return 是否成功
     */
    Map<String, Object> oppositionAnswer(User user, Integer answerId);

    /**
     * 取消反对
     * @param answerId 回答id
     * @return 是否取消成功
     */
    Map<String, Object> cancelOppose(User user, Integer answerId);

    /**
     * 获得count条问题信息
     * @param count 推荐数
     * @param start 起始下标
     * @return 问题表
     */
    Map<String,Object> moreRecommendAnswer(User user, Integer count,Integer start);

    /**
     * 插入浏览记录
     * @param user 用户
     * @param question 问题
     * @param answer 回答
     */
    void insertBrowsing(User user, Question question, Answer answer);

    /**
     * 判断用户是否是答主
     * @param answerId 回答id
     * @param user 用户
     * @return 是否成功
     */
    boolean isAnswerer(Integer answerId,User user);

    /**
     * 删除回答
     * @param answerId 回答id
     * @return 是否成功
     */
    Map<String, Object> deleteAnswer(User user, Integer answerId);

    /**
     * 获取原回答的内容
     * @param answerId 回答id
     * @return 包含原回答内容的map
     */
    Map<String, Object> getAnswerContent(User user, Integer answerId);

    /**
     * 更新回答内容
     * @param answerId 回答id
     * @param content 新的回答内容
     * @return 是否成功
     */
    Map<String, Object> updateAnswerContent(User user, Integer answerId, String content);
}
