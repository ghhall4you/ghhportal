package com.ghh.framework.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/*****************************************************************
 *
 * 获取请求参数公共类
 *
 * @author ghh
 * @date 2018年12月19日下午10:45:58
 * @since v1.0.1
 ****************************************************************/
public class RequestUtil {
	private static Logger logger = Logger.getLogger(RequestUtil.class);

	public static HttpServletRequest getRequest() {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		if (null != servletRequestAttributes) {
			return servletRequestAttributes.getRequest();
		} else {
			return null;
		}
	}

	public static HttpSession getHttpSession() {
		logger.info("#####start to get http session ####");
		HttpServletRequest request = RequestUtil.getRequest();
		if (null == request) {
			logger.info("current http servlet request is null~");
			return null;
		} else {
			logger.info("current http servlet request is ####" + request);
			return request.getSession();
		}
	}

	public static String getRequestUrl() {
		logger.info("#####start to get request url ####");
		HttpServletRequest request = RequestUtil.getRequest();
		if (null == request) {
			logger.info("request url is null~");
			return null;
		} else {
			logger.info("request url is ####" + request);
			return request.getRequestURL().toString();
		}
	}

	public static String getRequestRemoteAddr() {
		logger.info("#####start to get request remote address ####");
		HttpServletRequest request = RequestUtil.getRequest();
		if (null == request) {
			logger.info("request remote address is null~");
			return null;
		} else {
			logger.info("request remote address is ####" + request);
			return request.getRemoteAddr();
		}
	}

	public static String getCurrentSessionId() {

		HttpSession currentSession = RequestUtil.getHttpSession();
		if (null == currentSession) {
			logger.info("current http session is null~~~");
			return null;
		} else {
			logger.info("current current session is ####" + currentSession);
			return currentSession.getId();
		}
	}

	private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}

	/**
	 * 通过名称获取cookie
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static Cookie getCookieByName(String key) {

		Map<String, Cookie> cookieMap = readCookieMap(getRequest());
		if (cookieMap.containsKey(key)) {
			return cookieMap.get(key);
		} else {
			logger.info("can not find cookie name: " + key);
			return null;
		}
	}

	/**
	 * 通过名称获取header中的值
	 * 
	 * @param key
	 * @return
	 */
	public static String getHeaderByName(String key) {
		return getRequest().getHeader(key);
	}

}