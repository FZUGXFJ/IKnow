package org.gxfj.iknow.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;
import org.gxfj.iknow.pojo.Admin;
import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.service.AdminService;
import org.gxfj.iknow.service.UserService;
import org.gxfj.iknow.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
public class AdminAction {
    private Integer accountNum;
    private String password;

    @Autowired
    private AdminService adminService;

    private InputStream inputStream;
    private final String SUCCESS = "success";
    private static Integer HASH_MAP_NUM = 20;

    public String logout(){
        Map<String,Object> session = ActionContext.getContext().getSession();
        if(!session.isEmpty()){
            session.clear();
        }
        inputStream = new ByteArrayInputStream("{\"response\":0}".getBytes(StandardCharsets.UTF_8));
        return  SUCCESS;
    }

    public String login(){
        Map resultMap = new HashMap(HASH_MAP_NUM);
        Admin adminInf = new Admin();
        adminInf.setAccount(accountNum);
        adminInf.setPasswd(password);
        Admin admin = adminService.login(adminInf);
        if(admin == null){
            resultMap.put("resultCode" , 1);
        }
        else{
            resultMap.put("resultCode" , 0);
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(resultMap).getBytes(StandardCharsets.UTF_8));
        return SUCCESS;
    }
}
