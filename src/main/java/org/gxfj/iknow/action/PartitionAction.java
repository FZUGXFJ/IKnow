package org.gxfj.iknow.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.service.PartitionService;
import org.gxfj.iknow.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author erniumo
 */

@Controller
public class PartitionAction {

    @Autowired
    PartitionService partitionService;

    private Integer categoryId;
    private Integer subjectId;
    private Integer majorId;
    private Integer start;

    private InputStream inputStream;



    public String getCategories(){
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response = new HashMap<>(ConstantUtil.RESPONSE_NUM);
        User user = (User)session.get(ConstantUtil.SESSION_USER);
        if(user == null){
            response.put(ConstantUtil.RESULT_CODE, ConstantUtil.UN_LOGIN);
        }
        else {
            response=partitionService.getCategories();
            response.put(ConstantUtil.RESULT_CODE,ConstantUtil.SUCCESS);
        }

        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "success";
    }

    public String getSubjects(){
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response = new HashMap<>(ConstantUtil.RESPONSE_NUM);
        User user = (User)session.get(ConstantUtil.SESSION_USER);
        if(user == null){
            response.put(ConstantUtil.RESULT_CODE, ConstantUtil.UN_LOGIN);
        }
        else {
            response=partitionService.getSubjects(categoryId);
            response.put(ConstantUtil.RESULT_CODE,ConstantUtil.SUCCESS);
        }

        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "success";
    }

    public String getMajors(){
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response = new HashMap<>(ConstantUtil.RESPONSE_NUM);
        User user = (User)session.get(ConstantUtil.SESSION_USER);
        if(user == null){
            response.put(ConstantUtil.RESULT_CODE, ConstantUtil.UN_LOGIN);
        }
        else {
            response=partitionService.getMajors(subjectId);
            response.put(ConstantUtil.RESULT_CODE,ConstantUtil.SUCCESS);
        }

        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return "success";
    }

    public String getQuestion() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        Map<String, Object> response = new HashMap<>(ConstantUtil.RESPONSE_NUM);
        response = partitionService.getQuestion(categoryId,subjectId,majorId,start,ConstantUtil.PARTITION_QUESTION_COUNT);
        response.put(ConstantUtil.RESULT_CODE, ConstantUtil.SUCCESS);

        return "success";
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }
}