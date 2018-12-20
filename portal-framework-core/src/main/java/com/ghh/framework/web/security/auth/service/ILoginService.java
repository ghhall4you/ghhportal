package com.ghh.framework.web.security.auth.service;

import com.ghh.framework.GhhException;
import com.ghh.framework.common.entities.Sysuser;
import com.ghh.framework.core.annotation.NotProguard;
import com.ghh.framework.exception.UserException;

/*****************************************************************
 *
 * 登录验证接口
 *
 * @author ghh
 * @date 2018年12月19日下午10:52:31
 * @since v1.0.1
 ****************************************************************/
@NotProguard
public interface ILoginService {

	/**
	 * 校验用户是否存在
	 * 
	 * @param loginname
	 * @param passwd
	 * @return
	 * @throws UserException
	 */
	public boolean checkUserIsExistsOrNot(String loginname, String passwd) throws UserException;

	/**
	 * 获取用户信息
	 * 
	 * @param loginname
	 * @param passwd
	 * @return
	 * @throws UserException
	 */
	public Sysuser getUserByLogin(String loginname, String passwd) throws UserException;

	/**
	 * 根据用户名获取用户信息
	 * 
	 * @param loginname
	 * @param passwd
	 * @return
	 * @throws UserException
	 */
	public Sysuser getUserByLoginName(String loginname) throws UserException;

	/**
	 * 添加用户登录日志
	 * 
	 * @param logContent
	 * @param logTpye
	 * @throws UserException
	 */
	public void addLog(String loginname, String logContent, int logTpye) throws UserException;

	/**
	 * 检查用户名是否存在
	 * 
	 * @param loginName
	 * @return
	 */
	public boolean checkNameIsExistsOrNot(String loginName);

	/**
	 * 检查密码有效性
	 * 
	 * @param loginName
	 * @return
	 */
	public Boolean checkValidTime(String loginName);

	/**
	 * 锁定用户
	 * 
	 * @param loginName
	 * @throws UserException
	 */
	public void lockUser(String loginName) throws UserException;

	/**
	 * 检查用户是否被锁定
	 * 
	 * @param loginname
	 * @return
	 */
	public boolean checkUserIsLocksOrNot(String loginname);

	/**
	 * 获取跳转页面
	 * 
	 * @return
	 * @throws GhhException
	 */
	public String getDispatchPage() throws GhhException;
}
