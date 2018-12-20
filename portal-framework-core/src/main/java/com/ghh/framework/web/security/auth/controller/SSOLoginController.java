package com.ghh.framework.web.security.auth.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.ghh.framework.GhhException;
import com.ghh.framework.common.dto.ClientDTO;
import com.ghh.framework.common.entities.Sysuser;
import com.ghh.framework.core.persistence.CurrentUser;
import com.ghh.framework.core.support.ControllerSupport;
import com.ghh.framework.exception.UserException;
import com.ghh.framework.util.CopyIgnoreProperty;
import com.ghh.framework.util.GlobalNames;
import com.ghh.framework.util.IpUtil;
import com.ghh.framework.util.RmiServiceUtil;
import com.ghh.framework.web.security.auth.service.ILoginService;
import com.ghh.framework.web.security.auth.service.ISSOLoginService;
import com.ghh.framework.web.system.client.ClientManager;

/*****************************************************************
 *
 * 单点登录Controller类，作用是对外提供单点登录到Portal的功能
 *
 * @author ghh
 * @date 2018年12月19日下午10:49:58
 * @since v1.0.1
 ****************************************************************/
@Controller
@RequestMapping(value = "/ssoController")
public class SSOLoginController extends ControllerSupport<Object> {

	@Autowired
	private ISSOLoginService ssoService;

	@Autowired
	private ILoginService service;

	/**
	 * 利用TOKEN登录Portal系统
	 * 
	 * @param req
	 * @param loginName
	 * @param token
	 * @return
	 * @throws GhhException
	 * @throws UserException
	 */
	@RequestMapping(params = "index", method = RequestMethod.GET)
	public ModelAndView ssoPortal(HttpServletRequest req, HttpSession session, String loginName, String token)
			throws GhhException, UserException {
		if (ssoService.checkToken(loginName, token)) {
			// 1.用户登录
			ClientDTO dto = login(req, session, loginName);

			// 2.判断登录人员是否为超级管理员
			Boolean superFlag = ssoService.isSuperUser(loginName);

			if (superFlag == false) {
				// 2.1添加用户功能模块列表
				ssoService.setUserFunctionByLoginName(loginName, dto);
			}

			// 3.增加在线用户统计
			if ("true".equals(GlobalNames.sysConfig.get("PORTAL_CLUSTER_RMI_CACHE"))) {
				RmiServiceUtil.getClientRmiService().addClinet(session.getId(), dto);
			} else {
				ClientManager.getInstance().addClinet(session.getId(), dto);
			}

			// 4.校验是否需要跳转到指定页面
			if (!StringUtils.isEmpty(req.getParameter("resource"))) {
				ModelAndView mav = new ModelAndView("/" + service.getDispatchPage());
				try {
					String location = ssoService.getLocationByUrlParam(req.getParameter("resource"));
					mav.addObject("resource", location);
				} catch (Exception e) {
					GlobalNames.log.error("获取跳转路径发生异常： " + req.getParameter("resource"));
				}
				return mav;
			}

			return new ModelAndView(new RedirectView(req.getContextPath() + "/loginController.do?dispatchPage"));
		}
		return new ModelAndView(new RedirectView(req.getContextPath() + "/loginController.do?openLogin"));
	}

	/**
	 * 第三方系统人员单点登录
	 * 
	 * @param req
	 * @param session
	 * @param loginName
	 * @return
	 * @throws UserException
	 * @throws GhhException
	 */
	private ClientDTO login(HttpServletRequest req, HttpSession session, String loginName)
			throws UserException, GhhException {
		String ipAddr = IpUtil.getIpAddr(req);
		ClientDTO client = new ClientDTO();
		client.setIp(ipAddr);
		client.setLogindatetime(new Date());
		Sysuser user = service.getUserByLoginName(loginName);
		client.setUser(user);

		// 1.添加登陆日志
		String message = "第三方系统用户: " + user.getUsername() + "登录成功.";
		if ("1".equals(GlobalNames.sysConfig.get("SIGN_INOROUT_RECORD"))) {
			service.addLog(user.getLoginname(), message, GlobalNames.LOG_TYPE_LOGIN);
		}
		GlobalNames.log.info(message);

		// 2.持久化人员登录管理
		CurrentUser cUser = new CurrentUser();
		CopyIgnoreProperty.copy(user, cUser);
		cUser.setLogindatetime(client.getLogindatetime());
		cUser.setIp(client.getIp());
		cUser.setId(user.getUserid());
		session.setAttribute("cUser", cUser);
		return client;
	}
}
