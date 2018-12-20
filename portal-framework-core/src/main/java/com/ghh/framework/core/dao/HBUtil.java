package com.ghh.framework.core.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ghh.framework.GhhException;

/*****************************************************************
 *
 * spring容器BEAN获取
 *
 * @author ghh
 * @date 2018年12月19日下午10:28:02
 * @since v1.0.1
 ****************************************************************/
public final class HBUtil {

	private HBUtil() {
		throw new Error("请不要实例化 HBUtil");
	}

	public static GhhSession getGhhSession() {
		// WebApplicationContext ctx =
		// ContextLoader.getCurrentWebApplicationContext();
		return (GhhSession) BeanUtils.getBean("ghhSession");
	}

	public static Session openNewSession() {
		return getGhhSession().openNewSession();
	}

	/**
	 * 取序列发生器的下一个值
	 * 
	 * @param sequenceName
	 * @return
	 * @throws Exception
	 */
	public static String getSequence(String sequenceName) throws GhhException {
		GhhSession session = getGhhSession();
		Query query = session.createSQLQuery("Select " + sequenceName + ".nextval from dual");
		return query.uniqueResult().toString();
	}

}
