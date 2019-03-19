package com.coins.tradecoin.entity.dto;

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
public class CoinPriceDTO implements Serializable {
    private static final long serialVersionUID = -1L;

    private String coinName;

    /** 当前成交价 **/
    private BigDecimal price;

    /** 时间戳 **/
    private Long time;


}
