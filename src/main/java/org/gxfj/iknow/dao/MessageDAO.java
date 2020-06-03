package org.gxfj.iknow.dao;

import org.gxfj.iknow.pojo.Message;
;

import java.util.List;


public interface MessageDAO extends BaseDAO<Message>{
    /**
     * 根据用户id获取消息列表
     * @param userId 用户id
     * @return 消息列表
     */
    List<Message> listByUserId(Integer userId);

}
