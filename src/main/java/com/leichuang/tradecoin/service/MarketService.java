package com.leichuang.tradecoin.service;

import com.leichuang.tradecoin.consts.UriConsts;
import com.leichuang.tradecoin.http.HttpClient;
import com.leichuang.tradecoin.retry.RetryDot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.TreeMap;

@Slf4j
@Service
public class MarketService {

    @Autowired
    private HttpClient httpClient;

    @RetryDot(count = 3)
    public String getHuobiKline(String period, Integer size, String symbol) {
        TreeMap<String, Object> params = new TreeMap<>();
        params.put("period", period);
        params.put("size", size);
        params.put("symbol", symbol);
        try {
            return httpClient.doGet(UriConsts.HUOBI_PRO_PREFIX + UriConsts.HUOBI_FUTURE_KLINE, params);
        } catch (Exception e) {
            log.error("call http HUOBI_FUTURE_KLINE failed");
            e.printStackTrace();
        }
        return null;

    }


    @RetryDot(count = 3)
    public String getBinanceKline(String symbol, String interval, Long startTime, Long endTime, Integer limit) {
        TreeMap<String, Object> params = new TreeMap<>();
        params.put("symbol", symbol);
        if (interval != null) {
            params.put("interval", interval);
        }
        if (startTime != null) {
            params.put("startTime", startTime);
        }
        if (endTime != null) {
            params.put("endTime", endTime);
        }
        if (limit != null) {
            params.put("limit", limit);
        }
        try {
            return httpClient.doGet(UriConsts.BINANCE_PREFIX + UriConsts.BINANCE_MARKET_KLINE, params);
        } catch (Exception e) {
            log.error("call http BINANCE_MARKET_KLINE failed");
            e.printStackTrace();
        }
        return null;
    }

}
