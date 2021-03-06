package org.gxfj.iknow.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.service.AnswerService;
import org.gxfj.iknow.service.CollectionService;
import org.gxfj.iknow.service.RecordService;
import org.gxfj.iknow.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.gxfj.iknow.util.ConstantUtil.*;

/**
 * @author 爱学习的水先生
 */


@Controller
public class RecordAction {
    private Integer start;
    private InputStream inputStream;

    /*final static private int RESPONSE_NUM = 20;

    private final String SESSION_USER = "user";
    private final int SUCCESS = 0;
    private final int UN_LOGIN = 2;
    private final int NO_MORE = 1;*/

    @Autowired
    RecordService recordService;

    public InputStream getInputStream() { return inputStream; }

    public String collectionRecord(){
        User user = (User) ActionContext.getContext().getSession().get("user");
        Map<String, Object> response = recordService.collectionRecord(user, start);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String browsingRecord(){
        User user = (User) ActionContext.getContext().getSession().get("user");
        Map<String, Object> response = recordService.browsingRecord(user, start);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String postQueRecord(){
        User user = (User) ActionContext.getContext().getSession().get("user");
        Map<String, Object> response = recordService.listPostQuestionRecord(user, start);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String postAnsRecord(){
        User user = (User) ActionContext.getContext().getSession().get("user");
        Map<String, Object> response = recordService.listPostAnswerRecord(user, start);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getStart() {
        return start;
    }
}
