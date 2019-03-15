package com.leichuang.tradecoin.exception;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class ExceptionHandlerAspect {


    @Around("execution(public * com.leichuang.tradecoin.business.*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String simpleClassName = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();
        String[] paramNames=new LocalVariableTableParameterNameDiscoverer().getParameterNames(method);
        Object[] args = joinPoint.getArgs();
        log.info("call {}.{} paramNames:{},args:{}",simpleClassName, methodName, paramNames,args);
        try {
            Object result = joinPoint.proceed();
            log.info("call success,  {}.{} , result：{}",simpleClassName, methodName, result);
            return result;
        }catch (Throwable e) {
            log.info("error occurred， {}.{}, error：{}", simpleClassName, methodName, e);
            throw e;
        }
    }

}
