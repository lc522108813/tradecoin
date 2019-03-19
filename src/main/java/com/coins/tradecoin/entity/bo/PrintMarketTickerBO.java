package com.coins.tradecoin.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/** 行情打印BO **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrintMarketTickerBO {

    private BigDecimal close;
    private BigDecimal volume;
    private BigDecimal gains;
    private String coin;
    /** 十日移动平均线 **/
    private BigDecimal ma10;

}
