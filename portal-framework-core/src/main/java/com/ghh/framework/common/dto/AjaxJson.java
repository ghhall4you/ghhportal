package com.ghh.framework.common.dto;

import java.util.Map;

/*****************************************************************
 *
 * 后台返回前台的DTO对象
 *
 * @author ghh
 * @date 2018年12月19日下午9:56:59
 * @since v1.0.1
 ****************************************************************/
public class AjaxJson {
	private boolean success = true;
	private String msg = "请求成功！";
	private Object data = null;
	private Map<String, Object> attributes;
	private int totalCount;

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
