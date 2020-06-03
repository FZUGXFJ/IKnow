package org.gxfj.iknow.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author QMBX
 */
public class TextVerifyUtil {
    private final static String VERIFY_URL = "https://aip.baidubce.com/rest/2" +
            ".0/solution/v1/text_censor/v2/user_defined";
    private final static String ACCESS_TOKEN = "24.b859f8401fb773512c99cc523e16a0fd.2592000.1593670206" +
            ".282335-20185621";
    private final static String ACCESS_TOKEN_NAME = "access_token";

    private final static String VERIFY_RESULT_KEY = "conclusionType";

    private final static Integer VERIFY_RESULT_SUCCESS = 1;

    /**
     * 向百度api发送请求审核文本
     * @param text 需要审核的文本
     * @return 响应的JSON对象
     */
    public static JSONObject verifyText(String text) {
        JSONObject jsonObject = null;

        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 参数
        StringBuffer params = new StringBuffer();
        try {
            // 字符数据最好encoding以下;这样一来，某些特殊字符才能传过去(如:某人的名字就是“&”,不encoding的话,传不过去)
            params.append(ACCESS_TOKEN_NAME + "=" + URLEncoder.encode(ACCESS_TOKEN, "utf-8"));
            params.append("&");
            params.append("text" + "=" + URLEncoder.encode(text,"utf-8"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String url = VERIFY_URL + "?" + params;
        System.out.println(url);
        // 创建Post请求
        HttpPost httpPost = new HttpPost(url);
        // 设置ContentType(注:如果只是传普通参数的话,ContentType不一定非要用application/json)
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            //获得响应状态: response.getStatusLine()
            //获得响应内容长度: responseEntity.getContentLength()
            //获得响应内容: EntityUtils.toString(responseEntity)
            if (responseEntity != null) {
                jsonObject = JSON.parseObject(EntityUtils.toString(responseEntity));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    /**
     * 验证文本是否合规
     * @param text  需要验证的文本
     * @return 是否合规
     */
    public static boolean verifyCompliance(String text) {
        JSONObject jsonObject = verifyText(text);
        if (VERIFY_RESULT_SUCCESS.equals(jsonObject.get(VERIFY_RESULT_KEY))) {
            return true;
        } else {
            return false;
        }
    }
}