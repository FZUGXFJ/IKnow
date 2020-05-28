package org.gxfj.iknow.service;



import org.gxfj.iknow.pojo.Collectionproblem;
import org.gxfj.iknow.pojo.User;

import java.util.Date;
import java.util.Map;

/**
 * @author qmbx
 */
public interface QuestionService {
    /**
     * 根据参数构造Qustion对象并存储存储到数据库中
     * @param user 发表问题的用户
     * @param title 问题标题
     * @param context 问题详情
     * @param categoryType 问题所属的门类
     * @param subjectType 问题所属的学科
     * @param majorType 问题所属的专业
     * @param isAnonymous 提问者发布问题时，是否匿名
     * @return 问题id
     */
    public Integer postQuestion(User user, String title, String context, Integer categoryType, Integer subjectType
        , Integer majorType, Byte isAnonymous);

    /**
     * 从数据库中读取问题分类中的所有门类、学科、专业
     * @return 以Map形式嵌套存储所有分类
     */
    public Map<String, Object>getQuestionType();
    
    /**
     * 根据问题的id，获得问题及问题下的前length个回答的相关信息
     * @param user 用户
     * @param questionId 要获得的问题
     * @param length 要加载的回答个数
     * @param sort 排序方式
     * @return json格式的问题信息
     */
    public Map<String, Object>getQuestion(User user, Integer questionId, int length,int sort);

    /**
     * 根据问题的id，获得问题及问题下的前length个回答的相关信息
     * @param questionId 取消匿名的问题
     */
    public void cancelAdopt(Integer questionId);

    /**
     * 获取题主
     * @param questionId
     * @return
     */
    User get(Integer questionId);

    /**
     * 根据问题的id，获得问题及问题下的前length个回答的相关信息
     * @param user 用户
     * @param questionId 要获得的问题
     * @param length 要加载的回答个数
     * @param start 起始地址
     * @param sort 排序方式
     * @return json格式的问题信息
     */
    Map<String,Object> moreAnswers(User user, Integer questionId,int start, int length,int sort);

    /**
     * 插入浏览记录
     * @param userId 用户id
     * @param questionId 问题id
     */
    void insertBrowsing(Integer userId,Integer questionId);
}
