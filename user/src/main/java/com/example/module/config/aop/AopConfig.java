package com.example.module.config.aop;

import com.example.module.annotation.CommonLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AopConfig {
    // 패키지 경로 설정: com.example.module..service.*.*(..)
    @Pointcut("execution(* com.example.module..service.*.*(..)) && @annotation(commonLog)")
    public void servicePointcut(CommonLog commonLog) {}

    @AfterReturning(value = "servicePointcut(commonLog)", returning = "result", argNames = "joinPoint,commonLog,result")
    public void logServiceMethod(JoinPoint joinPoint, CommonLog commonLog, Object result) {
        log.debug("====================================");
        log.debug("Service method executed: " + joinPoint.getSignature());
        log.debug("CommonLog annotation value - title: " + commonLog.title() + ", commonAction: " + commonLog.commonAction());
        log.debug("Result: " + result);
        log.debug("====================================");
    }
}
