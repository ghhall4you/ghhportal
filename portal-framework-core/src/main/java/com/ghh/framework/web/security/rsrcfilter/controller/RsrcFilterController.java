package com.ghh.framework.web.security.rsrcfilter.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ghh.framework.GhhException;
import com.ghh.framework.common.dto.AjaxJson;
import com.ghh.framework.core.support.ControllerSupport;
import com.ghh.framework.util.GlobalNames;
import com.ghh.framework.util.ResourceUtil;
import com.ghh.framework.web.security.rsrcfilter.service.IRsrcFilterService;

/*****************************************************************
 *
 * 功能模块根据用户角色进行过滤接口控制类
 *
 * @author ghh
 * @date 2018年12月19日下午10:59:13
 * @since v1.0.1
 ****************************************************************/
@Controller
@RequestMapping(value = "/rsrcFilterController")
public class RsrcFilterController extends ControllerSupport<Object> {

	@Autowired
	private IRsrcFilterService service;

	@RequestMapping(params = "filterRsrc")
	@ResponseBody
	public AjaxJson filterRsrc(HttpSession session) throws GhhException {
		AjaxJson json = new AjaxJson();
		String loginname = ResourceUtil.getSessionUser().getUser().getLoginname();
		json.setData(service.getNavInfoByLoginname(loginname));

		// 非超级管理员增加按钮权限的后台过滤
		String superUsers = GlobalNames.sysConfig.get("PORTAL_SUPER_USERS");
		String[] usrs = superUsers.split(",");
		Boolean superFlag = false;
		for (int i = 0; i < usrs.length; i++) {
			if (loginname.equals(usrs[i])) {
				superFlag = true;
				break;
			}
		}
		if (!superFlag) {
			session.setAttribute("psRoleFunction", service.getFilterdBtnsByLoginname(loginname));
		}
		return json;
	}

}
