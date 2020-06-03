package org.gxfj.iknow.util;

import org.gxfj.iknow.dao.MessageDAO;
import org.gxfj.iknow.dao.MessageTypeDAO;
import org.gxfj.iknow.pojo.Message;
import org.gxfj.iknow.pojo.Messagetype;
import org.gxfj.iknow.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Date;

/**
 * @author erniumo
 */
@Component
public class MessageUtil {
    @Autowired
    MessageDAO messageDAO;
    @Autowired
    MessageTypeDAO messageTypeDAO;

    /**
     * 新建消息
     * @param type 消息类型
     * @param user  用户信息
     * @param content 消息内容
     */
    public void newMessage(Integer type,User user,String content){
        Message message = new Message();
        Messagetype messagetype=messageTypeDAO.get(type);
        message.setIsRead((byte)0);
        message.setContent(content);
        message.setDate(new Date());
        message.setUserByUserId(user);
        message.setMessagetypeByTypeId(messagetype);

        messageDAO.add(message);
    }
}
