package com.ghh.framework.web.system.code.service;

import java.util.List;
import java.util.Map;

import com.ghh.framework.GhhException;
import com.ghh.framework.common.entities.VSyscode;

/*****************************************************************
 *
 * 数据字典接口
 *
 * @author ghh
 * @date 2018年12月20日下午7:02:47
 * @since v1.0.1
 ****************************************************************/
public interface ISysCode {

	/**
	 * 从代码表中取代码
	 * 
	 * @param codeType
	 *            代码类别
	 * @param filter
	 *            代码过滤条件
	 * @return
	 */
	public List<VSyscode> getCodeList(String codeType, String filter);

	/**
	 * 根据codeType和code查代码视图，如果存在，则返回code对应的名称，否则返回原code
	 * 
	 * @param codeType
	 * @param code
	 * @return
	 */
	public String getValueByCode(String codeType, String code);

	/**
	 * 获取所有的数据字典
	 * 
	 * @return
	 */
	public Map<String, Map<String, String>> getAllCodeMap() throws GhhException;
}
