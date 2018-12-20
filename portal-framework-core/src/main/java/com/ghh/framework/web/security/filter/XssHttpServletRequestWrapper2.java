package com.ghh.framework.web.security.filter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.util.StringUtils;

/*****************************************************************
 *
 * 处理XSS攻击的请求包装类,将<>&"'#--从半角转成全角
 *
 * @author ghh
 * @date 2018年12月19日下午10:55:43
 * @since v1.0.1
 ****************************************************************/
public class XssHttpServletRequestWrapper2 extends HttpServletRequestWrapper {

	private String[] filterChars;
	private String[] replaceChars;

	public XssHttpServletRequestWrapper2(HttpServletRequest request, String filterChar, String replaceChar,
			String splitChar) {
		super(request);
		if ((filterChar != null) && (filterChar.length() > 0)) {
			filterChars = filterChar.split(splitChar);
		}
		if ((replaceChar != null) && (replaceChar.length() > 0)) {
			replaceChars = replaceChar.split(splitChar);
		}
	}

	/**
	 * 覆盖getHeader方法，将参数名和参数值都做xss过滤。<br/>
	 * 如果需要获得原始的值，则通过super.getHeaders(name)来获取<br/>
	 * getHeaderNames 也可能需要覆盖
	 */
	@Override
	public String getHeader(String name) {
		String value = super.getHeader(xssEncode(name));
		if (value != null) {
			value = xssEncode(value);
		}
		return value;
	}

	@Override
	public String getQueryString() {
		String value = super.getQueryString();
		if (value != null) {
			value = xssEncode(value);
		}
		return value;
	}

	/**
	 * 覆盖getParameter方法，将参数名和参数值都做xss过滤。<br/>
	 * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取<br/>
	 * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
	 */
	@Override
	public String getParameter(String name) {
		String value = super.getParameter(xssEncode(name));
		if (value != null) {
			value = xssEncode(value);
		}
		return value;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		// TODO Auto-generated method stub
		Map<String, String[]> parameterMap = super.getParameterMap();
		if ((parameterMap == null) || parameterMap.isEmpty()) {
			return null;
		}
		Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
		for (Entry<String, String[]> entry : entrySet) {
			if ("userlog".equals(entry.getKey())) {
				continue;
			}
			String[] parameters = entry.getValue();
			if ((parameters == null) || (parameters.length == 0)) {
				continue;
			}
			for (int i = 0; i < parameters.length; i++) {
				parameters[i] = xssEncode(parameters[i]);
			}
		}
		return parameterMap;
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] parameters = super.getParameterValues(name);
		if ((parameters == null) || (parameters.length == 0)) {
			return null;
		}
		for (int i = 0; i < parameters.length; i++) {
			if ("userlog".equals(name)) {
				continue;
			}
			parameters[i] = xssEncode(parameters[i]);
		}
		return parameters;
	}

	/**
	 * 将容易引起xss漏洞的半角字符直接替换成全角字符
	 * 
	 * @param s
	 * @return
	 */
	private String xssEncode(String s) {
		if (StringUtils.isEmpty(s)) {
			return s;
		}
		try {

			s = s.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
			s = s.replaceAll("\\+", "%2B");

			s = URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < filterChars.length; i++) {
			if (s.contains(filterChars[i])) {
				s = s.replace(filterChars[i], replaceChars[i]);
			}
		}
		return s;
	}
}
