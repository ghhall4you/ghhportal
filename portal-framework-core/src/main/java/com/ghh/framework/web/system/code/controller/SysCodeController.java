package com.ghh.framework.web.system.code.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ghh.framework.common.entities.VSyscode;
import com.ghh.framework.web.system.code.service.ISysCode;

/*****************************************************************
 *
 * 数据字典管理类
 *
 * @author ghh
 * @date 2018年12月20日下午7:01:50
 * @since v1.0.1
 ****************************************************************/
@Controller
public class SysCodeController {

	@Autowired
	private ISysCode syscode;

	/**
	 * 从代码表中取代码
	 * 
	 * @param codeType
	 *            代码类别
	 * @param filter
	 *            代码过滤条件
	 * @return
	 */
	public List<VSyscode> getCodeList(String codeType, String filter) {
		return syscode.getCodeList(codeType, filter);
	}

	/**
	 * 根据codeType和code查代码视图，如果存在，则返回code对应的名称，否则返回原code
	 * 
	 * @param codeType
	 * @param code
	 * @return
	 */
	public String getValueByCode(String codeType, String code) {
		return syscode.getValueByCode(codeType, code);
	}

}
