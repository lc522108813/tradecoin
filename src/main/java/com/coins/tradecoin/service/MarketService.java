package com.coins.tradecoin.service;

import com.coins.tradecoin.consts.UriConsts;
import com.coins.tradecoin.entity.bo.MarketBO;
import com.coins.tradecoin.http.HttpClient;
import com.coins.tradecoin.retry.RetryDot;
import com.coins.tradecoin.utils.ModelTransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TreeMap;

@Slf4j
@Service
public class MarketService {

    @Autowired
    private HttpClient httpClient;

    // 火币有时候会给data为空 需要加重试
    @RetryDot(count = 3)
    public List<MarketBO> getHuobiKline(String period, Integer size, String symbol) {
        TreeMap<String, Object> params = new TreeMap<>();
        params.put("period", period);
        params.put("size", size);
        params.put("symbol", symbol);
        List<MarketBO> list = null;
        while (null == list || list.size() == 0) {
            try {
                String httpResultStr = httpClient.doGet(UriConsts.HUOBI_PRO_PREFIX + UriConsts.HUOBI_FUTURE_KLINE, params);
                list = ModelTransfer.transListMarketBO(httpResultStr);
            } catch (Exception e) {
                log.error("call http HUOBI_FUTURE_KLINE failed");
                e.printStackTrace();
            }
        }
        return list;

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

    @RetryDot(count=3)
    public String getOkexTicker(String symbol,String currency){
        String instrument=symbol+"-"+currency;
        try {
            return httpClient.doGet(UriConsts.OKEX_PREFIX + String.format(UriConsts.OKEX_TIKER,instrument.toUpperCase()));
        } catch (Exception e) {
            log.error("call http OKEX_TIKER failed");
            e.printStackTrace();
        }
        return null;
    }

    @RetryDot(count=3)
    public String getOkexKline(String symbol,String currency,Integer granularity){
        String instrument=symbol+"-"+currency;
        TreeMap<String, Object> params = new TreeMap<>();
        params.put("granularity", granularity);
        try {
            return httpClient.doGet(UriConsts.OKEX_PREFIX + String.format(UriConsts.OKEX_KLINE,instrument.toUpperCase()),params);
        } catch (Exception e) {
            log.error("call http OKEX_KLINE failed");
            e.printStackTrace();
        }
        return null;
    }

}
