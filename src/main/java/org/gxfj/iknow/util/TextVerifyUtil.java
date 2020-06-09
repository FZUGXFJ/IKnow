package org.gxfj.iknow.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author QMBX
 */
public class TextVerifyUtil {
    public final static String VERIFY_URL = "https://aip.baidubce.com/rest/2" +
            ".0/solution/v1/text_censor/v2/user_defined";
    public final static String ACCESS_TOKEN = "24.b859f8401fb773512c99cc523e16a0fd.2592000.1593670206" +
            ".282335-20185621";
    public final static String ACCESS_TOKEN_NAME = "access_token";

    public final static String VERIFY_RESULT_KEY = "conclusionType";

    public final static Integer VERIFY_RESULT_SUCCESS = 1;

    public final static Integer VERIFY_RESULT_SUSPECTED = 3;

    /**
     * 验证文本是否合规
     * @param text  需要验证的文本
     * @return 是否合规
     */
    public static boolean verifyCompliance(String text) {
        if (text == null || text.equals("")) {
            return true;
        }
        JSONObject jsonObject = JSON.parseObject(httpPostRequest(VERIFY_URL, text));
        System.out.println(JSON.toJSONString(jsonObject));
        if (VERIFY_RESULT_SUCCESS.equals(jsonObject.get(VERIFY_RESULT_KEY))
                || VERIFY_RESULT_SUSPECTED.equals(jsonObject.get(VERIFY_RESULT_KEY))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 向文本验证的网址发送请求
     * @param url 验证的网址
     * @param text 需要审核的文本
     * @return 响应的JSON对象
     */
    public static String httpPostRequest(String url, String text) {
        if(url == null){

        }
        String result = "";
        try {
            // 创建httpClient实例
            CloseableHttpClient httpClient = HttpClients.createDefault();
            // 创建httpPost远程连接实例
            HttpPost httpPost = new HttpPost(url);
            // 配置请求参数实例
            // 设置连接主机服务超时时间
            // 设置连接请求超时时间
            // 设置读取数据连接超时时间
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000)
                    .setConnectionRequestTimeout(10000)
                    .setSocketTimeout(30000)
                    .build();
            // 为httpPost实例设置配置
            httpPost.setConfig(requestConfig);
            // 设置请求头
            httpPost.addHeader("content-type", "application/x-www-form-urlencoded");
            // 封装post请求参数

            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair(ACCESS_TOKEN_NAME, ACCESS_TOKEN));
            nameValuePairs.add(new BasicNameValuePair("text", text));

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
//            httpPost.setEntity(new StringEntity(paramsJson.toJSONString(), Charset.forName("UTF-8")));
            // httpClient对象执行post请求,并返回响应参数对象
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            result = EntityUtils.toString(httpResponse.getEntity());
        } catch (UnsupportedEncodingException e) {
            System.out.println("URLUtil.httpPostRequest encounters an UnsupportedEncodingException : {}");
        } catch (IOException e) {
            System.out.println("URLUtil.httpPostRequest encounters an IOException : {}");
        }
        return result;
    }
}