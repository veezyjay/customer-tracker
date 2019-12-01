package com.tracker.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {
	
	// setup logger
	private Logger myLogger = Logger.getLogger(getClass().getName());
	
	// setup pointcut declarations
	@Pointcut("execution(* com.tracker.controller.*.*(..))")
	private void forControllerPackage() {}
	
	@Pointcut("execution(* com.tracker.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* com.tracker.dao.*.*(..))")
	private void forDaoPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	private void forAppFlow() {}
	
	// add @Before advice
	@Before("forAppFlow()")
	public void before(JoinPoint theJoinPoint) {
		// display the method being called
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("====>>> in @Before - calling method: " + theMethod);
		
		// display the arguments to the method
		Object[] args = theJoinPoint.getArgs();
		for (Object tempArg : args) {
			myLogger.info("====>>> argument: " + tempArg);
		}
	}
	
	// add @AferReturning advice
	
}
