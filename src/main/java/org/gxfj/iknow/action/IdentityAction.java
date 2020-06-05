package org.gxfj.iknow.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.service.IdentityService;
import org.gxfj.iknow.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 爱学习的水先生
 */

@Controller
public class IdentityAction {
    @Autowired
    IdentityService identityService;

    private String keyword;
    //学校名
    private String school;
    private String realName;
    private String studentNum;
    private String jobNum;
    private InputStream inputStream;

    /**
     * 根据关键字获取相应的学校列表
     * keyword：关键字
     */
    public String getSchools(){
        Map<String,Object> result = identityService.getSchool(keyword);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 学生认证
     */
    public String stuAuthentication(){
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String,Object> result = identityService.stuAuthentication(user, school , realName , studentNum);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 教师认证
     */
    public String teaAuthentication(){
        User user = (User) ActionContext.getContext().getSession().get(ConstantUtil.SESSION_USER);
        Map<String,Object> result = identityService.teaAuthentication(user, school, realName, jobNum);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
