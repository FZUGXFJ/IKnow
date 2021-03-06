package org.gxfj.iknow.action;

import com.alibaba.fastjson.JSON;
import org.gxfj.iknow.service.PartitionService;
import org.gxfj.iknow.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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

    /*
    private final Integer UN_LOGIN = 1;
    private static Integer HASH_MAP_NUM = 20;
    private final static int MIN_HASH_MAP_NUM = 10;
    private final static String JSON_RESULT_CODE = "resultCode";
    private final static String JSON_QUESIONTS = "questions";
    private final static int SUCCESS = 0;
    private final static int RESPONSE_NUM = 20;
    private final static String SESSION_USER="user";
    private final static int PARTITION_QUESTION_COUNT = 20;
     */

    /**
     * 获取类别
     * @return SUCCESS
     */
    public String getCategories(){
        Map<String, Object> response = partitionService.getCategories();
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    /**
     * 获得学科
     * @return SUCCESS
     */
    public String getSubjects(){
        Map<String, Object> response = partitionService.getSubjects(categoryId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String getMajors(){
        Map<String, Object> response = partitionService.getMajors(subjectId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
    }

    public String getQuestion() {
        Map<String, Object> response = partitionService.getQuestion
                (categoryId,subjectId,majorId,start,ConstantUtil.PARTITION_QUESTION_COUNT);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return ConstantUtil.RETURN_STRING;
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
