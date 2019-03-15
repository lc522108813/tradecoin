package com.leichuang.tradecoin.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HuobiMarketResult implements Serializable {
    private static final long serialVersionUID = -9135521190490666254L;

    /** 返回状态 **/
    private String status;
    /** 返回时间间隔 **/
    private String ch;
    /** timestamp **/
    private Long ts;

    /** K线数据 **/
    List<MarketBO> data;


}
