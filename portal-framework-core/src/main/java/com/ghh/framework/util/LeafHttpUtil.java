package com.ghh.framework.util;

import javax.servlet.http.HttpServletRequest;

/*****************************************************************
 *
 * LeafHttpUtil
 *
 * @author ghh
 * @date 2018年12月19日下午10:42:47
 * @since v1.0.1
 ****************************************************************/
public class LeafHttpUtil {
	public static String calcReqFunction(HttpServletRequest paramHttpServletRequest) {
		String str1 = paramHttpServletRequest.getParameter("method");
		String str2 = paramHttpServletRequest.getServletPath();
		StringBuffer localStringBuffer = new StringBuffer(str2);
		if (str1 == null) {
			String str3 = paramHttpServletRequest.getParameter("prefix");
			String str4 = paramHttpServletRequest.getParameter("page");
			if ((str3 != null) && (str4 != null))
				localStringBuffer.append("?prefix=").append(str3).append("&page=").append(str4);
		} else {
			localStringBuffer.append("?method=").append(str1);
		}
		String str3 = paramHttpServletRequest.getParameter("menuId");
		if (str3 != null)
			localStringBuffer.append("&menuId=").append(str3);
		if (localStringBuffer.indexOf("?") < 0) {
			int i = localStringBuffer.indexOf("&");
			if (i >= 0)
				localStringBuffer.replace(i, i + 1, "?");
		}
		String str5 = localStringBuffer.toString();
		return str5;
	}

	public static String getRequestResource(HttpServletRequest paramHttpServletRequest) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append(paramHttpServletRequest.getServletPath());
		String str = paramHttpServletRequest.getQueryString();
		if (str != null)
			localStringBuffer.append("?").append(str);
		return localStringBuffer.toString();
	}
}
