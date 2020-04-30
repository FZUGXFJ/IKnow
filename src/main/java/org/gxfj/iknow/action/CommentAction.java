package org.gxfj.iknow.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hhj
 */
@Controller
public class CommentAction {
    private Integer answerId;
    private String content;
    private InputStream inputStream;
    @Autowired
    CommentService commentService;

    private final int SUCCESS = 0;
    private final int UN_LOGIN = 1;
    private final int MISS_COMMENT_INF = 2;

    public InputStream getInputStream() { return inputStream; }

    final static private int RESPONSE_NUM = 20;
    public String postComment(){
        Map<String , Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response = new HashMap<>(RESPONSE_NUM);
        User user = (User) session.get("user");
        if(user == null){
            response.put("resultCode" , UN_LOGIN);
        }
        else if(content == null){
            response.put("resultCode" , MISS_COMMENT_INF );
        }
        else{
            commentService.postComment(user , answerId , content);
            response.put("resultecode" , SUCCESS);
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "success";
    }

    public String viewComments(){
        Map<String , Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response = new HashMap<>(RESPONSE_NUM);
        response=commentService.getComments(answerId);
        response.put("resultCode",SUCCESS);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return  "success";
    }
}
