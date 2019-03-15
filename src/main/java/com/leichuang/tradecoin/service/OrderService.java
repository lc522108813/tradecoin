package com.leichuang.tradecoin.service;

import com.leichuang.tradecoin.consts.UriConsts;
import com.leichuang.tradecoin.enums.RequestType;
import com.leichuang.tradecoin.http.HttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.TreeMap;

@Slf4j
@Service
public class OrderService extends BaseService {

    private String huobiApiUrl=UriConsts.HUOBI_PRO_PREFIX;

    @Autowired
    private HttpClient httpClient;

    @PostConstruct
    public void test() throws Exception {
//        String result=getSpotAccountAssets();
//        log.info("In OrderService test:");
//        log.info(result);

    }


    /**
     * 获取官方时间戳
     **/
    public String getHuobiTimestamp() throws Exception {
        TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
        return httpClient.doGet(huobiApiUrl + UriConsts.HUOBI_TIMESTAMP, paraMap);
    }

    /**
     * 查询用户所有账户
     **/
    public String getAllAccount() throws Exception {
        TreeMap<String, Object> paramMap = new TreeMap<>();
        /** 添加签名 **/
        String signature = getSignature(RequestType.GET, huobiApiUrl, UriConsts.HUOBI_FUTURE_ALL_ACCOUNT, paramMap);
        paramMap.put("Signature", signature);
        return httpClient.doGet(huobiApiUrl + UriConsts.HUOBI_FUTURE_ALL_ACCOUNT, paramMap);
    }

    /** 查询当前现货账户的资产 **/
    public String getSpotAccountAssets() throws Exception {
        TreeMap<String, Object> paramMap = new TreeMap<>();
        /** 添加签名 **/
        String urlSuffix=UriConsts.HUOBI_FUTURE_ALL_ACCOUNT+"/"+getAccountId()+"/balance";
        String signature = getSignature(RequestType.GET, huobiApiUrl, urlSuffix, paramMap);
        paramMap.put("Signature", signature);
        return httpClient.doGet(huobiApiUrl + urlSuffix, paramMap);
    }


}
