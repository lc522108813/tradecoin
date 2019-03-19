package com.coins.tradecoin.controller;


import com.coins.tradecoin.entity.Result;
import com.coins.tradecoin.service.MainRobotService;
import com.coins.tradecoin.service.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/robot")
public class RobotController {

    @Autowired
    private MainRobotService mainRobotService;

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "开启交易策略",notes = "开启机器人交易策略")
    @GetMapping("/start")
    public Result start(){
        mainRobotService.startRobot();
        return Result.buildSuccess();
    }

}
