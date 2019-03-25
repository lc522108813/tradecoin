package com.coins.tradecoin.cache;

import com.coins.tradecoin.consts.CoinConsts;
import com.coins.tradecoin.consts.CoinEnum;
import com.coins.tradecoin.entity.bo.BinanceMarketKlineResult;
import com.coins.tradecoin.entity.bo.MarketBO;
import com.coins.tradecoin.service.MarketService;
import com.coins.tradecoin.utils.ModelTransfer;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

// 交易行情信息缓存
@Slf4j
@Component
public class TickerCache {

    private final static Set<Integer> BACK_DAYS = new HashSet<>(Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0));

    @Autowired
    private MarketService marketService;

    // 本地缓存，今日开盘价，key的格式 huobi:ht  binance:btc okex:okb
    private final LoadingCache<String, BigDecimal> OPEN_PRICE_CACHE = Caffeine.newBuilder()
            .refreshAfterWrite(15, TimeUnit.MINUTES)
            .expireAfterWrite(20, TimeUnit.MINUTES)
            .build(this::freshOpen);


    private BigDecimal freshOpen(String key) {
        // 拆分key
        String[] keys = StringUtils.split(key, ":");
        String exchange = keys[0];
        CoinEnum coinEnum = CoinEnum.getBySymbol(keys[1]);
        if (exchange.contains(CoinConsts.HUOBI)) {
            List<MarketBO> list = marketService.getHuobiKline(CoinConsts.HUOBI_PERIOD_ONE_DAY, CoinConsts.LIMIT_1, coinEnum.getSymbol() + CoinConsts.USDT_SYMBOL);
            MarketBO marketBO = list.get(0);
            return marketBO.getOpen();
        }
        else if (exchange.contains(CoinConsts.BINANCE)) {
            String tickerResultStr = marketService.getBinanceKline((coinEnum.getSymbol() + CoinConsts.USDT_SYMBOL).toUpperCase(), CoinConsts.BINANCE_INTERVAL_ONE_DAY, null, null, CoinConsts.LIMIT_1);
            BinanceMarketKlineResult tickerResult = ModelTransfer.transBinanceMarketKlineResult(tickerResultStr);
            return tickerResult.getOpen();
        }
        else if(exchange.contains(CoinConsts.OKEX)){
            String kLineResult = marketService.getOkexKline(coinEnum.getSymbol(), CoinConsts.USDT_SYMBOL, CoinConsts.OKEX_PERIOD_ONE_DAY);
            List<MarketBO> list=ModelTransfer.transOKKline(kLineResult);
            MarketBO marketBO = list.get(0);
            return marketBO.getOpen();
        }
        return null;
    }

    public BigDecimal getOpen(String key) {
        return OPEN_PRICE_CACHE.get(key);
    }


    //每个小时更新一次
    private final LoadingCache<String, HashMap<String, BigDecimal>> MA_10_CACHE = Caffeine.newBuilder()
            .refreshAfterWrite(60, TimeUnit.MINUTES)
            .expireAfterWrite(70, TimeUnit.MINUTES)
            .build(this::freshMA10);

    // 9日平均线缓存
    // key symbol
    // hashKey backDays，0表示今日，1表示昨日，2表示前天
    // value 当日平均线
    private HashMap<String, BigDecimal> freshMA10(String key) {
        HashMap<String, BigDecimal> hashMap = new HashMap();
        CoinEnum coinEnum = CoinEnum.getBySymbol(key);
        List<MarketBO> result = new ArrayList<>();
        if (coinEnum.getMarket().contains(CoinConsts.HUOBI)) {
            result = marketService.getHuobiKline(CoinConsts.HUOBI_PERIOD_ONE_DAY, CoinConsts.LIMIT_20, coinEnum.getSymbol() + CoinConsts.USDT_SYMBOL);
        }
        else if (coinEnum.getMarket().contains(CoinConsts.BINANCE)) {
            String resultStr = marketService.getBinanceKline((coinEnum.getSymbol() + CoinConsts.USDT_SYMBOL).toUpperCase(), CoinConsts.BINANCE_INTERVAL_ONE_DAY, null, null, CoinConsts.LIMIT_20);
            result = ModelTransfer.transListMarketBOFromBinance(resultStr);
        }
        else if(coinEnum.getMarket().contains(CoinConsts.OKEX)){
            String kLineResult = marketService.getOkexKline(coinEnum.getSymbol(), CoinConsts.USDT_SYMBOL, CoinConsts.OKEX_PERIOD_ONE_DAY);
            result = ModelTransfer.transOKKline(kLineResult);
        }
        for (Integer backDays : BACK_DAYS) {
            int total = (backDays == 0 ? 9 : 10);
            BigDecimal sum = new BigDecimal(0);
            for (int i = 10 - total; i < 10; i++) {
                MarketBO marketBO = result.get(backDays + i);
                sum = sum.add(marketBO.getClose());
            }
            BigDecimal ma = sum.divide(BigDecimal.valueOf(total), 4);
            hashMap.put(backDays.toString(), ma);
        }
        return hashMap;
    }

    public HashMap<String, BigDecimal> getMA10(String key) {
        return MA_10_CACHE.get(key);
    }


}
