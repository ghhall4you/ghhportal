package com.ghh.framework.web.security.auth.service;

import javax.servlet.http.HttpServletRequest;

import com.ghh.framework.GhhException;
import com.ghh.framework.common.dto.AjaxJson;
import com.ghh.framework.core.annotation.NotProguard;

/*****************************************************************
 *
 * 客户端自定义登录校验接口类
 *
 * @author ghh
 * @date 2018年12月19日下午10:52:07
 * @since v1.0.1
 ****************************************************************/
@NotProguard
public interface ICustomLoginService {

	/**
	 * 客户端自定义系统登录校验
	 * 
	 * @param loginname
	 * @param password
	 * @param req
	 * @return
	 * @throws GhhException
	 */
	public AjaxJson customLogin(String loginname, String password, HttpServletRequest req) throws GhhException;

}
