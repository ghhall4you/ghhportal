package com.ghh.framework.common.dto;

import java.util.Date;

/*****************************************************************
 *
 * 菜单节点DTO
 *
 * @author ghh
 * @date 2018年12月19日下午10:07:34
 * @since v1.0.1
 ****************************************************************/
public class MenuNodeDTO {
	private String functionid;
	private String location;
	private String title;
	private String parent;
	private Short orderno;
	private String type;
	private String description;
	private String log;
	private String developer;
	private String active;
	private String functiondesc;
	private String auflag;
	private String rpflag;
	private String uptype;
	private String publicflag;
	private String prsource;
	private String rbflag;
	private String param1;
	private String param2;
	private String sysid;
	private String firstopen;
	private String focanclose;
	private String reauflag;
	private Date createdate;
	private String owner;
	private String cache;
	private String iconcls;

	/** default constructor */
	public MenuNodeDTO() {
	}

	/** minimal constructor */
	public MenuNodeDTO(String functionid, String location, String title,
			Short orderno, String type, String description, String rpflag,
			String publicflag, String rbflag) {
		this.functionid = functionid;
		this.location = location;
		this.title = title;
		this.orderno = orderno;
		this.type = type;
		this.description = description;
		this.rpflag = rpflag;
		this.publicflag = publicflag;
		this.rbflag = rbflag;
	}

	/** full constructor */
	public MenuNodeDTO(String functionid, String location, String title,
			String parent, Short orderno, String type, String description,
			String log, String developer, String active, String functiondesc,
			String auflag, String rpflag, String uptype, String publicflag,
			String prsource, String rbflag, String param1, String param2,
			String sysid, String firstopen, String focanclose, String reauflag,
			Date createdate, String owner, String cache, String iconcls) {
		this.functionid = functionid;
		this.location = location;
		this.title = title;
		this.parent = parent;
		this.orderno = orderno;
		this.type = type;
		this.description = description;
		this.log = log;
		this.developer = developer;
		this.active = active;
		this.functiondesc = functiondesc;
		this.auflag = auflag;
		this.rpflag = rpflag;
		this.uptype = uptype;
		this.publicflag = publicflag;
		this.prsource = prsource;
		this.rbflag = rbflag;
		this.param1 = param1;
		this.param2 = param2;
		this.sysid = sysid;
		this.firstopen = firstopen;
		this.focanclose = focanclose;
		this.reauflag = reauflag;
		this.createdate = createdate;
		this.owner = owner;
		this.cache = cache;
		this.iconcls = iconcls;
	}

	public String getFunctionid() {
		return this.functionid;
	}

	public void setFunctionid(String functionid) {
		this.functionid = functionid;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getParent() {
		return this.parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public Short getOrderno() {
		return this.orderno;
	}

	public void setOrderno(Short orderno) {
		this.orderno = orderno;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLog() {
		return this.log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getDeveloper() {
		return this.developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getFunctiondesc() {
		return this.functiondesc;
	}

	public void setFunctiondesc(String functiondesc) {
		this.functiondesc = functiondesc;
	}

	public String getAuflag() {
		return this.auflag;
	}

	public void setAuflag(String auflag) {
		this.auflag = auflag;
	}

	public String getRpflag() {
		return this.rpflag;
	}

	public void setRpflag(String rpflag) {
		this.rpflag = rpflag;
	}

	public String getUptype() {
		return this.uptype;
	}

	public void setUptype(String uptype) {
		this.uptype = uptype;
	}

	public String getPublicflag() {
		return this.publicflag;
	}

	public void setPublicflag(String publicflag) {
		this.publicflag = publicflag;
	}

	public String getPrsource() {
		return this.prsource;
	}

	public void setPrsource(String prsource) {
		this.prsource = prsource;
	}

	public String getRbflag() {
		return this.rbflag;
	}

	public void setRbflag(String rbflag) {
		this.rbflag = rbflag;
	}

	public String getParam1() {
		return this.param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return this.param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getSysid() {
		return this.sysid;
	}

	public void setSysid(String sysid) {
		this.sysid = sysid;
	}

	public String getFirstopen() {
		return this.firstopen;
	}

	public void setFirstopen(String firstopen) {
		this.firstopen = firstopen;
	}

	public String getFocanclose() {
		return this.focanclose;
	}

	public void setFocanclose(String focanclose) {
		this.focanclose = focanclose;
	}

	public String getReauflag() {
		return this.reauflag;
	}

	public void setReauflag(String reauflag) {
		this.reauflag = reauflag;
	}

	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getCache() {
		return this.cache;
	}

	public void setCache(String cache) {
		this.cache = cache;
	}

	public String getIconcls() {
		return this.iconcls;
	}

	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
	}
}
