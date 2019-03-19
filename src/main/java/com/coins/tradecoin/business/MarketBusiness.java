package com.coins.tradecoin.business;

import com.coins.tradecoin.entity.dto.CoinPriceDTO;
import com.coins.tradecoin.service.MarketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MarketBusiness {

    @Autowired
    private MarketService marketService;


    /** 获取入参所选货币的当前价格 **/
    public List<CoinPriceDTO> getCoinPriceByCoins(List<String> coins) {
        List<CoinPriceDTO> list=new ArrayList<>();
        for (String coin : coins) {
            String period = "1min";
            String coinSymbol = coin.toLowerCase() + "usdt";
//            String result = marketService.getHuobiKline(period, 1, coinSymbol);
//            HuobiMarketResult marketResult = JSONObject.parseObject(result, HuobiMarketResult.class);
//            List<MarketBO> data = marketResult.getData();
//            if (data != null && data.size() != 0) {
//                MarketBO freshMarketBO = data.get(0);
//                System.out.println(String.format("货币%s，当前价格 %s", coin, freshMarketBO.getClose()));
//                CoinPriceDTO coinPriceDTO=CoinPriceDTO.builder()
//                        .coinName(coin)
//                        .price(freshMarketBO.getClose())
//                        .time(marketResult.getTs())
//                        .build();
//                list.add(coinPriceDTO);
//            }

        }
        return list;
    }

}
