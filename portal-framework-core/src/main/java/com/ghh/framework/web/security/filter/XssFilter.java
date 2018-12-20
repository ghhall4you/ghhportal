package com.ghh.framework.web.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

/*****************************************************************
 *
 * XSS攻击过滤器,处理SQL注入和XSS攻击
 *
 * @author ghh
 * @date 2018年12月19日下午10:54:55
 * @since v1.0.1
 ****************************************************************/
public class XssFilter implements Filter {

	private String xxsWrapper;
	private String filterChar;
	private String replaceChar;
	private String splitChar;
	FilterConfig filterConfig = null;

	public void destroy() {
		this.filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		/*
		 * 这里根据web.xml里配置的参数选择使用哪个包装类
		 */
		if (!StringUtils.isEmpty(xxsWrapper) && xxsWrapper.equals("1")) {
			chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);
			return;
		}
		if (!StringUtils.isEmpty(xxsWrapper) && xxsWrapper.equals("2")) {
			chain.doFilter(
					new XssHttpServletRequestWrapper2((HttpServletRequest) request, filterChar, replaceChar, splitChar),
					response);
			return;
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.xxsWrapper = filterConfig.getInitParameter("xxsWrapper");
		this.filterChar = filterConfig.getInitParameter("filterChar");
		this.replaceChar = filterConfig.getInitParameter("replaceChar");
		this.splitChar = filterConfig.getInitParameter("splitChar");
		this.filterConfig = filterConfig;
	}

}
