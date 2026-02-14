package com.bankapp.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bankapp.annotations.PerformanceTracker;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.AllArgsConstructor;

@Component
@Aspect
@AllArgsConstructor
public class PerformanceTrackerAspect {
	private static final Logger logger = LoggerFactory.getLogger(PerformanceTrackerAspect.class);
	private final ObservationRegistry observationRegistry;

	@Around("@annotation(performanceTracker)")
	public Object around(ProceedingJoinPoint point, PerformanceTracker performanceTracker) throws Throwable {

	    MethodSignature signature = (MethodSignature) point.getSignature();
	    String methodName = signature.getDeclaringType().getSimpleName() + "." + signature.getMethod().getName();

	    return Observation.createNotStarted("method.performance", observationRegistry)
	            .lowCardinalityKeyValue("method", methodName)
	            .observeChecked(() -> {
	                long start = System.currentTimeMillis();
	                try {
	                    Object result = point.proceed();
	                    long duration = System.currentTimeMillis() - start;

	                    logger.info("Method {} executed successfully in {} ms", methodName, duration);

	                    return result;
	                } catch (Throwable t) {
	                    long duration = System.currentTimeMillis() - start;

	                    logger.error("Method {} failed in {} ms with exception: {}", methodName, duration, t.toString());

	                    throw t;
	                }
	            });
	}
}