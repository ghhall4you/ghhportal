package com.ghh.framework.core.aop;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ghh.framework.GhhException;
import com.ghh.framework.core.annotation.GhhLogType;
import com.ghh.framework.core.annotation.OpLog;
import com.ghh.framework.core.oplog.OpLogManager;

/*****************************************************************
 *
 * 操作日志切面
 *
 * @author ghh
 * @date 2018年12月19日下午10:23:06
 * @since v1.0.1
 ****************************************************************/
@Component
@Aspect
@Order(3)
public class OpLogAspect {

	private static final Logger logger = Logger.getLogger(OpLogAspect.class);

	@Autowired
	private OpLogManager oplog;

	@Pointcut("@annotation(com.ghh.framework.core.annotation.OpLog)")
	public void opLogPointcut() {

	}

	@Around("opLogPointcut()")
	public Object aroundOpLogPointcut(ProceedingJoinPoint joinPoint) throws GhhException {
		Object result = null;
		String methodName = joinPoint.getSignature().getName();
		String userlog = null;
		try {
			// 前置通知
			logger.info("日志前置通知：" + "The method " + methodName + " begins with ....");// Arrays.asList(joinPoint.getArgs())
			Signature signature = joinPoint.getSignature();
			MethodSignature methodSignature = (MethodSignature) signature;
			Method targetMethod = methodSignature.getMethod();
			String[] args = new LocalVariableTableParameterNameDiscoverer().getParameterNames(targetMethod);
			for (int i = 0; i < joinPoint.getArgs().length; i++) {
				if ((joinPoint.getArgs()[i] instanceof String) && !StringUtils.isEmpty(joinPoint.getArgs()[i])
						&& (args.length == joinPoint.getArgs().length)) {
					if (args[i].equals("userlog")) {
						userlog = joinPoint.getArgs()[i].toString();
						break;
					}
				}
			}
			// 执行目标方法
			result = joinPoint.proceed();
			// 返回通知
			logger.info("日志返回通知：" + "The method " + methodName + " ends with " + result);

			OpLog log = targetMethod.getAnnotation(OpLog.class);
			if (log != null) {
				// System.out.println(log.value() +"--"+ log.digest()
				// +"---"+log.moduleName());
				if (log.value().equals(GhhLogType.RECORD)) {
					oplog.saveBizLog(log.digest(), log.moduleName());
				}
				if (log.value().equals(GhhLogType.PICTURE)) {
					if (userlog == null) {
						// System.out.println(userlog);
						throw new GhhException("操作日志摘要为空，或者模块未配置记录日志，请确认！");
					}
					oplog.saveOpLog(userlog);
				}
			}

		} catch (Throwable e) {
			// 异常通知
			logger.info("日志异常通知：" + "The method " + methodName + " occurs exception:" + e);
			throw new RuntimeException(e);
		}
		// 后置通知--进行日志图片的抓取
		logger.info("日志后置通知：" + "The method " + methodName + " ends 然后进行日志图片的抓取");
		return result;
	}

}
