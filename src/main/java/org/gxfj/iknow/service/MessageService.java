package org.gxfj.iknow.service;

import org.gxfj.iknow.pojo.User;

import java.util.Map;

public interface MessageService {
    /**
     * 获取消息
     * @param user 用户信息
     * @return json数据
     */
    Map<String,Object> messageInf(User user);

    /**
     * 读消息
     * @param user 用户信息
     * @param messageId 消息id
     * @return json数据
     */
    Map<String,Object> readMessage(User user,Integer messageId);
}
