package com.leichuang.tradecoin.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leichuang.tradecoin.entity.bo.BinanceMarketKlineResult;
import com.leichuang.tradecoin.entity.bo.HuobiMarketResult;
import com.leichuang.tradecoin.entity.bo.MarketBO;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

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
        HuobiMarketResult huobiMarketResult = JSONObject.parseObject(jsonStr, HuobiMarketResult.class);
        if (huobiMarketResult != null && huobiMarketResult.getData() != null && huobiMarketResult.getData().size() != 0) {
            marketBO = huobiMarketResult.getData().get(0);
        }
        return marketBO;
    }

}
