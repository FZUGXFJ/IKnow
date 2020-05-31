package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Answer;
import org.gxfj.iknow.pojo.Comment;
import org.gxfj.iknow.pojo.Question;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface AnswerDAO extends BaseDAO<Answer>{

    /**
     * 获得未删除的回答列表
     * @param questionId 问题id
     * @return  回答列表(所有回答）
     */
    List<Answer> getAnswersbyQid(Integer questionId);

    /**
     * 获得未删除的回答列表
     * @param questionId 问题id
     * @param start 开始index
     * @param length 记录数
     * @return  回答列表（部分回答）
     */
    List<Answer> listByQuestionId(int questionId,int start,int length);

    /**
     * 得到未删除的回答列表
     * @param count 数量
     * @param start 开始元素
     * @return 回答列表
     */
    List<Answer> listNoDelete(Integer start, Integer count);

    /**
     * 得到最新的未删除的回答
     * @param count 回答个数
     * @return 回答列表
     */
    List<Answer> listNoDelete(Integer count);

    /**
     * 获得所有的回答列表
     * @return 回答列表
     */
    List<Answer> listNoDelete();

    List<Answer> list();

    /**
     * 在未删除的数据中查找记录
     * @param id 要查找的记录的主键
     * @return 没有则为null
     */
    Answer getNotDelete(Integer id);

    /**
     * 列出指定用户id发布的回答
     * @param userId 用户ID
     * @param start 起始位置
     * @param length 问题个数
     * @return 问题列表没有则为null
     */
    List<Answer> listPartByUserId(Integer userId, Integer start, Integer length);

    /**
     * 获得用户发布的所有回答，包括标记为删除的
     * @param userId 用户id
     * @return 回答列表，没有为null
     */
    List<Answer> listPartByUserId(Integer userId);

    /**
     * 获得未删除的回答列表（排序）
     * @param questionId 问题id
     * @param start 开始index
     * @param length 记录数
     * @param sort 排序方式
     * @return  回答列表（部分回答）
     */
    List<Answer> listByQuestionIdSort(int questionId,int start,int length,int sort);

    /**
     * 删除回答
     * @param answer 要删除的回答
     */
    void delete(Answer answer);

    /**
     * 获得用户发布的回答的数目
     * @param userId 用户id
     * @return 回答数量
     */
    public Integer getUserAnswersCount(Integer userId);

    /**
     * 根据关键字获取回答
     * @param keyword 关键字
     * @return 回答列表
     */
    List<Answer> listByKeyword(String keyword);

}
