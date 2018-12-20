package com.ghh.framework.core.interceptor;

import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ghh.framework.core.persistence.SysfunctionManager;
import com.ghh.framework.util.GlobalNames;
import com.ghh.framework.util.LeafHttpUtil;

/*****************************************************************
 *
 * portal框架的安全拦截器
 *
 * @author ghh
 * @date 2018年12月19日下午10:29:54
 * @since v1.0.1
 ****************************************************************/
public class BusinessInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String reqFunction = LeafHttpUtil.calcReqFunction(request);
		Enumeration<String> enums = request.getParameterNames();
		String url = null;
		boolean sessOpFlag = false;
		while (enums.hasMoreElements()) {
			String param = enums.nextElement();
			if (param.indexOf("open") > -1) {
				url = param;
			} else {
				if (url != null) {
					url = url + "&" + param;
				}
			}
			if ("1".equals(GlobalNames.sysConfig.get("SESSN_TMOUT_CNTL"))) {
				if (param.equals("checkIsLoginOrNot") || param.equals("checkSessnIsTmoutOrNot")
						|| param.equals("monitorQuery")) {
					sessOpFlag = true;
				}
			}
		}
		if (url != null) {
			SysfunctionManager.setCurrentRequestFunctionCache(reqFunction.replace("/", "") + "?" + url);
		}
		// 在拦截器里增加最后访问时间的控制，则可以根据此时间进行判断是否已经超时
		if ("1".equals(GlobalNames.sysConfig.get("SESSN_TMOUT_CNTL"))) {
			if (sessOpFlag == false) {
				request.getSession().setAttribute("SESSNTMOUT",
						new Date().getTime() + request.getSession().getMaxInactiveInterval() * 1000);
			}
		}
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
