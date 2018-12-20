package com.ghh.framework.common.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*****************************************************************
 *
 * 用户密码与MD5值对应表
 *
 * @author ghh
 * @date 2018年12月19日下午10:11:13
 * @since v1.0.1
 ****************************************************************/
@Entity
@Table(name = "sysdigestauth")
public class Sysdigestauth implements Serializable {

	private static final long serialVersionUID = 4665044507241440589L;
	private String loginname;
	private String passwd;
	private String digestpasswd;

	/** default constructor */
	public Sysdigestauth() {
	}

	/** full constructor */
	public Sysdigestauth(String loginname, String passwd, String digestpasswd) {
		this.loginname = loginname;
		this.passwd = passwd;
		this.digestpasswd = digestpasswd;
	}

	@Id
	@Column(name = "LOGINNAME", unique = true, nullable = false, length = 50)
	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	@Column(name = "PASSWD", nullable = false, length = 64)
	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	@Column(name = "DIGESTPASSWD", nullable = false, length = 128)
	public String getDigestpasswd() {
		return this.digestpasswd;
	}

	public void setDigestpasswd(String digestpasswd) {
		this.digestpasswd = digestpasswd;
	}

}