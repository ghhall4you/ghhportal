package com.ghh.framework.web.security.auth.service;

import com.ghh.framework.GhhException;
import com.ghh.framework.common.dto.ClientDTO;
import com.ghh.framework.core.annotation.NotProguard;

/*****************************************************************
 *
 * 单点登录接口服务类，作用是对外提供单点登录到Portal的功能
 *
 * @author ghh
 * @date 2018年12月19日下午10:52:48
 * @since v1.0.1
 ****************************************************************/
public interface ISSOLoginService {

	/**
	 * 根据登录名获取Token值
	 * 
	 * @param loginName
	 * @return
	 * @throws GhhException
	 */
	@NotProguard
	public String getToken(String loginName) throws GhhException;

	/**
	 * 校验传入的TOKEN是否有效
	 * 
	 * @param loginName
	 * @param token
	 * @return
	 * @throws GhhException
	 */
	public Boolean checkToken(String loginName, String token) throws GhhException;

	/**
	 * 判断登录人员是否为超级管理员
	 * 
	 * @param loginName
	 * @return
	 * @throws GhhException
	 */
	public Boolean isSuperUser(String loginName) throws GhhException;

	/**
	 * 根据用户名添加用户的功能列表
	 * 
	 * @param loginName
	 * @param dto
	 * @throws GhhException
	 */
	public void setUserFunctionByLoginName(String loginName, ClientDTO dto) throws GhhException;

	/**
	 * 根据URL上的参数获取系统功能表对应的URL路径
	 * 
	 * @param urlParam
	 * @return
	 * @throws GhhException
	 */
	public String getLocationByUrlParam(String urlParam) throws GhhException;
}
