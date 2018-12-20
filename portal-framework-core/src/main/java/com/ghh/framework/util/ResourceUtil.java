package com.ghh.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ghh.framework.GhhException;
import com.ghh.framework.common.dto.ClientDTO;
import com.ghh.framework.core.annotation.NotProguard;
import com.ghh.framework.web.system.client.ClientManager;

/*****************************************************************
 *
 * 项目资源工具类
 *
 * @author ghh
 * @date 2018年12月19日下午10:46:15
 * @since v1.0.1
 ****************************************************************/
@NotProguard
public class ResourceUtil {

	public static final ClientDTO getSessionUser() {
		HttpSession session = ContextHolderUtils.getSession();
		ClientDTO dto = null;
		if ("true".equals(GlobalNames.sysConfig.get("PORTAL_CLUSTER_RMI_CACHE"))) {
			dto = RmiServiceUtil.getClientRmiService().getClient(session.getId());
		} else {
			dto = ClientManager.getInstance().getClient(session.getId());
		}
		return dto;
	}

	/**
	 * 获得请求路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestPath(HttpServletRequest request) {
		String requestPath = request.getRequestURI() + "?" + request.getQueryString();
		if (requestPath.indexOf("&") > -1) {// 去掉其他参数
			requestPath = requestPath.substring(0, requestPath.indexOf("&"));
		}
		requestPath = requestPath.substring(request.getContextPath().length() + 1);// 去掉项目路径
		return requestPath;
	}

	public static String getSysPath() {
		String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
		String temp = path.replaceFirst("file:/", "").replaceFirst("WEB-INF/classes/", "");
		String separator = System.getProperty("file.separator");
		String resultPath = temp.replaceAll("/", separator + separator).replaceAll("%20", " ");
		return resultPath;
	}

	/**
	 * 获取项目根目录
	 * 
	 * @return
	 */
	public static String getPorjectPath() {
		String nowpath; // 当前tomcat的bin目录的路径 如
						// D:\java\software\apache-tomcat-6.0.14\bin
		String tempdir;
		nowpath = System.getProperty("user.dir");
		tempdir = nowpath.replace("bin", "webapps"); // 把bin 文件夹变到 webapps文件里面
		tempdir += "\\"; // 拼成D:\java\software\apache-tomcat-6.0.14\webapps\sz_pro
		return tempdir;
	}

	public static String getClassPath() {
		String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
		String temp = path.replaceFirst("file:/", "");
		String separator = System.getProperty("file.separator");
		String resultPath = temp.replaceAll("/", separator + separator);
		return resultPath;
	}

	public static String getSystempPath() {
		return System.getProperty("java.io.tmpdir");
	}

	public static String getSeparator() {
		return System.getProperty("file.separator");
	}

	/**
	 * 通过javascript加载模块，此方法存在问题，不能用
	 * 
	 * @param url
	 * @param title
	 * @param id
	 * @throws GhhException
	 */
	public static void loadModelScriptEngine(String url, String title, String id) throws GhhException {
		try {
			ScriptEngineManager sem = new ScriptEngineManager();
			ScriptEngine engine = sem.getEngineByName("javascript");

			// 加载一个文件
			File f = new File("");
			Reader r = new InputStreamReader(new FileInputStream(f));

			// 把 Reader 放入eval 中(读者可以去API看一下,重载了很多的eval()方法)
			engine.eval(r);
			Invocable inv = (Invocable) engine;
			Object[] args = null;
			inv.invokeFunction("logout", args);

		} catch (Exception e) {
			throw new GhhException("调用js引擎失败：" + e.getMessage());
		}

	}

	/**
	 * 获取本机IP
	 */
	public static String getIp() {
		String ip = null;
		try {
			InetAddress address = InetAddress.getLocalHost();
			ip = address.getHostAddress();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ip;
	}

}
