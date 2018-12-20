package com.ghh.framework.common.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*****************************************************************
 *
 * 系统参数表
 *
 * @author ghh
 * @date 2018年12月19日下午10:13:25
 * @since v1.0.1
 ****************************************************************/
@Entity
@Table(name = "sysparam")
public class Sysparam implements Serializable {

	private static final long serialVersionUID = 1L;
	private String paramcode;
	private String paramname;
	private String paramvalue;
	private String paramexplain;
	private String maintstate;

	/** default constructor */
	public Sysparam() {
	}

	/** minimal constructor */
	public Sysparam(String paramcode, String paramname, String paramvalue, String maintstate) {
		this.paramcode = paramcode;
		this.paramname = paramname;
		this.paramvalue = paramvalue;
		this.maintstate = maintstate;
	}

	/** full constructor */
	public Sysparam(String paramcode, String paramname, String paramvalue, String paramexplain, String maintstate) {
		this.paramcode = paramcode;
		this.paramname = paramname;
		this.paramvalue = paramvalue;
		this.paramexplain = paramexplain;
		this.maintstate = maintstate;
	}

	@Id
	@Column(name = "PARAMCODE", unique = true, nullable = false, length = 50)
	public String getParamcode() {
		return this.paramcode;
	}

	public void setParamcode(String paramcode) {
		this.paramcode = paramcode;
	}

	@Column(name = "PARAMNAME", nullable = false, length = 50)
	public String getParamname() {
		return this.paramname;
	}

	public void setParamname(String paramname) {
		this.paramname = paramname;
	}

	@Column(name = "PARAMVALUE", nullable = false, length = 65535)
	public String getParamvalue() {
		return this.paramvalue;
	}

	public void setParamvalue(String paramvalue) {
		this.paramvalue = paramvalue;
	}

	@Column(name = "PARAMEXPLAIN", length = 100)
	public String getParamexplain() {
		return this.paramexplain;
	}

	public void setParamexplain(String paramexplain) {
		this.paramexplain = paramexplain;
	}

	@Column(name = "MAINTSTATE", nullable = false, length = 1)
	public String getMaintstate() {
		return this.maintstate;
	}

	public void setMaintstate(String maintstate) {
		this.maintstate = maintstate;
	}

}