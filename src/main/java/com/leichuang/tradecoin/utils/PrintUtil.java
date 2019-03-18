package com.leichuang.tradecoin.utils;

import com.leichuang.tradecoin.entity.bo.PrintMarketTickerBO;

import java.math.BigDecimal;

public class PrintUtil {

    private final static BigDecimal exchangeRate = new BigDecimal(6.68);
    private final static BigDecimal smallNumber = new BigDecimal(20);
    private final static BigDecimal bigNumber = new BigDecimal(1000);


    public static String printMarketResult(BigDecimal close, BigDecimal volume, String coin, BigDecimal gains, BigDecimal ma10) {
        PrintMarketTickerBO bo = PrintMarketTickerBO.builder()
                .close(close)
                .volume(volume)
                .coin(coin)
                .gains(gains)
                .ma10(ma10)
                .build();
        return getPrintMarketKline(bo);
    }

    public static String getPrintMarketKline(PrintMarketTickerBO bo) {
        StringBuilder strBuffer = new StringBuilder();
        if (bo.getGains().compareTo(new BigDecimal(99)) >= 0 || bo.getGains().compareTo(new BigDecimal(-99)) <= 0) {
            bo.setGains(new BigDecimal(0));
        }
        strBuffer.append(String.format(" vl %4.0fk", bo.getVolume().divide(bigNumber, 5)));
        strBuffer.append(String.format(" %4.2f", bo.getGains()));
        if (bo.getClose().multiply(exchangeRate).compareTo(bigNumber) >= 0) {
            strBuffer.insert(0, String.format(" cl %6.1f", bo.getClose())).append(String.format(" ma10 %4.1f", bo.getMa10()));
        }
        else if (bo.getClose().multiply(exchangeRate).compareTo(smallNumber) < 0) {
            strBuffer.insert(0, String.format(" cl %6.3f", bo.getClose())).append(String.format(" ma10 %4.3f", bo.getMa10()));
        }
        else {
            strBuffer.insert(0, String.format(" cl %6.2f", bo.getClose())).append(String.format(" ma10 %4.2f", bo.getMa10()));
        }
        return strBuffer.toString();
    }

}
