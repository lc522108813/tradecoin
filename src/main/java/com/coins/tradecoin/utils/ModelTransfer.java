package com.coins.tradecoin.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.coins.tradecoin.entity.bo.BinanceMarketKlineResult;
import com.coins.tradecoin.entity.bo.HuobiMarketResult;
import com.coins.tradecoin.entity.bo.MarketBO;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ModelTransfer {

    static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static BinanceMarketKlineResult transBinanceMarketKlineResult(String jsonStr) {
        JSONArray coinArray = JSONArray.parseArray(jsonStr);
        JSONArray coin = JSONArray.parseArray(coinArray.get(0).toString());
        BinanceMarketKlineResult result = new BinanceMarketKlineResult();
        result.setOpenTime(Long.valueOf(String.valueOf(coin.get(0))));
        result.setOpen(new BigDecimal((String) coin.get(1)));
        result.setHigh(new BigDecimal((String) coin.get(2)));
        result.setLow(new BigDecimal((String) coin.get(3)));
        result.setClose(new BigDecimal((String) coin.get(4)));
        result.setVolume(new BigDecimal((String) coin.get(5)));
        return result;
    }

    public static MarketBO transMarketBO(String jsonStr) {
        MarketBO marketBO = new MarketBO();
        HuobiMarketResult huobiMarketResult = null;
        try {
            huobiMarketResult = JSONObject.parseObject(jsonStr, HuobiMarketResult.class);
        } catch (Exception e) {
            log.info("错误json转换发生: {}",jsonStr);
            e.printStackTrace();
        }
        if (huobiMarketResult != null && huobiMarketResult.getData() != null && huobiMarketResult.getData().size() != 0) {
            marketBO = huobiMarketResult.getData().get(0);
        }
        return marketBO;
    }

    public static List<MarketBO> transListMarketBO(String jsonStr) {
        List<MarketBO> result = new ArrayList<>();
        HuobiMarketResult huobiMarketResult = null;
        try {
            huobiMarketResult = JSONObject.parseObject(jsonStr, HuobiMarketResult.class);
        } catch (Exception e) {
            log.info("错误json转换发生: {}",jsonStr);
            e.printStackTrace();
        }
        if (huobiMarketResult != null && huobiMarketResult.getData() != null && huobiMarketResult.getData().size() != 0) {
            result = huobiMarketResult.getData();
        }
        return result;
    }

    public static List<MarketBO> transListMarketBOFromBinance(String jsonStr) {
        List<MarketBO> result = new ArrayList<>();
        JSONArray coinArray = JSONArray.parseArray(jsonStr);
        for (int i = 0; i < coinArray.size(); i++) {
            MarketBO marketBO = new MarketBO();
            JSONArray coin = JSONArray.parseArray(coinArray.get(i).toString());
            marketBO.setOpen(new BigDecimal((String) coin.get(1)));
            marketBO.setHigh(new BigDecimal((String) coin.get(2)));
            marketBO.setLow(new BigDecimal((String) coin.get(3)));
            marketBO.setClose(new BigDecimal((String) coin.get(4)));
            marketBO.setVol(new BigDecimal((String) coin.get(5)));
            result.add(marketBO);
        }
        return result;
    }
}
