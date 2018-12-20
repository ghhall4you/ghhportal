package com.ghh.framework.common.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*****************************************************************
 *
 * 系统用户表
 *
 * @author ghh
 * @date 2018年12月19日下午10:15:17
 * @since v1.0.1
 ****************************************************************/
@Entity
@Table(name = "sysuser")
public class Sysuser implements Serializable {

	private static final long serialVersionUID = 4816750376325613919L;
	private String userid;
	private String passwd;
	private String loginname;
	private String dept;
	private String description;
	private String iplist;
	private String checkip;
	private String useful;
	private String isleader;
	private String regionid;
	private String username;
	private String owner;
	private String rate;
	private String certification;
	private String macaddr;
	private String usertype;
	private String otherinfo;
	private Date createdate;
	private String hashcode;
	private String email;
	private String phone;
	private String address;
	private Date pswinvalidtime;
	private String acctlockflag;

	/** default constructor */
	public Sysuser() {
	}

	/** minimal constructor */
	public Sysuser(String userid, String useful, String usertype) {
		this.userid = userid;
		this.useful = useful;
		this.usertype = usertype;
	}

	/** full constructor */
	public Sysuser(String userid, String passwd, String loginname, String dept, String description, String iplist,
			String checkip, String useful, String isleader, String regionid, String username, String owner, String rate,
			String certification, String macaddr, String usertype, String otherinfo, Date createdate, String hashcode,
			String email, String phone, String address, Date pswinvalidtime, String acctlockflag) {
		this.userid = userid;
		this.passwd = passwd;
		this.loginname = loginname;
		this.dept = dept;
		this.description = description;
		this.iplist = iplist;
		this.checkip = checkip;
		this.useful = useful;
		this.isleader = isleader;
		this.regionid = regionid;
		this.username = username;
		this.owner = owner;
		this.rate = rate;
		this.certification = certification;
		this.macaddr = macaddr;
		this.usertype = usertype;
		this.otherinfo = otherinfo;
		this.createdate = createdate;
		this.hashcode = hashcode;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.pswinvalidtime = pswinvalidtime;
		this.acctlockflag = acctlockflag;
	}

	@Id
	@Column(name = "USERID", unique = true, nullable = false, length = 32)
	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Column(name = "PASSWD", nullable = false, length = 64)
	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	@Column(name = "LOGINNAME", nullable = false, length = 50)
	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	@Column(name = "DEPT", length = 32)
	public String getDept() {
		return this.dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	@Column(name = "DESCRIPTION", length = 65535)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "IPLIST")
	public String getIplist() {
		return this.iplist;
	}

	public void setIplist(String iplist) {
		this.iplist = iplist;
	}

	@Column(name = "CHECKIP", length = 1)
	public String getCheckip() {
		return this.checkip;
	}

	public void setCheckip(String checkip) {
		this.checkip = checkip;
	}

	@Column(name = "USEFUL", nullable = false, length = 1)
	public String getUseful() {
		return this.useful;
	}

	public void setUseful(String useful) {
		this.useful = useful;
	}

	@Column(name = "ISLEADER", length = 1)
	public String getIsleader() {
		return this.isleader;
	}

	public void setIsleader(String isleader) {
		this.isleader = isleader;
	}

	@Column(name = "REGIONID", length = 32)
	public String getRegionid() {
		return this.regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}

	@Column(name = "USERNAME", nullable = false, length = 32)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "OWNER", length = 32)
	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Column(name = "RATE", length = 8)
	public String getRate() {
		return this.rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	@Column(name = "CERTIFICATION", length = 20)
	public String getCertification() {
		return this.certification;
	}

	public void setCertification(String certification) {
		this.certification = certification;
	}

	@Column(name = "MACADDR", length = 65535)
	public String getMacaddr() {
		return this.macaddr;
	}

	public void setMacaddr(String macaddr) {
		this.macaddr = macaddr;
	}

	@Column(name = "USERTYPE", nullable = false, length = 2)
	public String getUsertype() {
		return this.usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	@Column(name = "OTHERINFO", length = 65535)
	public String getOtherinfo() {
		return this.otherinfo;
	}

	public void setOtherinfo(String otherinfo) {
		this.otherinfo = otherinfo;
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

	@Column(name = "EMAIL", length = 64)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "PHONE", length = 32)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "ADDRESS", length = 100)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "PSWINVALIDTIME", length = 19)
	public Date getPswinvalidtime() {
		return this.pswinvalidtime;
	}

	public void setPswinvalidtime(Date pswinvalidtime) {
		this.pswinvalidtime = pswinvalidtime;
	}

	@Column(name = "ACCTLOCKFLAG", length = 1)
	public String getAcctlockflag() {
		return this.acctlockflag;
	}

	public void setAcctlockflag(String acctlockflag) {
		this.acctlockflag = acctlockflag;
	}

}