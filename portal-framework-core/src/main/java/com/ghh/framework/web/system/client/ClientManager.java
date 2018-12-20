package com.ghh.framework.web.system.client;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.ghh.framework.common.dto.ClientDTO;
import com.ghh.framework.core.annotation.NotProguard;

/*****************************************************************
 *
 * 对在线用户的管理
 *
 * @author ghh
 * @date 2018年12月20日下午7:00:04
 * @since v1.0.1
 ****************************************************************/
public class ClientManager {

	private static ClientManager instance = new ClientManager();

	private ClientManager() {

	}

	public static ClientManager getInstance() {
		return instance;
	}

	private Map<String, ClientDTO> map = new ConcurrentHashMap<String, ClientDTO>();

	/**
	 * 
	 * @param sessionId
	 * @param ClientDTO
	 */
	@NotProguard
	public void addClinet(String sessionId, ClientDTO ClientDTO) {
		map.put(sessionId, ClientDTO);
	}

	/**
	 * sessionId
	 */
	public void removeClinet(String sessionId) {
		map.remove(sessionId);
	}

	/**
	 * sessionId
	 */
	public void removeClinet(String loginname, String username) {
		Set<Entry<String, ClientDTO>> set = map.entrySet();
		Iterator<Entry<String, ClientDTO>> it = set.iterator();// 遍历的类
		while (it.hasNext()) {
			Map.Entry<String, ClientDTO> entry = it.next();// 找到所有key-value对集合
			if (entry.getValue().getUser().getLoginname().equals(loginname)
					&& entry.getValue().getUser().getUsername().equals(username)) {// 通过判断是否有该value值
				map.remove(entry.getKey());
			}
		}
	}

	/**
	 * 
	 * @param sessionId
	 * @return
	 */
	@NotProguard
	public ClientDTO getClient(String sessionId) {
		return map.get(sessionId);
	}

	/**
	 * 
	 * @param sessionId
	 * @return
	 */
	public ClientDTO getClient(String loginname, String username) {
		Set<Entry<String, ClientDTO>> set = map.entrySet();
		Iterator<Entry<String, ClientDTO>> it = set.iterator();// 遍历的类
		while (it.hasNext()) {
			Map.Entry<String, ClientDTO> entry = it.next();// 找到所有key-value对集合
			if (entry.getValue().getUser().getLoginname().equals(loginname)
					&& entry.getValue().getUser().getUsername().equals(username)) {// 通过判断是否有该value值
				return entry.getValue();
			}
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public Collection<ClientDTO> getAllClient() {
		return map.values();
	}

}
