package com.ghh.framework.web.security.rsrcfilter.service;

import java.util.List;
import java.util.Map;

import com.ghh.framework.GhhException;
import com.ghh.framework.common.entities.Sysfunction;
import com.ghh.framework.core.annotation.NotProguard;

/*****************************************************************
 *
 * 功能模块根据用户角色进行过滤接口
 *
 * @author ghh
 * @date 2018年12月19日下午10:59:52
 * @since v1.0.1
 ****************************************************************/
@NotProguard
public interface IRsrcFilterService {

	final String RSRC_CONT_BTN_FLAG = "1";// 权限中包括按钮

	/**
	 * 根据登录名获取登录模块权限
	 * 
	 * @param condition
	 * @return
	 * @throws GhhException
	 */
	public List<Sysfunction> getNavInfoByLoginname(String... condition) throws GhhException;

	/**
	 * 根据登录名获取拥有的按钮功能
	 * 
	 * @param loginname
	 * @return
	 * @throws GhhException
	 */
	public Map<String, Integer> getFilterdBtnsByLoginname(String loginname) throws GhhException;

	/**
	 * 根据用户名和ID获取人员信息
	 * 
	 * @param loginname
	 * @return
	 * @throws GhhException
	 */
	public Map<String, String> getPersonInfoByUserId(String loginname, String userId) throws GhhException;
}
