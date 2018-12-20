package com.ghh.framework.util;

import org.springframework.util.StringUtils;

/*****************************************************************
 *
 * 安全验证公共类
 *
 * @author ghh
 * @date 2018年12月19日下午10:47:43
 * @since v1.0.1
 ****************************************************************/
public class SafetyUtil {

	/**
	 * 校验是否为无需校验的URL
	 * 
	 * @param requestPath
	 * @return
	 */
	public static boolean checkIsExcludeUrl(String requestPath) {

		if (requestPath.contains("/rest")) {// 将restful接口排除在拦截器之外
			return true;
		} else if (requestPath.contains("Login.jsp")) {
			return true;
		} else if (requestPath.contains("Register.jsp")) {
			return true;
		} else if (requestPath.contains("registerController.do")) {
			return true;
		} else if (requestPath.contains("loginController.do") || requestPath.contains("ssoController.do")) {
			return true;
		} else if (!StringUtils.isEmpty(GlobalNames.sysConfig.get("EXCLUDE_URL"))) {
			String excludeUrl = GlobalNames.sysConfig.get("EXCLUDE_URL");
			String[] url = excludeUrl.split(",");
			for (int i = 0; i < url.length; i++) {
				if (requestPath.contains(url[i])) {
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

}
