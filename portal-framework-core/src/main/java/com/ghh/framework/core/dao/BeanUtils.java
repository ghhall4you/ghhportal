package com.ghh.framework.core.dao;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/*****************************************************************
 *
 * 普通类获取spring中bean的工具类
 *
 * @author ghh
 * @date 2018年12月19日下午10:26:33
 * @since v1.0.1
 ****************************************************************/
@Component
public class BeanUtils implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (BeanUtils.applicationContext == null) {
			BeanUtils.applicationContext = applicationContext;
		}
	}

	/**
	 * 获取applicationContext
	 * 
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 通过name获取 Bean.
	 * 
	 * @param name
	 * @return
	 */
	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}

	/**
	 * 通过class获取Bean.
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}

	/**
	 * 通过name,以及Clazz返回指定的Bean
	 * 
	 * @param name
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(String name, Class<T> clazz) {
		return getApplicationContext().getBean(name, clazz);
	}
}
