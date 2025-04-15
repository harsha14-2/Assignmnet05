package com.a5.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging method entries, exits, and exceptions across the application.
 * Uses Spring AOP to intercept method calls and add logging without modifying the core code.
 */
@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Logs entry, exit, and exceptions for controller methods.
     * Uses INFO level for controller logging to capture API usage patterns.
     * 
     * @param joinPoint The join point representing the intercepted method
     * @return The result of the method execution
     * @throws Throwable If the intercepted method throws an exception
     */
    @Around("execution(* com.a5.controller.*.*(..))")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        logger.info("Entering method {} of class {}", methodName, className);
        
        try {
            Object result = joinPoint.proceed();
            logger.info("Exiting method {} of class {} with result: {}", methodName, className, result);
            return result;
        } catch (Exception e) {
            logger.error("Exception in method {} of class {}: {}", methodName, className, e.getMessage());
            throw e;
        }
    }

    /**
     * Logs entry, exit, and exceptions for service methods.
     * Uses DEBUG level for service logging to reduce log volume in production
     * while still providing detailed information for troubleshooting.
     * 
     * @param joinPoint The join point representing the intercepted method
     * @return The result of the method execution
     * @throws Throwable If the intercepted method throws an exception
     */
    @Around("execution(* com.a5.service.*.*(..))")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        logger.debug("Entering service method {} of class {}", methodName, className);
        
        try {
            Object result = joinPoint.proceed();
            logger.debug("Exiting service method {} of class {}", methodName, className);
            return result;
        } catch (Exception e) {
            logger.error("Service exception in method {} of class {}: {}", methodName, className, e.getMessage());
            throw e;
        }
    }
} 