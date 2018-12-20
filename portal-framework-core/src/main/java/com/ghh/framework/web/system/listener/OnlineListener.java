package com.ghh.framework.web.system.listener;

import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.web.context.request.RequestContextHolder;

import com.ghh.framework.common.entities.Sysuserlog;
import com.ghh.framework.core.dao.HBUtil;
import com.ghh.framework.core.persistence.CurrentUser;
import com.ghh.framework.util.BrowserUtils;
import com.ghh.framework.util.ContextHolderUtils;
import com.ghh.framework.util.DateUtil;
import com.ghh.framework.util.GlobalNames;
import com.ghh.framework.util.ResourceUtil;
import com.ghh.framework.util.RmiServiceUtil;
import com.ghh.framework.web.system.client.ClientManager;

/*****************************************************************
 *
 * 监听在线用户上线下线
 *
 * @author ghh
 * @date 2018年12月20日下午7:04:44
 * @since v1.0.1
 ****************************************************************/
public class OnlineListener implements HttpSessionListener {

	public OnlineListener() {
	}

	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {

	}

	/**
	 * 就算网页关闭容器也会根据session超时时间来移除session
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		// 1.记录退出日志
		HttpSession session = httpSessionEvent.getSession();
		CurrentUser cUser = (CurrentUser) session.getAttribute("cUser");
		if ("1".equals(GlobalNames.sysConfig.get("SIGN_INOROUT_RECORD")) && (cUser != null)) {
			HttpServletRequest request = null;
			if (RequestContextHolder.getRequestAttributes() != null) {
				request = ContextHolderUtils.getRequest();
			}
			String broswer = null;
			if (request != null) {
				broswer = BrowserUtils.checkBrowse(request);
			} else {
				broswer = "Session超时,Server端自动退出!";
			}
			Session dbSess = HBUtil.openNewSession();
			Transaction t = dbSess.beginTransaction();
			try {
				Sysuserlog log = new Sysuserlog();
				log.setDigest("用户: " + cUser.getUsername() + " 部门：[" + cUser.getDept() + "]" + "退出系统");
				log.setLogtype(GlobalNames.LOG_TYPE_EXIT);
				log.setBzstate("0");
				log.setOpip(ResourceUtil.getIp());
				log.setBroswer(broswer);
				Date d = new Date();
				log.setYm(DateUtil.getYM(d));
				log.setOperator(cUser.getLoginname());
				log.setOptime(d);
				dbSess.save(log);
				t.commit();
			} catch (Exception e) {
				t.rollback();
				e.printStackTrace();
				GlobalNames.log.info("用户session销毁记录日志时发生异常：" + e.getMessage());
			} finally {
				dbSess.close(); // 释放资源
			}
		}

		// 2.内容中删除sessionid
		if ("true".equals(GlobalNames.sysConfig.get("PORTAL_CLUSTER_RMI_CACHE"))) {
			RmiServiceUtil.getClientRmiService().removeClinet(httpSessionEvent.getSession().getId());
		} else {
			ClientManager.getInstance().removeClinet(httpSessionEvent.getSession().getId());
		}
	}

	/**
	 * 服务器初始化
	 */
	public void contextInitialized(ServletContextEvent evt) {

	}

	public void contextDestroyed(ServletContextEvent paramServletContextEvent) {

	}

}
