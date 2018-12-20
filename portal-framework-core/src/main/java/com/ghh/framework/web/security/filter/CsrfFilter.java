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
 * CSRF攻击过滤器,处理CSRF攻击
 *
 * @author ghh
 * @date 2018年12月19日下午10:54:16
 * @since v1.0.1
 ****************************************************************/
public class CsrfFilter implements Filter {

	private String referer;

	public CsrfFilter() {

	}

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// HTTP请求头来源校验,如果来源不合法就抛异常出去
		HttpServletRequest req = (HttpServletRequest) request;
		if (StringUtils.isEmpty(referer)) {
			chain.doFilter(request, response);
			return;
		}
		String refererHeader = req.getHeader("Referer");
		if (StringUtils.isEmpty(refererHeader)) {
			chain.doFilter(request, response);
			return;
		}

		String[] refererHeaders = referer.split(",");
		for (String header : refererHeaders) {
			// 判断 Referer 是否以配置的referer值开头
			if ((!StringUtils.isEmpty(refererHeader)) && (refererHeader.trim().startsWith(header))) {
				chain.doFilter(request, response);
				return;
			}
		}
		throw new RuntimeException("HTTP请求来源不合法!");
	}

	public void init(FilterConfig fConfig) throws ServletException {
		this.referer = fConfig.getInitParameter("referer");
	}

}
