package org.gxfj.iknow.action;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import org.gxfj.iknow.pojo.User;
import org.gxfj.iknow.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import static org.gxfj.iknow.util.ConstantUtil.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author erniumo
 */

@Controller
public class ReportAction {

    @Autowired
    ReportService reportService;

    private Integer type;
    private Integer reason;
    private String description;
    private Integer targetId;

    private InputStream inputStream;

    public String getReportReason(){
        Map<String, Object> response = reportService.reportReasonmap();
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return RETURN_STRING;
    }

    public String doReport(){
        Map<String, Object> response = reportService.doReport(type,reason,description,targetId);
        inputStream = new ByteArrayInputStream(JSON.toJSONString(response).getBytes(StandardCharsets.UTF_8));
        return RETURN_STRING;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getReason() {
        return reason;
    }

    public void setReason(Integer reason) {
        this.reason = reason;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }
}
