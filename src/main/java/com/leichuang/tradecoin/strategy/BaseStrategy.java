package com.leichuang.tradecoin.strategy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class BaseStrategy {

    /** 货币单位 **/
    private int coinType;

    /** 交易类型   **/
    private int tranMode;

    /** 盈利抛售点 **/
    private BigDecimal stopProfitPrice;
    /** 止损抛售点 **/
    private BigDecimal stopLossPrice;

    /** 看涨入场点 **/
    private BigDecimal upStepInPrice;
    /** 看跌入场点 **/
    private BigDecimal downStepInPrice;

    /** 交易量 **/
    private BigDecimal transCount;


}
