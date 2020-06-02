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
    public String getQuestiontitle(Integer questionId);
    /**
     * 获取回答id
     * @param questionId 问题Id
     * @param content 回答内容
     * @param isAnonmyous 是否匿名
     * @param user 用户
     * @return json数据
     */
    public Map<String,Object> postAnswer(Integer questionId,String content,Byte isAnonmyous,User user);

    /**
     * 查看回答
     * @param questionId 问题id
     * @param answerId 回答的id
     * @param user 当前用户
     * @return json数据（答案的信息，关联的用户，评论，问题的信息）
     */
    public Map<String,Object> getRecommendAnswer(Integer questionId , Integer answerId , User user);

    /**
     * 执行用户采纳问题操作。如果用户是题主，采纳成功，否则采纳失败
     * @param user 执行操作的用户
     * @param answerId 要采纳的回答的id
     * @return 成功为true,失败为false
     */
    public Boolean adoptAnswer(User user, Integer answerId);

    /**
     * 执行回答取消匿名
     * @param user 执行操作的用户
     * @param answerId 要采纳的回答的id
     * @return 成功为true,失败为false
     */
    public Boolean cancelAnonymous(User user,Integer answerId);

    /**
     * 获得count条问题信息
     * @param count 推荐数
     * @return 问题表
     */
    public Map<String,Object> getRecommendAnswer(Integer userId, Integer count);


    void createRecommendAnswer();

    /**
     * 点赞回答
     * @param answerId 回答id
     * @param user 点赞用户
     * @return 是否点赞
     */
    public boolean approveAnswer(Integer answerId,User user);

    /**
     * 取消点赞
     * @param answerId 回答id
     * @param user 用户
     * @return 是否取消成功
     */
    public boolean cancelApprove(Integer answerId,User user);

    /**
     * 反对回答
     * @param answerId 回答id
     * @param user 用户
     * @return 是否成功
     */
    public boolean oppositionAnswer(Integer answerId,User user);

    /**
     * 取消反对
     * @param answerId 回答id
     * @param user 用户
     * @return 是否取消成功
     */
    public boolean cancelOppose(Integer answerId,User user);

    /**
     * 获得count条问题信息
     * @param count 推荐数
     * @param userId 用户id
     * @param start 起始下标
     * @return 问题表
     */
    public Map<String,Object> moreRecommendAnswer(Integer userId, Integer count,Integer start);

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
    public boolean isAnswerer(Integer answerId,User user);

    /**
     * 删除回答
     * @param user 用户
     * @param answerId 回答id
     * @return 是否成功
     */
    public boolean deleteAnswer(User user , Integer answerId);

    /**
     * 获取原回答的内容
     * @param answerId 回答id
     * @return 包含原回答内容的map
     */
    public String getAnswerContent(Integer answerId);

    /**
     * 更新回答内容
     * @param answerId 回答id
     * @param content 新的回答内容
     * @return 是否成功
     */
    public boolean updateAnswerContent(Integer answerId, String content);
}
