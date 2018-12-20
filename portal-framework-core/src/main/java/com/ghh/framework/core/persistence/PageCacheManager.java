package com.ghh.framework.core.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ghh.framework.GhhException;
import com.ghh.framework.core.dao.GhhSession;

/*****************************************************************
 *
 * 页面缓存持久类
 *
 * @author ghh
 * @date 2018年12月19日下午10:32:39
 * @since v1.0.1
 ****************************************************************/
@Component("pageCacheManager")
public class PageCacheManager {

	@Autowired
	private GhhSession session;

	private PageCacheManager() {
	}

	private volatile Map<String, String> cache = new HashMap<String, String>();

	public synchronized void initPageCache() throws GhhException {
		cache.clear();
		Map<String, String> map = getSysfuntionNeedCache();
		if (map != null) {
			cache.putAll(map);
		}
	}

	public synchronized void addPageCache(String funtionid, String uri) {
		cache.put(funtionid, uri);
	}

	public synchronized void removePageCache(String funtionid) {
		cache.remove(funtionid);
	}

	public Boolean isCached(String uri) {
		return cache.containsValue(uri);
	}

	/**
	 * 获取需要缓存的模块信息
	 * 
	 * @return
	 * @throws GhhException
	 */
	private Map<String, String> getSysfuntionNeedCache() throws GhhException {
		SQLQuery query = session.createSQLQuery("select functionid,location from Sysfunction where cache='1'");
		@SuppressWarnings("unchecked")
		List<Object[]> lst = query.list();
		Map<String, String> map = null;
		if (lst.size() > 0) {
			map = new HashMap<String, String>();
			for (Object[] obj : lst) {
				map.put(obj[0].toString(), obj[1].toString());
			}
		}
		return map;
	}
}
