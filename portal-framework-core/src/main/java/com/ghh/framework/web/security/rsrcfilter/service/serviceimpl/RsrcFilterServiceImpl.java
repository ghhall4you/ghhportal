package com.ghh.framework.web.security.rsrcfilter.service.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Service;

import com.ghh.framework.GhhException;
import com.ghh.framework.common.entities.Sysfunction;
import com.ghh.framework.core.support.ServiceSupport;
import com.ghh.framework.util.GlobalNames;
import com.ghh.framework.web.security.rsrcfilter.service.IRsrcFilterService;

/*****************************************************************
 *
 * 功能模块根据用户角色进行过滤接口实现类
 *
 * @author ghh
 * @date 2018年12月19日下午10:59:34
 * @since v1.0.1
 ****************************************************************/
@Service
public class RsrcFilterServiceImpl extends ServiceSupport implements IRsrcFilterService {

	@SuppressWarnings("unchecked")
	public List<Sysfunction> getNavInfoByLoginname(String... condition) throws GhhException {
		int len = 0;
		if (condition != null) {
			len = condition.length;
		}
		String loginname = null;
		String btnFlag = "0";
		switch (len) {
		case 1:
			loginname = condition[0];
			break;
		case 2:
			loginname = condition[0];
			btnFlag = condition[1];
		}

		String sql = "";
		String superUsers = GlobalNames.sysConfig.get("PORTAL_SUPER_USERS");
		String[] usrs = superUsers.split(",");
		Boolean superFlag = false;
		for (int i = 0; i < usrs.length; i++) {
			if (loginname.equals(usrs[i])) {
				superFlag = true;
				break;
			}
		}
		if (superFlag) {
			StringBuilder sb = new StringBuilder(240);
			sb.append(
					"SELECT X.FUNCTIONID,X.LOCATION,X.TITLE,X.PARENT,X.ORDERNO,X.TYPE,X.DESCRIPTION,X.PUBLICFLAG,X.ACTIVE,X.RPFLAG,X.ICONCLS,X.ICONURL from SYSFUNCTION X ");
			sb.append("WHERE X.ACTIVE='1' ");
			if (!"1".equals(btnFlag)) {
				sb.append("AND X.TYPE<>'2' ");
			}
			sb.append(" ORDER BY X.PARENT ASC,X.ORDERNO ASC,X.TYPE ASC");
			sql = sb.toString();
		} else {
			StringBuilder sb = new StringBuilder(1050);
			sb.append(
					"SELECT Y.FUNCTIONID,Y.LOCATION,Y.TITLE,Y.PARENT,Y.ORDERNO,Y.TYPE,Y.DESCRIPTION,Y.PUBLICFLAG,Y.ACTIVE,Y.RPFLAG,Y.ICONCLS,Y.ICONURL FROM ("
							+ "SELECT X.FUNCTIONID,X.LOCATION,X.TITLE,X.PARENT,X.ORDERNO,X.TYPE,X.DESCRIPTION,X.PUBLICFLAG,X.ACTIVE,X.RPFLAG,X.ICONCLS,X.ICONURL"
							+ "  FROM SYSFUNCTION X, SYSROLEACL A, SYSACT B, SYSGROUP C,SYSROLE D"
							+ " WHERE X.FUNCTIONID = A.FUNCTIONID" + "   AND B.OBJECTID = C.GROUPID"
							+ "   AND A.ROLEID = B.ROLEID " + " AND D.ROLEID = B.ROLEID ");
			sb.append(" AND C.GROUPID =" + "       (SELECT D.GROUPID" + "          FROM SYSUSERGROUPREF D"
					+ "         WHERE D.USERID = (SELECT E.USERID FROM SYSUSER E WHERE E.LOGINNAME =:loginname))");
			sb.append(" AND X.ACTIVE='1' AND D.STATUS = '1' ");
			if (!"1".equals(btnFlag)) {
				sb.append("AND X.TYPE<>'2' ");
			}
			sb.append(" UNION "
					+ "SELECT X.FUNCTIONID,X.LOCATION,X.TITLE,X.PARENT,X.ORDERNO,X.TYPE,X.DESCRIPTION,X.PUBLICFLAG,X.ACTIVE,X.RPFLAG,X.ICONCLS,X.ICONURL"
					+ " FROM SYSFUNCTION X, SYSROLEACL A, SYSACT B, SYSUSER C,SYSROLE D"
					+ " WHERE X.FUNCTIONID = A.FUNCTIONID" + "   AND B.OBJECTID = C.USERID"
					+ "   AND A.ROLEID = B.ROLEID AND D.ROLEID = B.ROLEID");
			sb.append("   AND C.USERID = (SELECT E.USERID FROM SYSUSER E WHERE E.LOGINNAME =:loginname)");
			sb.append(" AND X.ACTIVE='1' AND D.STATUS = '1' ");
			if (!"1".equals(btnFlag)) {
				sb.append("AND X.TYPE<>'2' ");
			}
			sb.append(") Y");
			sb.append(" ORDER BY Y.PARENT ASC,Y.ORDERNO ASC,Y.TYPE ASC ");
			sql = sb.toString();
		}

		Query query = session.createSQLQuery(sql);
		if (superFlag == false) {
			query.setString("loginname", loginname);
		}
		List<Object[]> lst = query.list();
		List<Sysfunction> functionlist = new java.util.ArrayList<Sysfunction>();
		for (int i = 0; i < lst.size(); i++) {
			Object[] obj = (Object[]) lst.get(i);
			Sysfunction dto = new Sysfunction();
			dto.setFunctionid(obj[0].toString());
			dto.setLocation(obj[1].toString());
			dto.setTitle(obj[2].toString());
			dto.setParent(obj[3] == null ? "" : obj[3].toString());
			dto.setOrderno((short) Integer.parseInt(obj[4].toString()));
			dto.setType(obj[5].toString());
			dto.setDescription(obj[6].toString());
			dto.setPublicflag(obj[7].toString());
			dto.setActive(obj[8].toString());
			dto.setRpflag(obj[9].toString());
			dto.setIconcls(obj[10] == null ? "none" : obj[10].toString());
			dto.setIconurl(obj[11] == null ? "none" : obj[11].toString());
			functionlist.add(dto);
		}
		return functionlist;
	}

