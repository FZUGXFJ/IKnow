package org.gxfj.iknow.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.gxfj.iknow.util.ConstantUtil.*;

/**
 * @author erniumo
 */
@Controller
public class MessageAction {

    @Autowired
    MessageService messageService;

    private Integer messageId;
    private InputStream inputStream;

    /**
     * 获取消息
     * @return 前端
     */
    public String getMessage(){
        User user = (User) ActionContext.getContext().getSession().get("user");
        Map<String, Object> response = messageService.messageInf(user);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return RETURN_STRING;
    }

    /**
     * 已读消息
     * @return 前端
     */
    public String readMessage(){
        User user = (User) ActionContext.getContext().getSession().get("user");
        Map<String, Object> response = messageService.readMessage(user, messageId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return RETURN_STRING;
    }
    public InputStream getInputStream() {
        return inputStream;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }
}
