package com.coins.tradecoin.http;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfig {

    /** 最大连接数 **/
    public static final int maxTotal=400;

    /** 当前主机到目的主机的一个路由，主要作用在通过httpClient转发请求到不同的目的主机的连接数限制 **/
    public static final int defaultMaxPerRoute=200;

    /** 创建连接的最长连接时间 **/
    public static final int connectTimeout=1000;

    /** 从连接池中获取到连接的最长时间 **/
    public static final int connectionRequestTimeout=500;

    /** 数据传输timeout **/
    public static final int socketTimeout=10000;

    /** 不活跃失效时间 **/
    public static final int validateAfterInactivity=1000;


    @Bean
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager(){
        PoolingHttpClientConnectionManager connectionManager=new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(maxTotal);
        connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        connectionManager.setValidateAfterInactivity(validateAfterInactivity);
        return connectionManager;

    }

    @Bean
    public HttpClientBuilder httpClientBuilder(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager){
        HttpClientBuilder httpClientBuilder=HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
        return httpClientBuilder;
    }

    @Bean
    public CloseableHttpClient closeableHttpClient(HttpClientBuilder httpClientBuilder){
        return httpClientBuilder.build();
    }

    @Bean
    public RequestConfig.Builder builder(){
        RequestConfig.Builder builder=RequestConfig.custom();
        return builder.setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setSocketTimeout(socketTimeout);

    }

    @Bean
    public RequestConfig requestConfig(RequestConfig.Builder builder){
        return builder.build();
    }



}
