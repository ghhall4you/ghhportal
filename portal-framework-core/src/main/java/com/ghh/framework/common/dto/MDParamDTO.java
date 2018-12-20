package com.ghh.framework.common.dto;

/*****************************************************************
 *
 * 模块参数DTO
 *
 * @author ghh
 * @date 2018年12月19日下午10:03:05
 * @since v1.0.1
 ****************************************************************/
public class MDParamDTO {
	/** 功能代码 */
	private String functionid;

	/** URL */
	private String location;

	/** 菜单的中文描述 */
	private String title;

	/** 自动审核标志，0－不自动审核；1－自动审核 */
	private String auflag;

	/** 报表标志：0－普通模块；1－普通报表；4－单据类报表 */
	private String rpflag;

	/** 模块功能分类::0－征缴，1－支付 */
	private String uptype;

	/** 公共函数标志，1－公共函数；0－非公共函数 */
	private String publicflag;

	/** 日志数据源 */
	private String prsource;

	/** 操作日志回退标志：0－不允许回退，1－允许回退 */
	private String rbflag;

	/** 自定义模块参数1 */
	private String param1;

	/** 自定义模块参数2 */
	private String param2;

	/** 在action中是否记日志，1为记，其它不记 */
	private String log;

	/** 模块的按钮ID */
	private String buttonid;

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getFunctionid() {
		return functionid;
	}

	public void setFunctionid(String functionid) {
		this.functionid = functionid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuflag() {
		return auflag;
	}

	public void setAuflag(String auflag) {
		this.auflag = auflag;
	}

	public String getRpflag() {
		return rpflag;
	}

	public void setRpflag(String rpflag) {
		this.rpflag = rpflag;
	}

	public String getUptype() {
		return uptype;
	}

	public void setUptype(String uptype) {
		this.uptype = uptype;
	}

	public String getPublicflag() {
		return publicflag;
	}

	public void setPublicflag(String publicflag) {
		this.publicflag = publicflag;
	}

	public String getPrsource() {
		return prsource;
	}

	public void setPrsource(String prsource) {
		this.prsource = prsource;
	}

	public String getRbflag() {
		return rbflag;
	}

	public void setRbflag(String rbflag) {
		this.rbflag = rbflag;
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getButtonid() {
		return buttonid;
	}

	public void setButtonid(String buttonid) {
		this.buttonid = buttonid;
	}
}