	public Map<String, Integer> getFilterdBtnsByLoginname(String loginname) throws GhhException {
		StringBuilder sb = new StringBuilder(800);
		sb.append(
				"SELECT X.LOCATION from SYSFUNCTION X WHERE X.ACTIVE='1' AND X.TYPE='2' AND X.BUTTONID IS NOT NULL AND X.LOCATION NOT IN (SELECT Y.LOCATION FROM ("
						+ "SELECT X.LOCATION" + "  FROM SYSFUNCTION X, SYSROLEACL A, SYSACT B, SYSGROUP C"
						+ " WHERE X.FUNCTIONID = A.FUNCTIONID" + "   AND B.OBJECTID = C.GROUPID"
						+ "   AND A.ROLEID = B.ROLEID ");
		sb.append(" AND C.GROUPID =" + "       (SELECT D.GROUPID" + "          FROM SYSUSERGROUPREF D"
				+ "         WHERE D.USERID = (SELECT E.USERID FROM SYSUSER E WHERE E.LOGINNAME =:loginname))");
		sb.append(" AND X.ACTIVE='1' AND X.TYPE='2' ");
		sb.append(" UNION " + "SELECT X.LOCATION" + " FROM SYSFUNCTION X, SYSROLEACL A, SYSACT B, SYSUSER C"
				+ " WHERE X.FUNCTIONID = A.FUNCTIONID" + "   AND B.OBJECTID = C.USERID" + "   AND A.ROLEID = B.ROLEID");
		sb.append("   AND C.USERID = (SELECT E.USERID FROM SYSUSER E WHERE E.LOGINNAME =:loginname)");
		sb.append(" AND X.ACTIVE='1' AND X.TYPE='2' ) Y)");

		Query query = session.createSQLQuery(sb.toString());
		query.setString("loginname", loginname);
		@SuppressWarnings("unchecked")
		List<String> lst = query.list();
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < lst.size(); i++) {
			String obj = lst.get(i);
			map.put(obj, 1);
		}
		return map;
	}

	public Map<String, String> getPersonInfoByUserId(String loginname, String userId) throws GhhException {
		String superUsers = GlobalNames.sysConfig.get("PORTAL_SUPER_USERS");
		String[] usrs = superUsers.split(",");
		Boolean superFlag = false;
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < usrs.length; i++) {
			if (loginname.equals(usrs[i])) {
				superFlag = true;
				break;
			}
		}

		if (superFlag) {
			map.put("role", "超级管理员");
		} else {
			StringBuilder sb = new StringBuilder(320);
			sb.append("SELECT B.ROLENAME FROM SYSACT A,SYSROLE B WHERE  A.ROLEID=B.ROLEID AND A.OBJECTID =:userid ");
			sb.append("UNION ALL ");
			sb.append("SELECT B.ROLENAME FROM SYSACT A,SYSROLE B,SYSGROUP C,SYSUSERGROUPREF D ");
			sb.append("WHERE  A.ROLEID=B.ROLEID AND A.OBJECTID=C.GROUPID AND C.GROUPID=D.GROUPID AND D.USERID=:userid");
			Query query = session.createSQLQuery(sb.toString());
			query.setString("userid", userId);
			@SuppressWarnings("unchecked")
			List<String> lst = query.list();
			String role = "";
			for (int i = 0; i < lst.size(); i++) {
				String obj = lst.get(i);
				role += obj + ",";
			}
			map.put("role", role.isEmpty() ? "" : role.substring(0, role.length() - 1));
		}
		map.put("group", "超级管理员");
		StringBuilder sb = new StringBuilder(130);
		sb.append("SELECT A.NAME FROM SYSGROUP A,SYSUSERGROUPREF B WHERE A.GROUPID=B.GROUPID AND B.USERID=:userid");
		Query query = session.createSQLQuery(sb.toString());
		query.setString("userid", userId);
		String str = (String) query.uniqueResult();
		map.put("group", str);
		return map;
	}
}
