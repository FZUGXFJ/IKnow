package org.gxfj.iknow.action;

import org.springframework.stereotype.Controller;

import java.io.InputStream;

import static org.gxfj.iknow.util.ConstantUtil.*;

/**
 * @author erniumo
 */
@Controller
public class MessageAction {

    private Integer messageId;
    private InputStream inputStream;

    /**
     * 获取消息
     * @return 前端
     */
    public String getMessage(){
        return RETURN_STRING;
    }

    /**
     * 已读消息
     * @return 前端
     */
    public String readMessage(){
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
