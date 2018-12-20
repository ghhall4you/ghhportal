package com.ghh.framework.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/*****************************************************************
 *
 * 上下文工具类
 *
 * @author ghh
 * @date 2018年12月19日下午10:38:52
 * @since v1.0.1
 ****************************************************************/
public class ContextHolderUtils {
	/**
	 * SpringMvc下获取request
	 * 
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request;

	}

	/**
	 * SpringMvc下获取session
	 * 
	 * @return
	 */
	public static HttpSession getSession() {
		HttpSession session = getRequest().getSession();
		return session;

	}

}
