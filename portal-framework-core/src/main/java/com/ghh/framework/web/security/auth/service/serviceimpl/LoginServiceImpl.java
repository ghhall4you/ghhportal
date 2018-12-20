package com.ghh.framework.web.security.auth.service.serviceimpl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghh.framework.GhhException;
import com.ghh.framework.common.entities.Sysuser;
import com.ghh.framework.common.entities.Sysuserlog;
import com.ghh.framework.core.dao.GhhSession;
import com.ghh.framework.exception.UserException;
import com.ghh.framework.util.BrowserUtils;
import com.ghh.framework.util.ContextHolderUtils;
import com.ghh.framework.util.DateUtil;
import com.ghh.framework.util.GlobalNames;
import com.ghh.framework.util.ResourceUtil;
import com.ghh.framework.web.security.auth.service.ICustomDispatchPageService;
import com.ghh.framework.web.security.auth.service.ILoginService;

/*****************************************************************
 *
 * 登录验证接口实现类
 *
 * @author ghh
 * @date 2018年12月19日下午10:50:34
 * @since v1.0.1
 ****************************************************************/
@Service
public class LoginServiceImpl implements ILoginService {

	@Autowired
	private GhhSession session;

	@Autowired
	private ICustomDispatchPageService dispatchPageService;

	@Override
	public boolean checkUserIsExistsOrNot(String loginname, String passwd) throws UserException {
		Query query = session.createSQLQuery(
				"select count(*) from Sysuser a where a.loginname =:loginname and a.passwd =:passwd  and useful='1' ");
		query.setString("loginname", loginname);
		query.setString("passwd", passwd);
		int count = Integer.parseInt(query.uniqueResult().toString());
		if (count == 1) {
			return true;
		}
		return false;
	}

	@Override
	public Sysuser getUserByLogin(String loginname, String passwd) throws UserException {
		Query query = session.createQuery("from Sysuser a where a.loginname =:loginname and a.passwd =:passwd ");
		query.setString("loginname", loginname);
		query.setString("passwd", passwd);
		Sysuser user = (Sysuser) query.uniqueResult();
		return user;
	}

	@Override
	public Sysuser getUserByLoginName(String loginname) throws UserException {
		Query query = session.createQuery("from Sysuser a where a.loginname =:loginname");
		query.setString("loginname", loginname);
		Sysuser user = (Sysuser) query.uniqueResult();
		return user;
	}

	@Override
	@Transactional
	public void addLog(String loginname, String logContent, int logTpye) throws UserException {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String broswer = BrowserUtils.checkBrowse(request);
		Sysuserlog log = new Sysuserlog();
		log.setDigest(logContent);
		log.setLogtype(logTpye);
		log.setBzstate("0");
		log.setOpip(ResourceUtil.getIp());
		log.setBroswer(broswer);
		Date d = new Date();
		log.setYm(DateUtil.getYM(d));
		log.setOperator(loginname);
		log.setOptime(d);
		session.save(log);
	}

	@Override
	public boolean checkNameIsExistsOrNot(String loginName) {
		String hql = "FROM Sysuser where loginname =:loginname";
		Sysuser user = (Sysuser) session.createQuery(hql).setString("loginname", loginName).uniqueResult();
		if (user != null) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean checkValidTime(String loginName) {
		String pswTmoutCntl = GlobalNames.sysConfig.get("PSW_TMOUT_CNTL");
		String hql = "FROM Sysuser where loginname =:loginname";
		Sysuser user = (Sysuser) session.createQuery(hql).setString("loginname", loginName).uniqueResult();
		Date now = new Date();
		if (user != null) {
			if ((user.getPswinvalidtime() == null) || ((pswTmoutCntl != null) && "0".equals(pswTmoutCntl))) {
				// 如果用户的密码有效时间为null或者不限制密码有效期，则返回true
				return true;
			}
			// 如果用户的密码有效时间大于当前时间，则返回true
			return user.getPswinvalidtime().after(now);
		}
		return false;
	}

	@Override
	@Transactional
	public void lockUser(String loginName) throws UserException {
		String hql = "FROM Sysuser where loginname =:loginname";
		Sysuser user = (Sysuser) session.createQuery(hql).setString("loginname", loginName).uniqueResult();
		if (user != null) {
			user.setAcctlockflag("1");// 锁
			session.update(user);
		}
	}

	@Override
	public boolean checkUserIsLocksOrNot(String loginname) {
		String hql = "FROM Sysuser where loginname =:loginname";
		Sysuser user = (Sysuser) session.createQuery(hql).setString("loginname", loginname).uniqueResult();
		if (user != null) {
			if ("1".equals(user.getAcctlockflag())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getDispatchPage() throws GhhException {
		try {
			String superUsers = GlobalNames.sysConfig.get("PORTAL_SUPER_USERS");
			String homePage = GlobalNames.sysConfig.get("PORTAL_HOME_PAGE");
			String loginName = ResourceUtil.getSessionUser().getUser().getLoginname();
			String userId = ResourceUtil.getSessionUser().getUser().getUserid();
			if (StringUtils.isEmpty(loginName)) {
				return "pages/login/Login";
			}
			String[] usrs = superUsers.split(",");
			Boolean superFlag = false;
			for (int i = 0; i < usrs.length; i++) {
				if (loginName.equals(usrs[i])) {
					superFlag = true;
					break;
				}
			}
			if (!StringUtils.isEmpty(homePage)) {
				return homePage;
			} else {
				if (superFlag) {
					return "AMain";
				} else {
					String customDispatchPage = dispatchPageService.getCustomDispatchPage(userId, loginName);
					if (!StringUtils.isEmpty(customDispatchPage)) {
						return customDispatchPage;
					} else {
						return "Main";
					}
				}
			}
		} catch (Exception e) {
			return "pages/login/Login";
		}
	}

}
