package com.ghh.framework.common.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/*****************************************************************
 *
 * 数据字典表
 *
 * @author ghh
 * @date 2018年12月19日下午10:09:59
 * @since v1.0.1
 ****************************************************************/
@Entity
@Table(name = "syscode")
public class Syscode implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer codeid;
	private String code;
	private String codename;
	private String codevalue;
	private String codeexplain;
	private String codetype;
	private String codestate;
	private String maintstate;

	/** default constructor */
	public Syscode() {
	}

	/** minimal constructor */
	public Syscode(Integer codeid, String code, String codename, String codevalue, String codeexplain) {
		this.codeid = codeid;
		this.code = code;
		this.codename = codename;
		this.codevalue = codevalue;
		this.codeexplain = codeexplain;
	}

	/** full constructor */
	public Syscode(Integer codeid, String code, String codename, String codevalue, String codeexplain, String codetype,
			String codestate, String maintstate) {
		this.codeid = codeid;
		this.code = code;
		this.codename = codename;
		this.codevalue = codevalue;
		this.codeexplain = codeexplain;
		this.codetype = codetype;
		this.codestate = codestate;
		this.maintstate = maintstate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "CODEID", unique = true, nullable = false)
	public Integer getCodeid() {
		return this.codeid;
	}

	public void setCodeid(Integer codeid) {
		this.codeid = codeid;
	}

	@Column(name = "CODE", nullable = false, length = 20)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "CODENAME", nullable = false, length = 100)
	public String getCodename() {
		return this.codename;
	}

	public void setCodename(String codename) {
		this.codename = codename;
	}

	@Column(name = "CODEVALUE", nullable = false, length = 100)
	public String getCodevalue() {
		return this.codevalue;
	}

	public void setCodevalue(String codevalue) {
		this.codevalue = codevalue;
	}

	@Column(name = "CODEEXPLAIN", nullable = false, length = 100)
	public String getCodeexplain() {
		return this.codeexplain;
	}

	public void setCodeexplain(String codeexplain) {
		this.codeexplain = codeexplain;
	}

	@Column(name = "CODETYPE", length = 100)
	public String getCodetype() {
		return this.codetype;
	}

	public void setCodetype(String codetype) {
		this.codetype = codetype;
	}

	@Column(name = "CODESTATE", length = 1)
	public String getCodestate() {
		return this.codestate;
	}

	public void setCodestate(String codestate) {
		this.codestate = codestate;
	}

	@Column(name = "MAINTSTATE", length = 1)
	public String getMaintstate() {
		return this.maintstate;
	}

	public void setMaintstate(String maintstate) {
		this.maintstate = maintstate;
	}

}