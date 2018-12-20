package com.ghh.framework.web.security;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;

import com.ghh.framework.GhhException;
import com.ghh.framework.common.dto.AjaxJson;
import com.ghh.framework.common.entities.Sysgroup;
import com.ghh.framework.common.entities.Sysrole;
import com.ghh.framework.core.dao.HBUtil;
import com.ghh.framework.exception.GroupException;

/*****************************************************************
 *
 * 权限管理统一入口公共类
 *
 * @author ghh
 * @date 2018年12月19日下午11:00:25
 * @since v1.0.1
 ****************************************************************/
public class PrivilegeManager {

	/**
	 * 根据用户ID或者组织ID获取对象能够被授权的角色
	 * 
	 * @param objectid
	 * @param start
	 * @param limit
	 * @return
	 * @throws GhhException
	 */
	@SuppressWarnings("unchecked")
	public AjaxJson getRoleByObjectId(String objectid, Integer start, Integer limit) throws GhhException {
		String sql = "select count(*) from Sysrole a where not exists (select 1 from Sysact b where a.roleid=b.roleid and b.objectid=:objectid)";
		String sql2 = "select a.roleid,a.roledesc,a.rolename,a.status from Sysrole a where not exists (select 1 from Sysact b where a.roleid=b.roleid and b.objectid=:objectid)";
		Query querycount = HBUtil.getGhhSession().createQuery(sql);
		querycount.setString("objectid", objectid);
		Query query = HBUtil.getGhhSession().createQuery(sql2);
		query.setString("objectid", objectid);
		AjaxJson dto = new AjaxJson();
		dto.setTotalCount(Integer.parseInt(querycount.uniqueResult().toString()));
		List<Sysrole> roleLst = new java.util.ArrayList<Sysrole>();
		List<Object[]> objLst = query.setFirstResult(start).setMaxResults(limit).list();
		for (Object[] obj : objLst) {
			Sysrole role = new Sysrole();
			role.setRoleid(obj[0].toString());
			role.setRoledesc(obj[1] == null ? "" : obj[1].toString());
			role.setRolename(obj[2].toString());
			role.setStatus(obj[3].toString());
			roleLst.add(role);
		}
		dto.setData(roleLst);
		return dto;
	}

	/**
	 * 根据用户组查询组信息
	 * 
	 * @param groupname
	 * @param start
	 * @param limit
	 * @return
	 * @throws GroupException
	 */
	@SuppressWarnings("unchecked")
	public AjaxJson getGroupInfoByName(String groupname, Integer start, Integer limit) throws GroupException {
		StringBuffer hql = new StringBuffer(30);
		hql.append("from Sysgroup a");
		if (!StringUtils.isEmpty(groupname)) {
			hql.append(" where name like :groupname");
		}
		Query querycount = HBUtil.getGhhSession().createSQLQuery("select count(*) " + hql.toString());
		if (!StringUtils.isEmpty(groupname)) {
			querycount.setString("groupname", "%" + groupname + "%");
		}

		AjaxJson dto = new AjaxJson();
		dto.setTotalCount(Integer.parseInt(querycount.uniqueResult().toString()));
		hql.append(" order by a.rate asc,a.createdate asc");
		Query query = HBUtil.getGhhSession().createQuery(hql.toString());
		if (!StringUtils.isEmpty(groupname)) {
			query.setString("groupname", "%" + groupname + "%");
		}

		List<Sysgroup> objLst = query.setFirstResult(start).setMaxResults(limit).list();
		dto.setData(objLst);
		return dto;
	}

	/**
	 * 获取url对应的权限列表中的 公共函数标志
	 * 
	 * @param url
	 * @return
	 * @throws GhhException
	 */
	public String getPublicFlagByLocation(String url) throws GhhException {
		Query query = HBUtil.getGhhSession()
				.createSQLQuery("select publicflag from Sysfunction s where s.location like :location ");
		query.setString("location", url + "%");
		return query.uniqueResult().toString();
	}

	private PrivilegeManager() {
	}

	private static class SingletonHolder {
		public final static PrivilegeManager instance = new PrivilegeManager();
	}

	public static PrivilegeManager getInstance() {
		return SingletonHolder.instance;
	}
}
