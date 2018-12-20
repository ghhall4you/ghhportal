package com.ghh.framework.core.persistence;

import java.util.List;
import java.util.Map;

/*****************************************************************
 *
 * 数据字典缓存
 *
 * @author ghh
 * @date 2018年12月19日下午10:31:52
 * @since v1.0.1
 ****************************************************************/
public class CodeDataCache {

	// var rpflag_comboData = [['0','普通模块'],['1','普通报表']]
	private List<Map<String, String>> hpLst;

	private Map<String, Map<String, String>> ddMap = null;// 数据字典新储存方式

	public List<Map<String, String>> getHpLst() {
		return hpLst;
	}

	public void setHpLst(List<Map<String, String>> hpLst) {
		this.hpLst = hpLst;
	}

	public Map<String, Map<String, String>> getDdMap() {
		return ddMap;
	}

	public void setDdMap(Map<String, Map<String, String>> ddMap) {
		this.ddMap = ddMap;
	}

}
