package com.coins.tradecoin.controller;

import com.coins.tradecoin.business.MarketBusiness;
import com.coins.tradecoin.entity.Result;
import com.coins.tradecoin.entity.dto.CoinPriceDTO;
import com.coins.tradecoin.entity.param.GetCoinPriceParam;
import com.coins.tradecoin.validate.ValidatorUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/market")
public class MarketController {

    @Autowired
    private MarketBusiness marketBusiness;

    @ApiOperation(value = "test",notes = "")
    @GetMapping(value = "/test")
    public String getTest(){
        return "success";
    }


    @ApiOperation(value = "获取市场价格接口",notes = "传入货币，根据货币获取到当前货币价格")
    @GetMapping("/getPrice")
    public Result<List<CoinPriceDTO>> getCoinPrice(GetCoinPriceParam param){
        /** 参数校验 **/
        ValidatorUtil.validate(param);
        List<CoinPriceDTO> list=marketBusiness.getCoinPriceByCoins(param.getCoins());
        return Result.buildSuccess(list);
    }

}
