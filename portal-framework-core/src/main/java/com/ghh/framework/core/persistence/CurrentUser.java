package com.ghh.framework.core.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/*****************************************************************
 *
 * 当前登录用户
 *
 * @author ghh
 * @date 2018年12月19日下午10:32:11
 * @since v1.0.1
 ****************************************************************/
public class CurrentUser implements Serializable {

	private static final long serialVersionUID = -1933503442331055635L;

	// ***********基本属性*******
	private String id;
	private String name;
	private String ownerId;
	private String status;
	private String passwd;
	private String loginname;
	private String username;
	private String dept;
	private String iplist;
	private String checkip;
	private String isleader;
	private String regionid;
	private String rate;
	private String empid;
	private String macaddr;
	private String usertype;
	private String otherinfo;
	private Date createdate;
	private String hashcode;
	private String desc;
	// ***********基本属性*******

	/**
	 * 用户IP
	 */
	private java.lang.String ip;
	/**
	 * 登录时间
	 */
	private java.util.Date logindatetime;

	private List<Object> functionList; // 该用户可用功能列表

	private HashMap<String, Object> userOtherInfo = new HashMap<String, Object>(); // 用来保存用户的其它特征信息

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void putUserOtherInfo(String key, Object value) {
		this.userOtherInfo.put(key, value);
	}

	public HashMap<String, Object> getUserOtherInfo() {
		return userOtherInfo;
	}

	public List<Object> getFunctionList() {
		return functionList;
	}

	public void setFunctionList(List<Object> functionList) {
		this.functionList = functionList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getIplist() {
		return iplist;
	}

	public void setIplist(String iplist) {
		this.iplist = iplist;
	}

	public String getCheckip() {
		return checkip;
	}

	public void setCheckip(String checkip) {
		this.checkip = checkip;
	}

	public String getIsleader() {
		return isleader;
	}

	public void setIsleader(String isleader) {
		this.isleader = isleader;
	}

	public String getRegionid() {
		return regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getEmpid() {
		return empid;
	}

	public void setEmpid(String empid) {
		this.empid = empid;
	}

	public String getMacaddr() {
		return macaddr;
	}

	public void setMacaddr(String macaddr) {
		this.macaddr = macaddr;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getOtherinfo() {
		return otherinfo;
	}

	public void setOtherinfo(String otherinfo) {
		this.otherinfo = otherinfo;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getHashcode() {
		return hashcode;
	}

	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public java.lang.String getIp() {
		return ip;
	}

	public void setIp(java.lang.String ip) {
		this.ip = ip;
	}

	public java.util.Date getLogindatetime() {
		return logindatetime;
	}

	public void setLogindatetime(java.util.Date logindatetime) {
		this.logindatetime = logindatetime;
	}

	public void setUserOtherInfo(HashMap<String, Object> userOtherInfo) {
		this.userOtherInfo = userOtherInfo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return this.loginname;
	}
}
