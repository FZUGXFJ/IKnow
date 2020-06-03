package org.gxfj.iknow.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author QMBX
 */
@Component
public class UrlUtil {

    /**
     * 未实现
     * 采用不同的request，
     * @param request
     * @return
     */
    public String getUrl(Map<String, Object> request) {
        return null;
    }

    /**
     * 获得当前请求的根目录
     * 例如当前请求的:http://localhost:8080/IKnow_war_exploded/user/myInformation.action
     * 则返回值为http://localhost:8080/IKnow_war_exploded/
     * @param request 请求头
     * @return 根目录
     */
    public String getUrl(HttpServletRequest request) {
        String url =
                request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
        System.out.println(url);
        return url;
    }
}
