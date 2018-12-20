package com.ghh.framework.common.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*****************************************************************
 *
 * 用户、组织、角色关联关系表
 *
 * @author ghh
 * @date 2018年12月19日下午10:08:34
 * @since v1.0.1
 ****************************************************************/
@Entity
@Table(name = "sysact")
public class Sysact implements Serializable {

	private static final long serialVersionUID = 1L;
	private String actid;
	private String objectid;
	private String roleid;
	private String sceneid;
	private String objecttype;
	private String dispatchauth;
	private String hashcode;

	/** default constructor */
	public Sysact() {
	}

	/** minimal constructor */
	public Sysact(String actid, String objectid, String roleid, String objecttype) {
		this.actid = actid;
		this.objectid = objectid;
		this.roleid = roleid;
		this.objecttype = objecttype;
	}

	/** full constructor */
	public Sysact(String actid, String objectid, String roleid, String sceneid, String objecttype, String dispatchauth,
			String hashcode) {
		this.actid = actid;
		this.objectid = objectid;
		this.roleid = roleid;
		this.sceneid = sceneid;
		this.objecttype = objecttype;
		this.dispatchauth = dispatchauth;
		this.hashcode = hashcode;
	}

	@Id
	@Column(name = "ACTID", unique = true, nullable = false, length = 40)
	public String getActid() {
		return this.actid;
	}

	public void setActid(String actid) {
		this.actid = actid;
	}

	@Column(name = "OBJECTID", nullable = false, length = 40)
	public String getObjectid() {
		return this.objectid;
	}

	public void setObjectid(String objectid) {
		this.objectid = objectid;
	}

	@Column(name = "ROLEID", nullable = false, length = 40)
	public String getRoleid() {
		return this.roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	@Column(name = "SCENEID", length = 40)
	public String getSceneid() {
		return this.sceneid;
	}

	public void setSceneid(String sceneid) {
		this.sceneid = sceneid;
	}

	@Column(name = "OBJECTTYPE", nullable = false, length = 1)
	public String getObjecttype() {
		return this.objecttype;
	}

	public void setObjecttype(String objecttype) {
		this.objecttype = objecttype;
	}

	@Column(name = "DISPATCHAUTH", length = 1)
	public String getDispatchauth() {
		return this.dispatchauth;
	}

	public void setDispatchauth(String dispatchauth) {
		this.dispatchauth = dispatchauth;
	}

	@Column(name = "HASHCODE", length = 64)
	public String getHashcode() {
		return this.hashcode;
	}

	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}

}