package com.coins.tradecoin.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/** K线行情 **/
@Data
@AllArgsConstructor
@Builder
public class MarketBO implements Serializable {
    private static final long serialVersionUID = -8915940758417518406L;
    /** 行情编号 **/
    private Long id;
    /** 开盘价 **/
    private BigDecimal open;
    /** 收盘价 **/
    private BigDecimal close;
    /** 最低价 **/
    private BigDecimal low;
    /** 最高价 **/
    private BigDecimal high;
    /** 成交量 **/
    private BigDecimal amount;
    /** 成交额 **/
    private BigDecimal vol;
    /** 成交笔数 **/
    private Integer count;

    public MarketBO(){
        this.open=new BigDecimal(0);
        this.close=new BigDecimal(0);
        this.low=new BigDecimal(0);
        this.high=new BigDecimal(0);
        this.amount=new BigDecimal(0);
        this.vol=new BigDecimal(0);
    }
}
