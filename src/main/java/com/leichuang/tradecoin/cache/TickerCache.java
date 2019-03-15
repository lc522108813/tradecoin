package com.leichuang.tradecoin.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.leichuang.tradecoin.consts.CoinConsts;
import com.leichuang.tradecoin.consts.CoinEnum;
import com.leichuang.tradecoin.entity.bo.BinanceMarketKlineResult;
import com.leichuang.tradecoin.entity.bo.MarketBO;
import com.leichuang.tradecoin.service.MarketService;
import com.leichuang.tradecoin.utils.ModelTransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

// 交易行情信息缓存
@Slf4j
@Component
public class TickerCache {

    @Autowired
    private MarketService marketService;

    // 本地缓存，今日开盘价，key的格式 huobi:ht  binance:btc
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
            String tickerResultStr = null;
            do {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tickerResultStr = marketService.getHuobiKline(CoinConsts.HUOBI_PERIOD_ONE_DAY, CoinConsts.LIMIT_1, coinEnum.getSymbol() + CoinConsts.USDT_SYMBOL);
            } while (tickerResultStr == null);
            MarketBO marketBO = ModelTransfer.transMarketBO(tickerResultStr);
            return marketBO.getOpen();
        }
        else if (exchange.contains(CoinConsts.BINANCE)) {
            String tickerResultStr=null;
            do {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tickerResultStr = marketService.getBinanceKline((coinEnum.getSymbol() + CoinConsts.USDT_SYMBOL).toUpperCase(), CoinConsts.BINANCE_INTERVAL_ONE_DAY, null, null, CoinConsts.LIMIT_1);
            } while (tickerResultStr == null);
            BinanceMarketKlineResult tickerResult = ModelTransfer.transBinanceMarketKlineResult(tickerResultStr);
            return tickerResult.getOpen();
        }
        return null;
    }

    public BigDecimal getOpen(String key) {
        return OPEN_PRICE_CACHE.get(key);
    }

}
