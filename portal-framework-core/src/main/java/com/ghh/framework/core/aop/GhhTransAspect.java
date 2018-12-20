package com.ghh.framework.core.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ghh.framework.GhhException;

/*****************************************************************
 *
 * 事务切面
 *
 * @author ghh
 * @date 2018年12月19日下午10:21:54
 * @since v1.0.1
 ****************************************************************/
@Component
@Aspect
@Order(2)
public class GhhTransAspect {
	
    private static final Logger logger = Logger.getLogger(GhhTransAspect.class);
	@Pointcut("@annotation(com.ghh.framework.core.annotation.GhhTransaction)")
	public void transPointcut() {
		
	}
	
	@Around("transPointcut()")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public Object aroundTransPointcut(ProceedingJoinPoint joinPoint) throws GhhException{
		Object result = null;
		String methodName = joinPoint.getSignature().getName();
		try {
			//前置通知
			logger.info("事务前置通知：" + "The method " + methodName + " begins with ....");
			//执行目标方法
			result = joinPoint.proceed();
			//返回通知
			logger.info("事务返回通知：" + "The method " + methodName + " ends with " + result);
		} catch (Throwable e) {
			//异常通知
			logger.info("事务异常通知：" + "The method " + methodName + " occurs exception:" + e);
			throw new RuntimeException(e);
		}
		return result;
	}
}
