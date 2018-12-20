package com.ghh.framework.common.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/*****************************************************************
 *
 * 系统角色表
 *
 * @author ghh
 * @date 2018年12月19日下午10:14:02
 * @since v1.0.1
 ****************************************************************/
@Entity
@Table(name = "sysrole")
public class Sysrole implements Serializable {

	private static final long serialVersionUID = 1L;
	private String roleid;
	private String roledesc;
	private String parent;
	private String owner;
	private String rolename;
	private String status;
	private Date createdate;
	private String hashcode;

	/** default constructor */
	public Sysrole() {
	}

	/** minimal constructor */
	public Sysrole(String roleid, String rolename, String status) {
		this.roleid = roleid;
		this.rolename = rolename;
		this.status = status;
	}

	/** full constructor */
	public Sysrole(String roleid, String roledesc, String parent, String owner, String rolename, String status,
			Date createdate, String hashcode) {
		this.roleid = roleid;
		this.roledesc = roledesc;
		this.parent = parent;
		this.owner = owner;
		this.rolename = rolename;
		this.status = status;
		this.createdate = createdate;
		this.hashcode = hashcode;
	}

	@Id
	@Column(name = "ROLEID", unique = true, nullable = false, length = 32)
	public String getRoleid() {
		return this.roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	@Column(name = "ROLEDESC", length = 30)
	public String getRoledesc() {
		return this.roledesc;
	}

	public void setRoledesc(String roledesc) {
		this.roledesc = roledesc;
	}

	@Column(name = "PARENT", length = 32)
	public String getParent() {
		return this.parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	@Column(name = "OWNER", length = 32)
	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Column(name = "ROLENAME", nullable = false, length = 100)
	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATEDATE", length = 10)
	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	@Column(name = "HASHCODE", length = 64)
	public String getHashcode() {
		return this.hashcode;
	}

	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}

}