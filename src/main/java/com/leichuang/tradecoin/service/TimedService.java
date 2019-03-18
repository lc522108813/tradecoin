package com.leichuang.tradecoin.service;

import com.leichuang.tradecoin.cache.TickerCache;
import com.leichuang.tradecoin.consts.CoinConsts;
import com.leichuang.tradecoin.consts.CoinEnum;
import com.leichuang.tradecoin.entity.bo.BinanceMarketKlineResult;
import com.leichuang.tradecoin.entity.bo.MarketBO;
import com.leichuang.tradecoin.utils.ModelTransfer;
import com.leichuang.tradecoin.utils.PrintUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;

import static com.leichuang.tradecoin.consts.CoinConsts.LIMIT_1;

@Service
@Slf4j
public class TimedService {
    @Autowired
    private MarketService marketService;

    @Autowired
    private TickerCache tickerCache;

    @Scheduled(fixedRate = 4000)
    public void reportCurrentPrice() throws InterruptedException {
        StringBuilder stringBuffer = new StringBuilder();
        // 获取当前价格 以及半个小时内的交易额
        for (CoinEnum coinEnum : CoinEnum.values()) {
            // 获取huobi的内容
            if (coinEnum.getMarket().equals(CoinConsts.HUOBI)) {
                String result = marketService.getHuobiKline(CoinConsts.HUOBI_PERIOD_ONE_MINUTE, LIMIT_1, coinEnum.getSymbol() + CoinConsts.USDT_SYMBOL);
                MarketBO marketBO = ModelTransfer.transMarketBO(result);
                // 半小时内交易额
                String dealResult = marketService.getHuobiKline(CoinConsts.HUOBI_PERIOD_HALF_HOUR, LIMIT_1, coinEnum.getSymbol() + CoinConsts.USDT_SYMBOL);
                MarketBO marketDealResult = ModelTransfer.transMarketBO(dealResult);
                BigDecimal volume = marketDealResult.getVol();
                // 当日涨跌幅
                BigDecimal open = tickerCache.getOpen(CoinConsts.HUOBI + ":" + coinEnum.getSymbol());
                BigDecimal gains;
                if (marketBO.getClose().compareTo(new BigDecimal(0)) == 0) {
                    gains = new BigDecimal(0);
                }
                else{
                    gains = marketBO.getClose().subtract(open).divide(open, 5).multiply(CoinConsts.BIG_DEC_100);
                }
                // 计算ma10
                HashMap<String,BigDecimal> map=tickerCache.getMA10(coinEnum.getSymbol());
                BigDecimal ma10=map.get(String.valueOf(0)).multiply(new BigDecimal(9)).add(marketBO.getClose()).divide(new BigDecimal(10),4);
                stringBuffer.append(PrintUtil.printMarketResult(marketBO.getClose(), volume, coinEnum.getDesc(), gains,ma10)).append("\n");
            }
            else if (coinEnum.getMarket().equals(CoinConsts.BINANCE)) {
                String binanceResult = marketService.getBinanceKline((coinEnum.getSymbol() + com.leichuang.tradecoin.consts.CoinConsts.USDT_SYMBOL).toUpperCase(), CoinConsts.BINANCE_INTERVAL_ONE_MINUETE, null, null, LIMIT_1);
                BinanceMarketKlineResult binanceMarketKlineResult = ModelTransfer.transBinanceMarketKlineResult(binanceResult);
                // 半小时内交易额
                String dealResult = marketService.getBinanceKline((coinEnum.getSymbol() + CoinConsts.USDT_SYMBOL).toUpperCase(), CoinConsts.BINANCE_INTERVAL_THIRTY_MINUTE, null, null, LIMIT_1);
                BinanceMarketKlineResult dealKlineResult = ModelTransfer.transBinanceMarketKlineResult(dealResult);
                BigDecimal volume = dealKlineResult.getVolume();
                // 当日涨跌幅
                BigDecimal open = tickerCache.getOpen(CoinConsts.BINANCE + ":" + coinEnum.getSymbol());
                BigDecimal gains;
                if (binanceMarketKlineResult.getClose().compareTo(new BigDecimal(0)) == 0) {
                    gains = new BigDecimal(0);
                }
                else{
                    gains = binanceMarketKlineResult.getClose().subtract(open).divide(open, 5).multiply(CoinConsts.BIG_DEC_100);
                }
                // 计算ma10
                HashMap<String,BigDecimal> map=tickerCache.getMA10(coinEnum.getSymbol());
                BigDecimal ma10=map.get(String.valueOf(0)).multiply(new BigDecimal(9)).add(binanceMarketKlineResult.getClose()).divide(new BigDecimal(10),4);
                stringBuffer.append(PrintUtil.printMarketResult(binanceMarketKlineResult.getClose(), volume, coinEnum.getSymbol(), gains,ma10)).append("\n");
            }
        }
        System.out.println(stringBuffer.toString());
    }

    @Scheduled(fixedRate = 3600_0000)
    public void calculateTradingStrategy(){
        //计算十日移动平均线


    }

}
