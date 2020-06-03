package org.gxfj.iknow.service;

import org.gxfj.iknow.dao.MessageDAO;
import org.gxfj.iknow.pojo.Message;
import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.util.HtmlUtil;
import org.gxfj.iknow.util.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author erniumo
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService{
    @Autowired
    MessageDAO messageDAO;
    @Override
    public Map<String, Object> messageInf(User user) {
        Map<String,Object> response = new HashMap<>(20);
        if (user == null){
            response.put("resultCode",1);
            return response;
        }
        else {
            List<Map<String,Object>> messages = new ArrayList<>();
            Map<String,Object> message;
            List<Message> messageList = messageDAO.listByUserId(user.getId());
            for (Message message1 : messageList){
                message = new HashMap<>(5);
                message.put("id",message1.getId());
                message.put("type",message1.getMessagetypeByTypeId().getId());
                message.put("content", HtmlUtil.html2Text(message1.getContent()));
                message.put("isRead",message1.getIsRead());
                message.put("time", Time.getTime(message1.getDate()));

                messages.add(message);
            }
            response.put("messages",messages);
            response.put("resultCode",0);
            return response;
        }
    }

    @Override
    public Map<String, Object> readMessage(User user, Integer messageId) {
        Map<String,Object> response = new HashMap<>(20);
        if (user == null){
            response.put("resultCode",1);
            return response;
        }
        else {
            Message message = messageDAO.get(messageId);
            if(user.getId().equals(message.getUserByUserId().getId())){
                response.put("resultCode",2);
                return response;
            }
            message.setIsRead((byte)1);
            messageDAO.update(message);
            response.put("resultCode",0);
            response.put("content",message.getContent());
            return response;
        }
    }
}
