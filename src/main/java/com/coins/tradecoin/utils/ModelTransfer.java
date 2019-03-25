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

    // 返回的数组中又200条记录，只处理其中前30条记录
    private static final Integer OK_KLINE_PROCESS_MAX_SIZE=30;

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
            log.info("错误json转换发生: {}", jsonStr);
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
            log.info("错误json转换发生: {}", jsonStr);
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

    public static MarketBO transOKTicker(String jsonStr) {
        MarketBO marketBO = new MarketBO();
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        BigDecimal close = new BigDecimal((String) jsonObject.get("last"));
        BigDecimal open = new BigDecimal((String) jsonObject.get("open_24h"));
        marketBO.setClose(close);
        marketBO.setVol(new BigDecimal((String) jsonObject.get("base_volume_24h")));
        BigDecimal gains = close.subtract(close).divide(open).multiply(new BigDecimal(100));
        marketBO.setGains(gains);
        return marketBO;

    }

    public static List<MarketBO> transOKKline(String jsonStr) {
        List<MarketBO> result = new ArrayList<>();
        JSONArray coinArray = JSONArray.parseArray(jsonStr);
        log.info("trans OKKline时的入参 {}",jsonStr);
        for (int i = 0; i < OK_KLINE_PROCESS_MAX_SIZE; i++) {
            MarketBO marketBO = new MarketBO();
            JSONArray jsonArray = coinArray.getJSONArray(i);
            log.info("循环内部jsonArray，每个元素 {}",jsonArray.toJSONString());
            String time = jsonArray.get(0).toString();
            String open = jsonArray.get(1).toString();
            String high = jsonArray.get(2).toString();
            String low = jsonArray.get(3).toString();
            String close = jsonArray.get(4).toString();
            String volume = jsonArray.get(5).toString();
            marketBO.setOpen(new BigDecimal(open));
            marketBO.setClose(new BigDecimal(close));
            marketBO.setVol(new BigDecimal(volume));
            result.add(marketBO);
        }
        return result;

    }


}
