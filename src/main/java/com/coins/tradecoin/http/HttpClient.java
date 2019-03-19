package com.coins.tradecoin.http;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@Component
public class HttpClient {

    /**
     * 默认字符集
     **/
    public static final String DEFAULT_CHARSET = "UTF-8";

    @Autowired
    private CloseableHttpClient closeableHttpClient;

    @Autowired
    private RequestConfig config;


    /**
     * get请求处理
     **/

    public <T> T doGet(String url, TreeMap<String, Object> requestParameter, Class<T> clazz) throws Exception {
        String responseJson = this.doGet(url, requestParameter);
        T response = JSONObject.parseObject(responseJson, clazz);
        return response;
    }


    public String doGet(String url, TreeMap<String, Object> requestParameter) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(url);
        if (requestParameter != null) {
            for (Map.Entry<String, Object> entry : requestParameter.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }
        /** 打印get请求 **/
//        log.info("doing get request \n"+uriBuilder.build().toString());
        return this.doGet(uriBuilder.build().toString());
    }


    public String doGet(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);
        httpGet.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36");
        CloseableHttpResponse response;
        int statusCode = HttpStatus.SC_OK;
        String returnBody;
        do {
            // 非首次尝试，间隔150ms
            if (statusCode != HttpStatus.SC_OK) {
                Thread.sleep(150);
            }
            response = this.closeableHttpClient.execute(httpGet);
            statusCode = response.getStatusLine().getStatusCode();
            returnBody = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
            EntityUtils.consumeQuietly(response.getEntity());
        } while (StringUtils.isBlank(returnBody) || statusCode != HttpStatus.SC_OK);
        return returnBody;
    }


    /**
     * post请求处理
     **/
    public HttpResult doPost(String url) throws IOException {
        return this.doPost(url, null);
    }

    public <T> T doPost(String url, LinkedHashMap<String, Object> requestParameter, Class<T> clazz) throws Exception {
        HttpResult httpResult = this.doPost(url, requestParameter);
        int statusCode = httpResult.getCode();
        if (statusCode != HttpStatus.SC_OK) {
            throw new Exception("api request exception, http reponse code:" + statusCode);
        }

        T response = JSONObject.parseObject(httpResult.getBody(), clazz);
        return response;
    }


    public HttpResult doPost(String url, LinkedHashMap<String, Object> requestParameter) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);
        httpPost.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
        httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36");

        if (null != requestParameter) {
            String requestBody = JSONObject.toJSONString(requestParameter);
            StringEntity postEntity = new StringEntity(requestBody, "UTF-8");
            httpPost.setEntity(postEntity);
        }

        CloseableHttpResponse response = this.closeableHttpClient.execute(httpPost);
        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET));

    }


}
