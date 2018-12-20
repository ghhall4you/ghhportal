package com.ghh.framework.web.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*****************************************************************
 *
 * Cookie过滤器，设置HttpOnly，Secure，Expire属性
 *
 * @author ghh
 * @date 2018年12月19日下午10:53:57
 * @since v1.0.1
 ****************************************************************/
public class CookieFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpServletResponse resp = (HttpServletResponse) arg1;
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			Cookie cookie = cookies[0];
			if (cookie != null) {
				// Servlet 2.5不支持在Cookie上直接设置HttpOnly属性
				String value = cookie.getValue();
				StringBuilder builder = new StringBuilder();
				builder.append("JSESSIONID=" + value + "; ");
				// builder.append("Secure; ");
				builder.append("HttpOnly; ");
				/*
				 * Calendar cal = Calendar.getInstance(); cal.add(Calendar.HOUR,
				 * 1); Date date = cal.getTime(); Locale locale = Locale.CHINA;
				 * SimpleDateFormat sdf = new
				 * SimpleDateFormat("dd-MM-yyyy HH:mm:ss",locale);
				 * builder.append("Expires=" + sdf.format(date));
				 */
				resp.setHeader("Set-Cookie", builder.toString());
			}
		}
		chain.doFilter(req, resp);
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
