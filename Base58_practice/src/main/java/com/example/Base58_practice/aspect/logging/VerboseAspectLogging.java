package com.example.Base58_practice.aspect.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class VerboseAspectLogging {
    @Before("@annotation(com.example.Base58_practice.aspect.logging.annotation.VerboseLogging)")
    public void logEnter(final JoinPoint joinPoint) throws Throwable {
        log.debug("ENTER: {}:{} <--- ARGS: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                joinPoint.getArgs());
    }

    @AfterReturning(value = "@annotation(com.example.Base58_practice.aspect.logging.annotation.VerboseLogging)", returning = "returnValue")
    public void logExit(final JoinPoint joinPoint, Object returnValue) throws Throwable {
        log.debug("EXIT: {}:{} ---> RETURN: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                returnValue);
    }

    @AfterThrowing(value = "@annotation(com.example.Base58_practice.aspect.logging.annotation.VerboseLogging)", throwing = "e")
    public void logException(final JoinPoint joinPoint, Exception e) {
        log.error("EXCEPTION: {}:{} ---> {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                e.getMessage());
    }
}
