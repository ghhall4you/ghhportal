package com.ghh.framework.util;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.Query;
import org.hibernate.Session;

import com.ghh.framework.common.entities.Sysparam;
import com.ghh.framework.core.annotation.NotProguard;
import com.ghh.framework.core.dao.HBUtil;

/*****************************************************************
 *
 * 系统全局配置信息类
 *
 * @author ghh
 * @date 2018年12月19日下午10:41:09
 * @since v1.0.1
 ****************************************************************/
@NotProguard
public final class GlobalNames {

	// ----日志------
	public static final Logger log = Logger.getLogger(GlobalNames.class);

	public static final int LOG_TYPE_LOGIN = 1; // 登陆

	public static final int LOG_TYPE_EXIT = 2; // 退出

	public static final int LOG_TYPE_BIZ = 3; // 业务

	/**
	 * 系统参数列表，/WEB-INF/conf/SysConfig.xml中的参数全部缓存在该列表中， 以及动态读入的系统参数也加载进来
	 */
	public static final ConcurrentMap<String, String> sysConfig = new ConcurrentHashMap<String, String>();

	/**
	 * 读取配制文件，同时形成配制信息 这个在系统启动时加载
	 * 
	 * @param inputstream
	 */
	@SuppressWarnings("unchecked")
	public static final void readConfiguration(InputStream inputstream) {
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(inputstream);
			Element eles = document.getRootElement();
			for (Iterator<Element> i = eles.elementIterator(); i.hasNext();) {
				Element ele = i.next();
				sysConfig.put(ele.attribute("name").getValue(), ele.attribute("value").getValue());
				log.info("系统参数 ：" + ele.attribute("name").getValue() + " = " + ele.attribute("value").getValue());
			}
		} catch (Exception e) {
			log.info("配置系统综合参数异常：" + e.getMessage());
			return;
		}
	}

	/**
	 * 读取系统综合参数，同时形成配制信息
	 * 
	 * @param inputstream
	 */
	@SuppressWarnings("unchecked")
	public static final synchronized void readConfiguration() {
		// System.out.println("读取系统综合参数.....");
		Session session = HBUtil.openNewSession();
		try {
			// GhhSession session = HBUtil.getGhhSession();
			Query query = session.createQuery("from Sysparam a");
			List<Sysparam> lst = query.list();
			for (Sysparam param : lst) {
				System.out.println("配置 " + param.getParamcode() + ": " + param.getParamvalue());
				sysConfig.put(param.getParamcode(), param.getParamvalue());
			}
		} catch (Exception e) {
			log.info("配置系统综合参数异常：" + e.getMessage());
			e.printStackTrace();
		} finally {
			session.close(); // 释放资源
		}
		// System.out.println("读取系统综合参数，配置综合参数成功！");
	}
}
