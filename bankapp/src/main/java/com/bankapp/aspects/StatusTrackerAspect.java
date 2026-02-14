package com.bankapp.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bankapp.annotations.LogStatus;

import lombok.AllArgsConstructor;

@Component
@Aspect
@AllArgsConstructor
public class StatusTrackerAspect {

    private static final Logger statusLogger = LoggerFactory.getLogger(StatusTrackerAspect.class);

    @Around("@annotation(logStatus)")
    public Object trackStatus(ProceedingJoinPoint point, LogStatus logStatus) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        String methodName = signature.getDeclaringType().getSimpleName() + "." + signature.getMethod().getName();

        try {
            Object result = point.proceed();
            statusLogger.info("Method {} executed SUCCESS", methodName);
            return result;
        } catch (Throwable t) {
            statusLogger.error("Method {} executed FAILURE: {}", methodName, t.toString());
            throw t;
        }
    }
}
