package com.ghh.framework.web.security.auth.service;

import com.ghh.framework.GhhException;
import com.ghh.framework.core.annotation.NotProguard;

/*****************************************************************
 *
 * 客户端自定义跳转页面接口，获取客户端设置的首页面
 *
 * @author ghh
 * @date 2018年12月19日下午10:51:42
 * @since v1.0.1
 ****************************************************************/
@NotProguard
public interface ICustomDispatchPageService {

	/**
	 * 获取客户端返回的自定义首页面
	 * 
	 * @param userId
	 * @param loginname
	 * @return
	 * @throws GhhException
	 */
	public String getCustomDispatchPage(String userId, String loginname) throws GhhException;
}
