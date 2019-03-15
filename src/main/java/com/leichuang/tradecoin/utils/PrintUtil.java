package com.leichuang.tradecoin.utils;

import com.leichuang.tradecoin.entity.bo.BinanceMarketKlineResult;
import com.leichuang.tradecoin.entity.bo.HuobiMarketResult;
import com.leichuang.tradecoin.entity.bo.MarketBO;

import java.math.BigDecimal;
import java.util.List;

public class PrintUtil {

    public final static BigDecimal exchangeRate = new BigDecimal(6.68);
    public final static BigDecimal smallNumber = new BigDecimal(20);
    public final static BigDecimal bigNumber = new BigDecimal(1000);


    public static String printHuobiMarketResult(MarketBO marketBO, BigDecimal volume, String coin, BigDecimal gains) {
        /** 获取时间并打印 **/
        return coin +
                getPrintStr(marketBO.getClose(), volume, gains);
    }

    public static String printBinanceMarketKlineResult(BinanceMarketKlineResult marketKlineResult, BigDecimal volume, String coin, BigDecimal gains) {
        /** 获取时间并打印 **/
        return coin +
                getPrintStr(marketKlineResult.getClose(), volume, gains);
    }

    public static String getPrintStr(BigDecimal close, BigDecimal volume, BigDecimal gains) {
        StringBuilder strBuffer = new StringBuilder();
        if (gains.compareTo(new BigDecimal(99)) >= 0 || gains.compareTo(new BigDecimal(-99)) <= 0) {
            gains = new BigDecimal(0);
        }
        strBuffer.append(String.format(" vl %4.0fk", volume.divide(bigNumber, 5)));
        strBuffer.append(String.format(" %4.2f",gains ));
        if (close.compareTo(bigNumber) >= 0) {
            strBuffer.insert(0, String.format(" cl %6.1f", close)).append(String.format(" cn %4.0f", close.multiply(exchangeRate)));
        }
        else if (close.compareTo(smallNumber) < 0) {
            strBuffer.insert(0, String.format(" cl %6.3f", close)).append(String.format(" cn %4.1f", close.multiply(exchangeRate)));
        }
        else {
            strBuffer.insert(0, String.format(" cl %6.2f", close)).append(String.format(" cn %4.1f", close.multiply(exchangeRate)));
        }
        return strBuffer.toString();
    }

}
