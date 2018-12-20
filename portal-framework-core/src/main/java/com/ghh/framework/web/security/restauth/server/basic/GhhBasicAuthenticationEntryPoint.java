package com.ghh.framework.web.security.restauth.server.basic;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

/*****************************************************************
 *
 * 基本认证入口扩展类
 *
 * @author ghh
 * @date 2018年12月19日下午10:57:07
 * @since v1.0.1
 ****************************************************************/
@Deprecated
public class GhhBasicAuthenticationEntryPoint extends
		BasicAuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authEx)
			throws IOException, ServletException {
		response.addHeader("WWW-Authenticate", "Basic realm=\""
				+ getRealmName() + "\"");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		PrintWriter writer = response.getWriter();
		writer.println("HTTP Status 401 - " + authEx.getMessage());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		setRealmName("Basic Auth");
		super.afterPropertiesSet();
	}
}