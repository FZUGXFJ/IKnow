package org.gxfj.iknow.service;

import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.dao.MessageDAO;
import org.gxfj.iknow.pojo.Message;
import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.util.ConstantUtil;
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
    public Map<String, Object> messageInf() {
        User user = (User) ActionContext.getContext().getSession().get("user");
        Map<String , Object> result = new HashMap<>(ConstantUtil.HASH_MAP_NUM);
        if (user == null){
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME,1);
            return result;
        }
        else {
            List<Map<String,Object>> messages = new ArrayList<>();
            Map<String,Object> message;
            List<Message> messageList = messageDAO.listUnReadMessageByUserId(user.getId());
            for (Message message1 : messageList){
                message = new HashMap<>(5);
                message.put("id",message1.getId());
                message.put("type",message1.getMessagetypeByTypeId().getId());
                message.put("content", HtmlUtil.html2Text(message1.getContent()));
                message.put("isRead",message1.getIsRead());
                message.put("time", Time.getTime(message1.getDate()));

                messages.add(message);
            }
            result.put("messages",messages);
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.SUCCESS);
            return result;
        }
    }

    @Override
    public Map<String, Object> readMessage( Integer messageId) {
        User user = (User) ActionContext.getContext().getSession().get("user");
        Map<String , Object> result = new HashMap<>(ConstantUtil.HASH_MAP_NUM);
        if (user == null){
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME,1);
            return result;
        }
        else {
            Message message = messageDAO.get(messageId);
            if(!user.getId().equals(message.getUserByUserId().getId())){
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME,2);
                return result;
            }
            message.setIsRead((byte)1);
            messageDAO.update(message);
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME,0);
            result.put("content",message.getContent());
            return result;
        }
    }
}
