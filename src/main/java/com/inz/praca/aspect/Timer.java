package com.inz.praca.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.invoke.MethodHandles;

@Aspect
@Component
public class Timer {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Around("@annotation(com.inz.praca.aspect.Timed)")
    public Object logServiceAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object retVal = joinPoint.proceed();
        stopWatch.stop();
        logger.info("Completed {} within {} ms", joinPoint.getSignature().toShortString(), stopWatch.getTotalTimeMillis());
        return retVal;
    }
}
