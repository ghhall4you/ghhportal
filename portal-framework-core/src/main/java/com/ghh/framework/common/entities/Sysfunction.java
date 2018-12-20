package com.ghh.framework.common.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*****************************************************************
 *
 * 系统资源表
 *
 * @author ghh
 * @date 2018年12月19日下午10:11:59
 * @since v1.0.1
 ****************************************************************/
@Entity
@Table(name = "sysfunction")
public class Sysfunction implements Serializable {

	private static final long serialVersionUID = 6731518983483556646L;
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
	private String iconurl;
	private String buttonid;

	/** default constructor */
	public Sysfunction() {
	}

	/** minimal constructor */
	public Sysfunction(String functionid, String location, String title, Short orderno, String type, String description,
			String rpflag, String publicflag, String rbflag) {
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
	public Sysfunction(String functionid, String location, String title, String parent, Short orderno, String type,
			String description, String log, String developer, String active, String functiondesc, String auflag,
			String rpflag, String uptype, String publicflag, String prsource, String rbflag, String param1,
			String param2, String sysid, String firstopen, String focanclose, String reauflag, Date createdate,
			String owner, String cache, String iconcls, String iconurl, String buttonid) {
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
		this.iconurl = iconurl;
		this.buttonid = buttonid;
	}

	@Id
	@Column(name = "FUNCTIONID", unique = true, nullable = false, length = 32)
	public String getFunctionid() {
		return this.functionid;
	}

	public void setFunctionid(String functionid) {
		this.functionid = functionid;
	}

	@Column(name = "LOCATION", nullable = false, length = 65535)
	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name = "TITLE", nullable = false, length = 100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "PARENT", length = 32)
	public String getParent() {
		return this.parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	@Column(name = "ORDERNO", nullable = false)
	public Short getOrderno() {
		return this.orderno;
	}

	public void setOrderno(Short orderno) {
		this.orderno = orderno;
	}

	@Column(name = "TYPE", nullable = false, length = 1)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "DESCRIPTION", nullable = false, length = 65535)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "LOG", length = 1)
	public String getLog() {
		return this.log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	@Column(name = "DEVELOPER", length = 50)
	public String getDeveloper() {
		return this.developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	@Column(name = "ACTIVE", length = 3)
	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	@Column(name = "FUNCTIONDESC", length = 65535)
	public String getFunctiondesc() {
		return this.functiondesc;
	}

	public void setFunctiondesc(String functiondesc) {
		this.functiondesc = functiondesc;
	}

	@Column(name = "AUFLAG", length = 1)
	public String getAuflag() {
		return this.auflag;
	}

	public void setAuflag(String auflag) {
		this.auflag = auflag;
	}

	@Column(name = "RPFLAG", nullable = false, length = 2)
	public String getRpflag() {
		return this.rpflag;
	}

	public void setRpflag(String rpflag) {
		this.rpflag = rpflag;
	}

	@Column(name = "UPTYPE", length = 1)
	public String getUptype() {
		return this.uptype;
	}

	public void setUptype(String uptype) {
		this.uptype = uptype;
	}

	@Column(name = "PUBLICFLAG", nullable = false, length = 1)
	public String getPublicflag() {
		return this.publicflag;
	}

	public void setPublicflag(String publicflag) {
		this.publicflag = publicflag;
	}

	@Column(name = "PRSOURCE", length = 65535)
	public String getPrsource() {
		return this.prsource;
	}

	public void setPrsource(String prsource) {
		this.prsource = prsource;
	}

	@Column(name = "RBFLAG", nullable = false, length = 1)
	public String getRbflag() {
		return this.rbflag;
	}

	public void setRbflag(String rbflag) {
		this.rbflag = rbflag;
	}

	@Column(name = "PARAM1", length = 200)
	public String getParam1() {
		return this.param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	@Column(name = "PARAM2", length = 200)
	public String getParam2() {
		return this.param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	@Column(name = "SYSID", length = 32)
	public String getSysid() {
		return this.sysid;
	}

	public void setSysid(String sysid) {
		this.sysid = sysid;
	}

	@Column(name = "FIRSTOPEN", length = 1)
	public String getFirstopen() {
		return this.firstopen;
	}

	public void setFirstopen(String firstopen) {
		this.firstopen = firstopen;
	}

	@Column(name = "FOCANCLOSE", length = 1)
	public String getFocanclose() {
		return this.focanclose;
	}

	public void setFocanclose(String focanclose) {
		this.focanclose = focanclose;
	}

	@Column(name = "REAUFLAG", length = 1)
	public String getReauflag() {
		return this.reauflag;
	}

	public void setReauflag(String reauflag) {
		this.reauflag = reauflag;
	}

	@Column(name = "CREATEDATE", length = 19)
	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	@Column(name = "OWNER", length = 32)
	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Column(name = "CACHE", length = 1)
	public String getCache() {
		return this.cache;
	}

	public void setCache(String cache) {
		this.cache = cache;
	}

	@Column(name = "ICONCLS", length = 50)
	public String getIconcls() {
		return this.iconcls;
	}

	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
	}

	@Column(name = "ICONURL", length = 100)
	public String getIconurl() {
		return this.iconurl;
	}

	public void setIconurl(String iconurl) {
		this.iconurl = iconurl;
	}

	@Column(name = "BUTTONID", length = 32)
	public String getButtonid() {
		return this.buttonid;
	}

	public void setButtonid(String buttonid) {
		this.buttonid = buttonid;
	}

}