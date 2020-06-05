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
     * @param title 问题标题
     * @param context 问题详情
     * @param categoryType 问题所属的门类
     * @param subjectType 问题所属的学科
     * @param majorType 问题所属的专业
     * @param isAnonymous 提问者发布问题时，是否匿名
     * @return 问题id
     */
    public Map<String, Object> postQuestion(String title, String context, Integer categoryType, Integer subjectType
        , Integer majorType, Byte isAnonymous);

    /**
     * 从数据库中读取问题分类中的所有门类、学科、专业
     * @return 以Map形式嵌套存储所有分类
     */
    public Map<String, Object>getQuestionType();
    
    /**
     * 根据问题的id，获得问题及问题下的前length个回答的相关信息
     * @param questionId 要获得的问题
     * @param length 要加载的回答个数
     * @param sort 排序方式
     * @return json格式的问题信息
     */
    public Map<String, Object>getQuestion(Integer questionId, Integer length,Integer sort);

    /**
     * 根据问题的id，获得问题及问题下的前length个回答的相关信息
     * @param questionId 取消匿名的问题
     */
    public Map<String, Object> cancelAdopt(Integer questionId);

    /**
     * 获取题主
     * @param questionId
     * @return
     */
    User get(Integer questionId);

    /**
     * 根据问题的id，获得问题及问题下的前length个回答的相关信息
     * @param questionId 要获得的问题
     * @param length 要加载的回答个数
     * @param start 起始地址
     * @return json格式的问题信息
     */
    Map<String,Object> moreAnswers (Integer questionId,int start, int length);

    /**
     * 插入浏览记录
     * @param userId 用户id
     * @param questionId 问题id
     */
    void insertBrowsing(Integer userId,Integer questionId);

    /**
     * 删除问题
     * @param questionId 要删除问题
     * @return 删除的结果
     */
    Map<String, Object> deleteQuestion(Integer questionId);

    /**
     * 获取问题信息
     * @param questionId 问题id
     * @return 问题信息
     */
    Map<String,Object> getQuestioninf(Integer questionId);

    /**
     * 修改问题信息
     * @param newQuestionId 新的问题id
     * @param newQuestionTitle 新的问题标题
     * @param newQuestionContent 新的问题内容
     * @param newCategoriesType 新的门类类别id
     * @param newSubjectType 新的学科类别id
     * @param newMajorType 新的专业类别id
     * @return 是否成功
     */
    Map<String,Object> updateQuesiton(Integer newQuestionId, String newQuestionTitle, String newQuestionContent,
                                       Integer newCategoriesType, Integer newSubjectType, Integer newMajorType);

    /**
     * 邀请回答
     * @param questionId 问题id
     * @param userId 被邀请人用户id
     * @return 是否成功
     */
    Map<String,Object> inviteAnswer(Integer questionId, Integer userId);
}
