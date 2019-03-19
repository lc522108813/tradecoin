package com.coins.tradecoin.service;

import com.coins.tradecoin.strategy.BaseStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class MainRobotService {

    private static BaseStrategy strategy=null;

    public void startRobot(){

        log.info("start trading app");


    }

}
