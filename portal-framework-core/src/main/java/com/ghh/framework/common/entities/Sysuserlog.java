package com.ghh.framework.common.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*****************************************************************
 *
 * 系统日志表
 *
 * @author ghh
 * @date 2018年12月19日下午10:16:58
 * @since v1.0.1
 ****************************************************************/
@Entity
@Table(name = "sysuserlog")
public class Sysuserlog implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long opseno;
	private String functionid;
	private Integer ym;
	private String objectid;
	private String digest;
	private String prcol1;
	private String prcol2;
	private String prcol3;
	private String prcol4;
	private String prcol5;
	private String prcol6;
	private String prcol7;
	private String prcol8;
	private String operator;
	private Date optime;
	private String remarks;
	private String bzstate;
	private String rollbacker;
	private Date rollbacktime;
	private byte[] orisourceb;
	private byte[] form;
	private String broswer;
	private String opip;
	private Integer logtype;

	// Constructors

	/** default constructor */
	public Sysuserlog() {
	}

	/** minimal constructor */
	public Sysuserlog(Long opseno, String operator, Date optime, String bzstate) {
		this.opseno = opseno;
		this.operator = operator;
		this.optime = optime;
		this.bzstate = bzstate;
	}

	/** full constructor */
	public Sysuserlog(Long opseno, String functionid, Integer ym,
			String objectid, String digest, String prcol1, String prcol2,
			String prcol3, String prcol4, String prcol5, String prcol6,
			String prcol7, String prcol8, String operator, Date optime,
			String remarks, String bzstate, String rollbacker,
			Date rollbacktime, byte[] orisourceb, byte[] form, String broswer,
			String opip, Integer logtype) {
		this.opseno = opseno;
		this.functionid = functionid;
		this.ym = ym;
		this.objectid = objectid;
		this.digest = digest;
		this.prcol1 = prcol1;
		this.prcol2 = prcol2;
		this.prcol3 = prcol3;
		this.prcol4 = prcol4;
		this.prcol5 = prcol5;
		this.prcol6 = prcol6;
		this.prcol7 = prcol7;
		this.prcol8 = prcol8;
		this.operator = operator;
		this.optime = optime;
		this.remarks = remarks;
		this.bzstate = bzstate;
		this.rollbacker = rollbacker;
		this.rollbacktime = rollbacktime;
		this.orisourceb = orisourceb;
		this.form = form;
		this.broswer = broswer;
		this.opip = opip;
		this.logtype = logtype;
	}

	// Property accessors
	@Id
	@Column(name = "OPSENO", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	public Long getOpseno() {
		return this.opseno;
	}

	public void setOpseno(Long opseno) {
		this.opseno = opseno;
	}

	@Column(name = "FUNCTIONID", length = 40)
	public String getFunctionid() {
		return this.functionid;
	}

	public void setFunctionid(String functionid) {
		this.functionid = functionid;
	}

	@Column(name = "YM")
	public Integer getYm() {
		return this.ym;
	}

	public void setYm(Integer ym) {
		this.ym = ym;
	}

	@Column(name = "OBJECTID", length = 40)
	public String getObjectid() {
		return this.objectid;
	}

	public void setObjectid(String objectid) {
		this.objectid = objectid;
	}

	@Column(name = "DIGEST", length = 65535)
	public String getDigest() {
		return this.digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	@Column(name = "PRCOL1", length = 120)
	public String getPrcol1() {
		return this.prcol1;
	}

	public void setPrcol1(String prcol1) {
		this.prcol1 = prcol1;
	}

	@Column(name = "PRCOL2", length = 120)
	public String getPrcol2() {
		return this.prcol2;
	}

	public void setPrcol2(String prcol2) {
		this.prcol2 = prcol2;
	}

	@Column(name = "PRCOL3", length = 120)
	public String getPrcol3() {
		return this.prcol3;
	}

	public void setPrcol3(String prcol3) {
		this.prcol3 = prcol3;
	}

	@Column(name = "PRCOL4", length = 120)
	public String getPrcol4() {
		return this.prcol4;
	}

	public void setPrcol4(String prcol4) {
		this.prcol4 = prcol4;
	}

	@Column(name = "PRCOL5", length = 120)
	public String getPrcol5() {
		return this.prcol5;
	}

	public void setPrcol5(String prcol5) {
		this.prcol5 = prcol5;
	}

	@Column(name = "PRCOL6", length = 120)
	public String getPrcol6() {
		return this.prcol6;
	}

	public void setPrcol6(String prcol6) {
		this.prcol6 = prcol6;
	}

	@Column(name = "PRCOL7", length = 120)
	public String getPrcol7() {
		return this.prcol7;
	}

	public void setPrcol7(String prcol7) {
		this.prcol7 = prcol7;
	}

	@Column(name = "PRCOL8", length = 120)
	public String getPrcol8() {
		return this.prcol8;
	}

	public void setPrcol8(String prcol8) {
		this.prcol8 = prcol8;
	}

	@Column(name = "OPERATOR", nullable = false, length = 50)
	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Column(name = "OPTIME", nullable = false, length = 19)
	public Date getOptime() {
		return this.optime;
	}

	public void setOptime(Date optime) {
		this.optime = optime;
	}

	@Column(name = "REMARKS", length = 65535)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "BZSTATE", nullable = false, length = 1)
	public String getBzstate() {
		return this.bzstate;
	}

	public void setBzstate(String bzstate) {
		this.bzstate = bzstate;
	}

	@Column(name = "ROLLBACKER", length = 50)
	public String getRollbacker() {
		return this.rollbacker;
	}

	public void setRollbacker(String rollbacker) {
		this.rollbacker = rollbacker;
	}

	@Column(name = "ROLLBACKTIME", length = 19)
	public Date getRollbacktime() {
		return this.rollbacktime;
	}

	public void setRollbacktime(Date rollbacktime) {
		this.rollbacktime = rollbacktime;
	}

	@Column(name = "ORISOURCEB")
	public byte[] getOrisourceb() {
		return this.orisourceb;
	}

	public void setOrisourceb(byte[] orisourceb) {
		this.orisourceb = orisourceb;
	}

	@Column(name = "FORM")
	public byte[] getForm() {
		return this.form;
	}

	public void setForm(byte[] form) {
		this.form = form;
	}

	@Column(name = "BROSWER", length = 120)
	public String getBroswer() {
		return this.broswer;
	}

	public void setBroswer(String broswer) {
		this.broswer = broswer;
	}

	@Column(name = "OPIP", length = 50)
	public String getOpip() {
		return this.opip;
	}

	public void setOpip(String opip) {
		this.opip = opip;
	}

	@Column(name = "LOGTYPE", length = 1)
	public Integer getLogtype() {
		return this.logtype;
	}

	public void setLogtype(Integer logtype) {
		this.logtype = logtype;
	}

}