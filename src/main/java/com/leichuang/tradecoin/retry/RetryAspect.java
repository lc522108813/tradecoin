package com.leichuang.tradecoin.retry;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @Description: 切面，处理需要重试的业务
 * @Auther: leichuang
 * @Date: 2019/2/25
 */
@Slf4j
@Aspect
@Component
public class RetryAspect {

    /** 线程池 **/
    ExecutorService executorService=new ThreadPoolExecutor(3,5,1,TimeUnit.SECONDS,new LinkedBlockingQueue<>());


    @Around(value="@annotation(retryDot)")
    public Object execute(ProceedingJoinPoint joinPoint, RetryDot retryDot) throws Exception{
        RetryTemplate retryTemplate=new RetryTemplate() {
            @Override
            protected Object doBiz() throws Throwable {
                return joinPoint.proceed();
            }
        };
        retryTemplate.setRetryTime(retryDot.count());
        retryTemplate.setSleepTime(retryDot.sleep());
        if(retryDot.asyn()){

            return retryTemplate.submit(executorService);
        }
        else {
            return retryTemplate.execute();
        }
    }

}
