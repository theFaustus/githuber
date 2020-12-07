package com.evil.inc.githuber.aop;

import com.evil.inc.githuber.service.LogShippingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingAspect {
    private final LogShippingService logShippingService;

    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.stereotype.Controller *)")
    public void springBeanPointcut() {
    }

    @Pointcut("(within(com.evil.inc.githuber..*)" +
            " || within(com.evil.inc.githuber.service..*)" +
            " || within(com.evil.inc.githuber.web..*)) " +
            " && !execution(* com.evil.inc.githuber.service.LogShippingService.*(..))" +
            " && !execution(* com.evil.inc.githuber.service.LogShippingConsumerService.*(..)) ")
    public void applicationPackagePointcut() {
    }

    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        String loggingMessage = String.format("Exception in %s.%s() with cause = %s", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL");
        logShippingService.sendLog(loggingMessage);
        log.error(loggingMessage);
    }

    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        if (log.isDebugEnabled()) {
            String loggingMessage = String.format("Enter: %s.%s() with argument[s] = %s", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
            logShippingService.sendLog(loggingMessage);
            log.debug(loggingMessage);
        }
        try {
            Object result = joinPoint.proceed();
            if (log.isDebugEnabled()) {
                String loggingMessage = String.format("Exit: %s.%s() with result = %s", joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(), result);
                logShippingService.sendLog(loggingMessage);
                log.debug(loggingMessage);
            }
            return result;
        } catch (IllegalArgumentException e) {
            String loggingMessage = String.format("Illegal argument: %s in %s.%s()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            logShippingService.sendLog(loggingMessage);
            log.error(loggingMessage);
            throw e;
        }
    }
}

