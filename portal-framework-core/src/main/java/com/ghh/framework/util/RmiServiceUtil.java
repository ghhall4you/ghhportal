package com.ghh.framework.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ghh.framework.web.system.cache.remote.rmi.IClientRMIService;

/*****************************************************************
 *
 * 远程方法调用服务辅助类
 *
 * @author ghh
 * @date 2018年12月19日下午10:47:24
 * @since v1.0.1
 ****************************************************************/
public class RmiServiceUtil {

	private static IClientRMIService clientService;

	static {
		@SuppressWarnings("resource")
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:conf/spring-mvc-rmi-cache.xml");
		clientService = (IClientRMIService) ctx.getBean("myClient");
	}

	public static IClientRMIService getClientRmiService() {
		return clientService;
	}

}
