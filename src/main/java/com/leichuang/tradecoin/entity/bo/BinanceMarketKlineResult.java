package com.leichuang.tradecoin.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BinanceMarketKlineResult implements Serializable {
    private static final long serialVersionUID = 7857148989410743569L;

    private Long openTime;

    /** 开盘价 **/
    private BigDecimal open;

    /** 最高价 **/
    private BigDecimal high;

    /** 最低价 **/
    private BigDecimal low;

    /** 收盘价 **/
    private BigDecimal close;

    // 交易量
    private BigDecimal volume;

    private Long closeTime;

    // 引用资产规模
    private BigDecimal quoteAssetVolume;

    private Integer tradeNumber;

    // 买家买入规模
    private BigDecimal buyBaseAssetVolume;

    // 买家买入引用资产规模
    private BigDecimal buyquoteAssetVolume;

}
