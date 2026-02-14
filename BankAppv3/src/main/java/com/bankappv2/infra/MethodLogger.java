package com.bankappv2.infra;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MethodLogger {
	private static final Logger logger = LoggerFactory.getLogger(MethodLogger.class);

	@Around("@annotation(Loggable)")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long start = System.currentTimeMillis();
		Object result = point.proceed();
		logger.info("start " + point.getSignature().getName() + " is called"
				+ " takes " + (System.currentTimeMillis() - start));
		return result;
	}
}
