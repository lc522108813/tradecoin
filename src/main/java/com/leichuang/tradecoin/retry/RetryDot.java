package com.leichuang.tradecoin.retry;

import java.lang.annotation.*;

/** 重试机制注解 **/
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RetryDot {
    /** 重试次数 **/
    int count() default 0;

    /** 重试时间间隔 **/
    int sleep() default 0;

    /** 是否支持异步重试 **/
    boolean asyn() default false;
}
