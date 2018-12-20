package com.ghh.framework.common.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * VSyscode entity. @author MyEclipse Persistence Tools
 */
/*****************************************************************
 *
 * 数据字典视图
 *
 * @author ghh
 * @date 2018年12月19日下午10:17:18
 * @since v1.0.1
 ****************************************************************/
@Entity
@Table(name = "v_syscode")
public class VSyscode implements Serializable {

	private static final long serialVersionUID = 1L;
	private String codeid;
	private String code;
	private String codevalue;
	private String codeexplain;
	private String codetype;
	private String codestate;

	/** default constructor */
	public VSyscode() {
	}

	/** minimal constructor */
	public VSyscode(String codeid, String code) {
		this.codeid = codeid;
		this.code = code;
	}

	/** full constructor */
	public VSyscode(String codeid, String code, String codevalue, String codeexplain, String codetype,
			String codestate) {
		this.codeid = codeid;
		this.code = code;
		this.codevalue = codevalue;
		this.codeexplain = codeexplain;
		this.codetype = codetype;
		this.codestate = codestate;
	}

	@Id
	@Column(name = "codeid", nullable = false, length = 32)
	public String getCodeid() {
		return this.codeid;
	}

	public void setCodeid(String codeid) {
		this.codeid = codeid;
	}

	@Column(name = "code", nullable = false, length = 20)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "codevalue", length = 100)
	public String getCodevalue() {
		return this.codevalue;
	}

	public void setCodevalue(String codevalue) {
		this.codevalue = codevalue;
	}

	@Column(name = "codeexplain", length = 100)
	public String getCodeexplain() {
		return this.codeexplain;
	}

	public void setCodeexplain(String codeexplain) {
		this.codeexplain = codeexplain;
	}

	@Column(name = "codetype", length = 65535)
	public String getCodetype() {
		return this.codetype;
	}

	public void setCodetype(String codetype) {
		this.codetype = codetype;
	}

	@Column(name = "codestate", length = 1)
	public String getCodestate() {
		return this.codestate;
	}

	public void setCodestate(String codestate) {
		this.codestate = codestate;
	}

}