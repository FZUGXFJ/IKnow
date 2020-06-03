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
    //学校id
    private Integer school;
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
        result.put(ConstantUtil.RETURN_STRING,ConstantUtil.SUCCESS);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 学生认证
     */
    public String stuAuthentication(){
        Map<String , Object> session = ActionContext.getContext().getSession();
        Map<String,Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        User user=(User)session.get(ConstantUtil.SESSION_USER);
        if(user==null){
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.UN_LOGIN_TWO);
        }
        else {
            if (identityService.stuAuthentication(user,school,realName,studentNum)){
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.SUCCESS);
            }
            else {
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.AUTHENTICATION_FAILED);
            }
        }
        inputStream = new ByteArrayInputStream(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 教师认证
     */
    public String teaAuthentication(){
        Map<String , Object> session = ActionContext.getContext().getSession();
        Map<String,Object> result = new HashMap<>(ConstantUtil.MIN_HASH_MAP_NUM);
        User user=(User)session.get(ConstantUtil.SESSION_USER);
        if(user==null){
            result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.UN_LOGIN_TWO);
        }
        else {
            if (identityService.teaAuthentication(user,school,realName,jobNum)){
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.SUCCESS);
            }
            else {
                result.put(ConstantUtil.JSON_RETURN_CODE_NAME,ConstantUtil.AUTHENTICATION_FAILED);
            }
        }
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

    public Integer getSchool() {
        return school;
    }

    public void setSchool(Integer school) {
        this.school = school;
    }
}
