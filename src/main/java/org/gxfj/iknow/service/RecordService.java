package org.gxfj.iknow.service;

import org.gxfj.iknow.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * @author 爱学习的水先生
 */
public interface RecordService {
    /**
     *收藏记录
     * @param start 起始地址
     * @param user 用户
     * @return 收藏数据
     */
    public Map<String,Object> collectionRecord(User user, Integer start);

    /**
     *浏览记录
     * @param start 起始地址
     * @param user 用户
     * @return 浏览数据
     */
    public Map<String,Object> browsingRecord(User user, Integer start);

    /**
     *  获取用户发表的问题记录
     * @param start 起始地址
     * @param user 用户
     * @return 用户发表问题列表
     */
    public List<Map<String,Object>> listPostQuestionRecord(User user, Integer start);

    /**
     *  获取用户发表的回答记录
     * @param start 起始地址
     * @param user 用户
     * @return 用户发表问题列表
     */
    public List<Map<String,Object>> listPostAnswerRecord(User user, Integer start);
}
