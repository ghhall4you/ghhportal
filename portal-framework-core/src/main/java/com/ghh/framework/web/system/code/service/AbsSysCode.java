package com.ghh.framework.web.system.code.service;

import java.util.List;

import com.ghh.framework.common.entities.VSyscode;

/*****************************************************************
 *
 * @author ghh
 * @date 2018年12月20日下午7:02:28
 * @since v1.0.1
 ****************************************************************/
public abstract class AbsSysCode implements ISysCode {

	/**
	 * 从代码表中取代码
	 * 
	 * @param codeType
	 *            代码类别
	 * @param filter
	 *            代码过滤条件
	 * @return
	 */
	abstract public List<VSyscode> getCodeList(String codeType, String filter);

	/**
	 * 根据codeType和code查代码视图，如果存在，则返回code对应的名称，否则返回原code
	 * 
	 * @param codeType
	 * @param code
	 * @return
	 */
	abstract public String getValueByCode(String codeType, String code);

}
