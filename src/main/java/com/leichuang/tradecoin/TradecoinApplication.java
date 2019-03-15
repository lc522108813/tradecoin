package com.leichuang.tradecoin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.leichuang")
public class TradecoinApplication {
    public static void main(String[] args) {
        SpringApplication.run(TradecoinApplication.class, args);
    }
}

