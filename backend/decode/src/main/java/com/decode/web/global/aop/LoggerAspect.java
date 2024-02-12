package com.decode.web.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggerAspect {

    @Pointcut("execution(* com.decode.web.domain.*.controller..*.*(..))")
    private void controllerLogger() {
    }

    @Before("controllerLogger()")
    public void before(JoinPoint joinPoint) {
        log.info("[Before] method name : {}", joinPoint.getSignature().getName());
        log.info("[Arguments] : {}", joinPoint.getArgs());
    }

    @AfterReturning("controllerLogger()")
    public void afterReturning(JoinPoint joinPoint) {
        log.info("[AfterReturning] method name : {}", joinPoint.getSignature().getName());
        log.info("[Arguments] : {}", joinPoint.getArgs());
    }

    @AfterThrowing("controllerLogger()")
    public void afterThrowing(JoinPoint joinPoint) {
        log.info("[AfterThrowing] method name : {}", joinPoint.getSignature().getName());
        log.info("[Arguments] : {}", joinPoint.getArgs());
    }

}
