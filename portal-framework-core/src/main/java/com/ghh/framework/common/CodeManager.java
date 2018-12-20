package com.ghh.framework.common;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.ghh.framework.core.dao.BeanUtils;
import com.ghh.framework.core.persistence.SysCodeManager;

import java.util.Set;

/*****************************************************************
 *
 * 代码管理类
 *
 * @author ghh
 * @date 2018年12月19日下午10:17:56
 * @since v1.0.1
 ****************************************************************/
public class CodeManager {

	private static SysCodeManager codeManager = null;

	static {
		// 这里只加载一次，节约了资源，但是要获取的数据是在SysCodeManager里变量中，也可以根据session随时变换
		// WebApplicationContext ctx =
		// ContextLoader.getCurrentWebApplicationContext();
		codeManager = (SysCodeManager) BeanUtils.getBean("SysCodeManager");

	}

	private CodeManager() {

	}

	/**
	 * 从内存中取代码并转换成字符串数组
	 * 
	 * @param codeType
	 * @return
	 */
	public static String getArrayCode(String codeType) {
		StringBuilder sb = new StringBuilder(400);
		Map<String, Map<String, String>> map = null;
		map = codeManager.getDdMap();
		int j = 0;
		if (map != null && map.get(codeType) != null) {
			Set<Entry<String, String>> set = map.get(codeType).entrySet();
			Iterator<Entry<String, String>> it = set.iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				if (entry != null) {
					if (j == 0) {
						sb.append("['" + entry.getKey() + "','" + entry.getValue() + "']");
						j++;
					} else {
						sb.append("," + "['" + entry.getKey() + "','" + entry.getValue() + "']");
						j++;
					}
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 从内存中获取数据字典迭代器
	 * 
	 * @param codeType
	 * @return
	 */
	public static Iterator<Entry<String, String>> getIteratorFromDd(String codeType) {
		Map<String, Map<String, String>> map = null;
		map = codeManager.getDdMap();
		if (map != null && map.get(codeType) != null) {
			Set<Entry<String, String>> set = map.get(codeType).entrySet();
			return set.iterator();
		} else {
			return null;
		}
	}

	public static SysCodeManager getCodeManager() {
		return codeManager;
	}
}
