package com.ghh.framework.common.dto;

import java.util.List;

/*****************************************************************
 *
 * 原生SQL分页查询DTO
 *
 * @author ghh
 * @date 2018年12月19日下午10:08:01
 * @since v1.0.1
 ****************************************************************/
public class SqlQueryDTO {

	private int totalCount;
	private List<Object[]> objLst;

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<Object[]> getObjLst() {
		return objLst;
	}

	public void setObjLst(List<Object[]> objLst) {
		this.objLst = objLst;
	}
}
