package com.ghh.framework.web.system.cache.remote.rmi;

import java.util.Collection;

import com.ghh.framework.common.dto.ClientDTO;

/*****************************************************************
 *
 * 用户信息缓存接口
 *
 * @author ghh
 * @date 2018年12月20日下午6:59:33
 * @since v1.0.1
 ****************************************************************/
public interface IClientRMIService {

	/**
	 * 
	 * @param sessionId
	 * @param ClientDTO
	 */
	public void addClinet(String sessionId, ClientDTO ClientDTO);

	/**
	 * 
	 * @param sessionId
	 */
	public void removeClinet(String sessionId);

	/**
	 * 
	 * @param loginname
	 * @param username
	 */
	public void removeClinet(String loginname, String username);

	/**
	 * 
	 * @param sessionId
	 * @return
	 */
	public ClientDTO getClient(String sessionId);

	/**
	 * 
	 * @param loginname
	 * @param username
	 * @return
	 */
	public ClientDTO getClient(String loginname, String username);

	/**
	 * 获取所有用户
	 * 
	 * @return
	 */
	public Collection<ClientDTO> getAllClient();
}
