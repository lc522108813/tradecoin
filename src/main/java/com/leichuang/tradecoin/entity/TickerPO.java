package com.leichuang.tradecoin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class TickerPO implements Serializable {

    private static final long serialVersionUID = -1L;

    private BigDecimal open;	//开盘价

    private BigDecimal vol;		//成交量

    private String symbol;		//类型

    private BigDecimal last;	//当前价

    private BigDecimal buy;		//买1

    private BigDecimal sell;	//卖1

    private BigDecimal high;	//最高价

    private BigDecimal low;		//最低价

    private Long time;			//时间
}
