package com.ghh.framework.common.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/*****************************************************************
 *
 * 组织表
 *
 * @author ghh
 * @date 2018年12月19日下午10:12:38
 * @since v1.0.1
 ****************************************************************/
@Entity
@Table(name = "sysgroup", uniqueConstraints = @UniqueConstraint(columnNames = "NAME"))
public class Sysgroup implements Serializable {

	private static final long serialVersionUID = 1L;
	private String groupid;
	private String name;
	private String description;
	private String parentid;
	private String org;
	private String districtcode;
	private String license;
	private String principal;
	private String shortname;
	private String address;
	private String tel;
	private String linkman;
	private String type;
	private String chargedept;
	private String otherinfo;
	private String owner;
	private String status;
	private Date createdate;
	private String hashcode;
	private String rate;

	/** default constructor */
	public Sysgroup() {
	}

	/** minimal constructor */
	public Sysgroup(String groupid, String name, String rate) {
		this.groupid = groupid;
		this.name = name;
		this.rate = rate;
	}

	/** full constructor */
	public Sysgroup(String groupid, String name, String description, String parentid, String org, String districtcode,
			String license, String principal, String shortname, String address, String tel, String linkman, String type,
			String chargedept, String otherinfo, String owner, String status, Date createdate, String hashcode,
			String rate) {
		this.groupid = groupid;
		this.name = name;
		this.description = description;
		this.parentid = parentid;
		this.org = org;
		this.districtcode = districtcode;
		this.license = license;
		this.principal = principal;
		this.shortname = shortname;
		this.address = address;
		this.tel = tel;
		this.linkman = linkman;
		this.type = type;
		this.chargedept = chargedept;
		this.otherinfo = otherinfo;
		this.owner = owner;
		this.status = status;
		this.createdate = createdate;
		this.hashcode = hashcode;
		this.rate = rate;
	}

	@Id
	@Column(name = "GROUPID", unique = true, nullable = false, length = 32)
	public String getGroupid() {
		return this.groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	@Column(name = "NAME", unique = true, nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DESCRIPTION", length = 65535)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "PARENTID", length = 32)
	public String getParentid() {
		return this.parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	@Column(name = "ORG", length = 8)
	public String getOrg() {
		return this.org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	@Column(name = "DISTRICTCODE", length = 12)
	public String getDistrictcode() {
		return this.districtcode;
	}

	public void setDistrictcode(String districtcode) {
		this.districtcode = districtcode;
	}

	@Column(name = "LICENSE", length = 20)
	public String getLicense() {
		return this.license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	@Column(name = "PRINCIPAL", length = 32)
	public String getPrincipal() {
		return this.principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	@Column(name = "SHORTNAME", length = 50)
	public String getShortname() {
		return this.shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	@Column(name = "ADDRESS", length = 150)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "TEL", length = 30)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "LINKMAN", length = 30)
	public String getLinkman() {
		return this.linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	@Column(name = "TYPE", length = 3)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "CHARGEDEPT", length = 50)
	public String getChargedept() {
		return this.chargedept;
	}

	public void setChargedept(String chargedept) {
		this.chargedept = chargedept;
	}

	@Column(name = "OTHERINFO", length = 65535)
	public String getOtherinfo() {
		return this.otherinfo;
	}

	public void setOtherinfo(String otherinfo) {
		this.otherinfo = otherinfo;
	}

	@Column(name = "OWNER", length = 32)
	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Column(name = "STATUS", length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "CREATEDATE", length = 19)
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

	@Column(name = "RATE", nullable = false, length = 8)
	public String getRate() {
		return this.rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

}