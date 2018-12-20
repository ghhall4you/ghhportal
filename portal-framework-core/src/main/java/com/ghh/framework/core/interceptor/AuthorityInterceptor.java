package com.ghh.framework.core.interceptor;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ghh.framework.common.dto.ClientDTO;
import com.ghh.framework.util.ContextHolderUtils;
import com.ghh.framework.util.GlobalNames;
import com.ghh.framework.util.ResourceUtil;
import com.ghh.framework.util.SafetyUtil;
import com.ghh.framework.web.system.client.ClientManager;

/*****************************************************************
 *
 * 权限拦截器
 *
 * @author ghh
 * @date 2018年12月19日下午10:29:25
 * @since v1.0.1
 ****************************************************************/
public class AuthorityInterceptor implements HandlerInterceptor {

	private static final Logger logger = Logger.getLogger(AuthorityInterceptor.class);
	private List<String> excludeUrls;

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	/**
	 * 在controller后拦截
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object,
			Exception exception) throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object,
			ModelAndView modelAndView) throws Exception {

	}

	/**
	 * 在controller前拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		String requestPath = ResourceUtil.getRequestPath(request);// 用户访问的资源地址
		if (StringUtils.isEmpty(requestPath)) {
			logger.info("请求的URI为空！");
			return false;
		}
		if (SafetyUtil.checkIsExcludeUrl(requestPath)) {
			return true;
		}
		if (requestPath.contains("rsrcFilterController.do") || requestPath.contains("operMonController.do")
				|| requestPath.contains("passwordController.do")) {
			return true;
		}
		// 超级管理员可以直接跳过验证
		String loginname = ResourceUtil.getSessionUser().getUser().getLoginname();
		String superUsers = GlobalNames.sysConfig.get("PORTAL_SUPER_USERS");
		String[] usrs = superUsers.split(",");
		for (int i = 0; i < usrs.length; i++) {
			if ((loginname != null) && loginname.equals(usrs[i])) {
				return true;
			}
		}

		logger.info("权限拦截器开始拦截：" + requestPath);
		HttpSession session = ContextHolderUtils.getSession();
		ClientDTO client = ClientManager.getInstance().getClient(session.getId());
		if (client == null) {
			logger.info("权限拦截器结束拦截 ：验证失败");
			return false;
		} else {
			if (requestPath.indexOf(".do") != -1) {
				@SuppressWarnings("unchecked")
				Map<String, Integer> map = (Map<String, Integer>) session.getAttribute("psRoleFunction");
				// 超级管理员是没有这个map的，即map==null
				// RsrcFilterController中有session.setAttribute("psRoleFunction",
				// service.getFilterdBtnsByLoginname(loginname));
				// map存放的是该用户没有权限的按钮，key为按钮路径，所以map.get(requestPath)!=null就说明没权限
				if ((map != null) && (map.get(requestPath) != null)) {
					response.sendRedirect(request.getContextPath() + "/pages/login/Timeout.html");
					return false;
				}
			}
			// requestPath和FuncLocations集合里的路径进行比较判断，是否越权； 如果越权return false
			Set<String> funcLocations = client.getFuncLocations();
			if ((funcLocations == null) || funcLocations.isEmpty()) {
				logger.info("该操作可能存在越权行为1");
				return false;
			}
			if ((funcLocations != null) && (funcLocations.size() > 0)) {
				if (requestPath.indexOf("?") > -1) {
					for (String funcLocation : funcLocations) {
						if (funcLocation.contains(requestPath.split("\\?")[0])) {
							return true;
						}
					}
				}
				logger.info("该操作可能存在越权行为2");
				return false;
			}
		}
		logger.info("权限拦截器结束拦截：" + requestPath);
		return true;
	}

}
