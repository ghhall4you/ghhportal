package com.ghh.framework.core.persistence;

import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ghh.framework.GhhException;
import com.ghh.framework.web.system.code.service.ISysCode;

/*****************************************************************
 *
 * 数据字典持久类
 *
 * @author ghh
 * @date 2018年12月19日下午10:33:03
 * @since v1.0.1
 ****************************************************************/
@Component("SysCodeManager")
public class SysCodeManager {
	private volatile Map<String, Map<String, String>> ddMap = null;

	@Resource(name = "DefaultSysCode")
	private ISysCode syscode;

	private SysCodeManager() {
	}

	/**
	 * 获取数据字典
	 * 
	 * @return
	 * @throws GhhException
	 */
	public Map<String, Map<String, String>> getDdMap() {
		return ddMap;
	}

	/**
	 * @Title initDdMap
	 * @Description 设置数据字典集合
	 * @param ddMap
	 * @throws GhhException
	 */
	public void initDdMap() throws GhhException {
		if (ddMap == null) {
			synchronized (this) {
				if (ddMap == null) {
					ddMap = syscode.getAllCodeMap();
				}
			}
		}
	}

	public synchronized void updateDdMap(String code, String oldCodeValue, String newCodeValue, String newCodeExplain) {
		Map<String, String> map = this.ddMap.get(code);
		// modify by zhuangkaixuan(z11230) on 20170907
		// Question list:D005219
		// start
		if (map == null) {
			insertDdMap(code, newCodeValue, newCodeExplain);
			return;
		}
		// end
		map.remove(oldCodeValue);
		map.put(newCodeValue, newCodeExplain);
	}

	public synchronized void deleteDdMap(String code, String codeValue) {
		Map<String, String> map = this.ddMap.get(code);
		map.remove(codeValue);
	}

	public synchronized void insertDdMap(String code, String codeValue, String codeExplain) {
		Map<String, String> map = this.ddMap.get(code);
		if (map == null) {
			map = new TreeMap<String, String>();
			ddMap.put(code, map);
		}
		map.put(codeValue, codeExplain);
	}

}
