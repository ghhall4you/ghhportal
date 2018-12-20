package com.ghh.framework.core.persistence;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ghh.framework.GhhException;
import com.ghh.framework.common.entities.Sysfunction;
import com.ghh.framework.core.dao.GhhSession;
import com.ghh.framework.util.GlobalNames;
import com.ghh.framework.util.ResourceUtil;

/*****************************************************************
 *
 * 系统功能管理持久类
 * 
 * @author ghh
 * @date 2018年12月19日下午10:33:24
 * @since v1.0.1
 ****************************************************************/
@Component("sysfunctionManager")
public class SysfunctionManager {

	@Autowired
	private GhhSession session;
	static Logger log = Logger.getLogger(SysfunctionManager.class);

	private static final ThreadLocal<String> currentRequestFunctionCache = new ThreadLocal<String>();

	public static String getCurrentRequestFunctionCache() {
		return currentRequestFunctionCache.get();
	}

	public static void setCurrentRequestFunctionCache(String reqFunction) {
		currentRequestFunctionCache.set(reqFunction);
	}

	/**
	 * 根据请求的url取sysfunction
	 * 
	 * @param reqFunction
	 * @return
	 * @throws GhhException
	 * @throws PrivilegeException
	 */
	public Sysfunction getSysFunctionByUrl(String reqFunction) throws GhhException {
		Query query = session.createQuery("from Sysfunction a where a.location=:location");
		query.setString("location", reqFunction);
		Sysfunction sysFunction = (Sysfunction) query.uniqueResult();
		if (sysFunction != null) {
			return sysFunction;
		} else {
			return null;
		}
	}

	/**
	 * 获取模块的子模块Id
	 * 
	 * @param functionId
	 * @return
	 * @throws GhhException
	 */
	@SuppressWarnings("unchecked")
	public String getButtonIdByFunctionId(String functionId) throws GhhException {
		/******************* 获取非超级权限人员的按钮id **********************/
		String superUsers = GlobalNames.sysConfig.get("PORTAL_SUPER_USERS");
		String[] usrs = superUsers.split(",");
		Boolean superFlag = false;
		String loginname = ResourceUtil.getSessionUser().getUser().getLoginname();
		for (int i = 0; i < usrs.length; i++) {
			if (loginname.equals(usrs[i])) {
				superFlag = true;
				break;
			}
		}
		if (!superFlag) {
			StringBuilder sb = new StringBuilder(900);
			sb.append(
					"SELECT X.BUTTONID FROM SYSFUNCTION X WHERE X.PARENT=:parent AND X.ACTIVE='1' AND X.TYPE='2' AND X.BUTTONID NOT IN ("
							+ "SELECT Y.BUTTONID FROM (" + "SELECT X.BUTTONID"
							+ "  FROM SYSFUNCTION X, SYSROLEACL A, SYSACT B, SYSGROUP C"
							+ " WHERE X.FUNCTIONID = A.FUNCTIONID" + "   AND B.OBJECTID = C.GROUPID"
							+ "   AND A.ROLEID = B.ROLEID ");
			sb.append(" AND C.GROUPID =" + "       (SELECT D.GROUPID" + "          FROM SYSUSERGROUPREF D"
					+ "         WHERE D.USERID = (SELECT E.USERID FROM SYSUSER E WHERE E.LOGINNAME =:loginname))");
			sb.append(" AND X.ACTIVE='1' AND X.TYPE='2' AND X.PARENT =:parent ");
			sb.append(" UNION " + "SELECT X.BUTTONID" + " FROM SYSFUNCTION X, SYSROLEACL A, SYSACT B, SYSUSER C"
					+ " WHERE X.FUNCTIONID = A.FUNCTIONID" + "   AND B.OBJECTID = C.USERID"
					+ "   AND A.ROLEID = B.ROLEID");
			sb.append("   AND C.USERID = (SELECT E.USERID FROM SYSUSER E WHERE E.LOGINNAME =:loginname)");
			sb.append(" AND X.ACTIVE='1' AND X.TYPE='2' AND X.PARENT =:parent ) Y)");
			Query query = session.createSQLQuery(sb.toString());
			query.setString("loginname", loginname);
			query.setString("parent", functionId);
			List<String> lst = query.list();
			StringBuilder btn = new StringBuilder();
			for (String str : lst) {
				btn.append(str + ",");
			}
			if (btn.length() > 0) {
				return btn.toString().substring(0, btn.length() - 1);
			}
		}
		/*****************************************/
		return null;
	}

	/**
	 * 取指定请求所在的模块sysfunction
	 * 
	 * @param currentSysfunctionid
	 * @return
	 * @throws GhhException
	 * @throws PrivilegeException
	 */
	public Sysfunction getSysfunctionById(String currentSysfunctionid) throws GhhException {
		if (currentSysfunctionid == null)
			return null;

		String funcid = currentSysfunctionid;
		while (funcid != null) {
			Sysfunction sysfunction = (Sysfunction) session.get(Sysfunction.class, funcid);
			if (sysfunction == null)
				return null;
			if (sysfunction.getType().equals("1")) {
				return sysfunction;
			} else {
				funcid = sysfunction.getParent();
			}
		}
		return null;
	}

	/**
	 * 取客户端最初请求的sysfunction
	 * 
	 * @return
	 * @throws AppException
	 * @throws PrivilegeException
	 */
	public Sysfunction getCurrentSysfunction() throws GhhException {
		return getSysFunctionByUrl(getCurrentRequestFunctionCache());
	}

	/**
	 * 取客户端最初请求所在模块的sysfunction
	 * 
	 * @return
	 * @throws GhhException
	 * @throws PrivilegeException
	 */
	public Sysfunction getModuleSysfunction() throws GhhException {
		Sysfunction curSysfunction = getCurrentSysfunction();
		if (curSysfunction == null) {
			return null;
		}
		if (curSysfunction.getType().equals("1") || curSysfunction.getType().equals("2")) {
			return curSysfunction;
		}
		return null;
	}
}
