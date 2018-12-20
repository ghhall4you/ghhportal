package com.ghh.framework.common.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*****************************************************************
 *
 * 系统定时任务表
 *
 * @author ghh
 * @date 2018年12月19日下午10:14:57
 * @since v1.0.1
 ****************************************************************/
@Entity
@Table(name = "systimetask")
public class Systimetask implements Serializable {

	private static final long serialVersionUID = 1L;
	private String taskid;
	private String jobname;
	private String jobgroup;
	private String description;
	private String cron;
	private String classname;
	private String active;
	private String isstart;
	private Date createdate;
	private String creator;
	private Date updatedate;
	private String updator;

	/** default constructor */
	public Systimetask() {
	}

	/** minimal constructor */
	public Systimetask(String taskid, String jobname, String jobgroup, String description, String cron,
			String classname, String active, String isstart, Date createdate, String creator) {
		this.taskid = taskid;
		this.jobname = jobname;
		this.jobgroup = jobgroup;
		this.description = description;
		this.cron = cron;
		this.classname = classname;
		this.active = active;
		this.isstart = isstart;
		this.createdate = createdate;
		this.creator = creator;
	}

	/** full constructor */
	public Systimetask(String taskid, String jobname, String jobgroup, String description, String cron,
			String classname, String active, String isstart, Date createdate, String creator, Date updatedate,
			String updator) {
		this.taskid = taskid;
		this.jobname = jobname;
		this.jobgroup = jobgroup;
		this.description = description;
		this.cron = cron;
		this.classname = classname;
		this.active = active;
		this.isstart = isstart;
		this.createdate = createdate;
		this.creator = creator;
		this.updatedate = updatedate;
		this.updator = updator;
	}

	@Id
	@Column(name = "TASKID", unique = true, nullable = false, length = 32)
	public String getTaskid() {
		return this.taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	@Column(name = "JOBNAME", nullable = false, length = 100)
	public String getJobname() {
		return this.jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	@Column(name = "JOBGROUP", nullable = false, length = 100)
	public String getJobgroup() {
		return this.jobgroup;
	}

	public void setJobgroup(String jobgroup) {
		this.jobgroup = jobgroup;
	}

	@Column(name = "DESCRIPTION", nullable = false, length = 150)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "CRON", nullable = false, length = 50)
	public String getCron() {
		return this.cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	@Column(name = "CLASSNAME", nullable = false, length = 150)
	public String getClassname() {
		return this.classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	@Column(name = "ACTIVE", nullable = false, length = 1)
	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	@Column(name = "ISSTART", nullable = false, length = 1)
	public String getIsstart() {
		return this.isstart;
	}

	public void setIsstart(String isstart) {
		this.isstart = isstart;
	}

	@Column(name = "CREATEDATE", nullable = false, length = 19)
	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	@Column(name = "CREATOR", nullable = false, length = 32)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "UPDATEDATE", length = 19)
	public Date getUpdatedate() {
		return this.updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	@Column(name = "UPDATOR", length = 32)
	public String getUpdator() {
		return this.updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}

}