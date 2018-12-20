package com.ghh.framework.web.security.auth.controller;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ghh.framework.GhhException;
import com.ghh.framework.common.dto.AjaxJson;
import com.ghh.framework.common.dto.ClientDTO;
import com.ghh.framework.common.entities.Sysuser;
import com.ghh.framework.core.persistence.CurrentUser;
import com.ghh.framework.core.support.ControllerSupport;
import com.ghh.framework.exception.UserException;
import com.ghh.framework.util.ContextHolderUtils;
import com.ghh.framework.util.CopyIgnoreProperty;
import com.ghh.framework.util.GlobalNames;
import com.ghh.framework.util.IpUtil;
import com.ghh.framework.util.MD5;
import com.ghh.framework.util.ResourceUtil;
import com.ghh.framework.util.RmiServiceUtil;
import com.ghh.framework.web.security.auth.service.ICustomLoginService;
import com.ghh.framework.web.security.auth.service.ILoginService;
import com.ghh.framework.web.security.auth.service.ISSOLoginService;
import com.ghh.framework.web.security.rsrcfilter.service.IRsrcFilterService;
import com.ghh.framework.web.system.client.ClientManager;

/*****************************************************************
 *
 * 用户登录控制类
 *
 * @author ghh
 * @date 2018年12月19日下午10:48:42
 * @since v1.0.1
 ****************************************************************/
@Controller
@RequestMapping(value = "/loginController")
public class LoginController extends ControllerSupport<Object> {

	private Logger log = Logger.getLogger(LoginController.class);
	private final Map<String, List<Date>> failLoginRecords = new java.util.concurrent.ConcurrentHashMap<String, List<Date>>();

	@Autowired
	private ILoginService service;

	@Autowired
	private ICustomLoginService customService;

	@Autowired
	IRsrcFilterService rsrcService;

	@Autowired
	private ISSOLoginService ssoService;

	/**
	 * 授权页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "openLogin")
	public ModelAndView openLogin(HttpServletRequest request) {
		return new ModelAndView("pages/login/Login");
	}

	/**
	 * 检查用户名称
	 * 
	 * @param user
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "checkuser")
	@ResponseBody
	public AjaxJson checkuser(Sysuser user, HttpServletRequest req) throws Exception {
		AjaxJson j = new AjaxJson();
		Boolean superFlag = ssoService.isSuperUser(user.getLoginname());
		// 判断是否已加锁
		boolean isLock = service.checkUserIsLocksOrNot(user.getLoginname());
		if (isLock) {
			j.setMsg("账户已锁，请联系管理员解锁!");
			j.setSuccess(false);
			return j;
		}
		HttpSession session = ContextHolderUtils.getSession();
		String passwd = user.getPasswd();
		if ("BASE64".equals(GlobalNames.sysConfig.get("GHH_PASSWORD_ENCODE"))) {
			passwd = MD5.md5(new String(Base64.decode(passwd.getBytes("UTF-8"))));
		}
		boolean flag = service.checkUserIsExistsOrNot(user.getLoginname().replace("'", ""), passwd);// 密码由前端md5加密
		if (flag) {
			user.setPasswd(passwd);
			return psLogin(user, req, j, session);
		} else {
			j = customService.customLogin(user.getLoginname(), user.getPasswd(), req);// 客户端校验
			String customLoginName = user.getLoginname();
			if (j.isSuccess()) {
				user = (Sysuser) j.getData();
				if (user == null) {
					j.setMsg("无效的用户名或密码!");
					j.setSuccess(false);
					return j;
				}
				return psCustomLogin(user, req, j, session);
			} else {
				// failLoginRecords增加未成功登录尝试记录 2016-7-26
				return doFailLoginRecord(user, superFlag, customLoginName, j);
			}
		}
	}

	private AjaxJson doFailLoginRecord(Sysuser user, Boolean superFlag, String customLoginName, AjaxJson j)
			throws UserException {
		String loginName = user.getLoginname();
		boolean isExits = service.checkNameIsExistsOrNot(loginName);
		int count = Integer.parseInt(GlobalNames.sysConfig.get("FAILED_LOGIN_ATTEMPTS_CNTL"));
		// 如果用户名存、不是超级管理员并且开启了登录失败次数控制
		if (isExits && !superFlag && (count != 0)) {
			List<Date> dateList = null;
			if (failLoginRecords.get(loginName) == null) {
				// 第一次登录失败时，创建dateList
				dateList = new LinkedList<Date>();
			} else {
				// 第二次及以上登录失败，根据用户名获取dateList
				dateList = failLoginRecords.get(loginName);
			}
			// 将当前日期放入dateList，用以记录每次登录失败的时间
			dateList.add(new Date());
			// 将key为用户名，value为dateList的键值对放入failLoginRecords
			failLoginRecords.put(loginName, dateList);
			// 当失败次数等于sysconfig.xml中定义的次数时
			if (dateList.size() == count) {
				List<Long> timeList = new LinkedList<Long>();
				// 将每次登录失败的时间放入一个集合timeList
				for (int i = 0; i < count; i++) {
					timeList.add(dateList.get(i).getTime());
				}
				for (int i = 0; i < count; i++) {
					// 将timeList集合中最后一个时间与第一个时间作比较，如果小于60秒则锁定用户
					if ((i == 0) && ((timeList.get(count - 1) - timeList.get(i)) < 60000)) {
						j.setMsg("账户已锁，请联系管理员解锁!");
						j.setSuccess(false);
						failLoginRecords.remove(loginName);
						service.lockUser(loginName);
						return j;
					}
					// 如果上面条件不符合，则将timeList集合中最后一个时间依次与第二个、第三个...比较，并从dateList中移除超过60秒的元素
					if ((timeList.get(count - 1) - timeList.get(i)) < 60000) {
						Iterator<Date> it = dateList.iterator();
						int temp = 0;
						// 遍历dateList集合，用来删除已经超过60秒的元素
						while (it.hasNext() && (temp < i)) {
							it.next();
							it.remove();
							temp++;
						}
						break;
					}
				}
			}
			j.setMsg("无效的用户名或密码!\r\n1分钟内还有" + (count - failLoginRecords.get(customLoginName).size()) + "次机会");
			j.setSuccess(false);
			return j;
		}
		j.setMsg("无效的用户名或密码!");
		j.setSuccess(false);
		return j;
	}

	/**
	 * 本地系统用户登录前的操作
	 * 
	 * @param user
	 * @param req
	 * @param j
	 * @param session
	 * @return
	 * @throws UserException
	 */
	private AjaxJson psLogin(Sysuser user, HttpServletRequest req, AjaxJson j, HttpSession session)
			throws UserException {
		Sysuser sysUser = service.getUserByLogin(user.getLoginname(), user.getPasswd());
		Map<String, String> map = null;
		try {
			map = rsrcService.getPersonInfoByUserId(sysUser.getLoginname(), sysUser.getUserid());
		} catch (GhhException e) {
			j.setMsg("获取用户组织和角色异常！" + e.getMessage());
			j.setSuccess(false);
			return j;
		}
		String message = "用户: " + sysUser.getUsername() + ",系统角色：≮" + map.get("role") + "≯,隶属组织：【" + map.get("group")
				+ "】" + "登录成功.";
		log.info(message);
		String ipAddr = IpUtil.getIpAddr(req);
		// 用户已经登录，不允许重复登录
		ClientDTO client = null;
		if ("2".equals(GlobalNames.sysConfig.get("DUP_LOGIN_CHECK"))) {
			if ("true".equals(GlobalNames.sysConfig.get("PORTAL_CLUSTER_RMI_CACHE"))) {
				client = RmiServiceUtil.getClientRmiService().getClient(sysUser.getLoginname(), sysUser.getUsername());
			} else {
				client = ClientManager.getInstance().getClient(sysUser.getLoginname(), sysUser.getUsername());
			}
			if ((client != null) && !StringUtils.isEmpty(client.getUser().getLoginname())
					&& !client.getIp().equals(ipAddr)) {
				j.setMsg("您好，您的账号在IP地址为[" + client.getIp() + "]的机器上被登录，不能重复登录，请确认！");
				j.setSuccess(false);
				return j;
			}
		}

		client = new ClientDTO();
		client.setIp(ipAddr);
		client.setLogindatetime(new Date());
		client.setUser(sysUser);

		// 添加用户功能模块列表
		try {
			ssoService.setUserFunctionByLoginName(sysUser.getLoginname(), client);
		} catch (GhhException e) {
			throw new UserException(e.getMessage());
		}

		// 判断是否需要踢掉已经使用该账号登录的用户
		if ("1".equals(GlobalNames.sysConfig.get("DUP_LOGIN_CHECK"))) {
			if ("true".equals(GlobalNames.sysConfig.get("PORTAL_CLUSTER_RMI_CACHE"))) {
				RmiServiceUtil.getClientRmiService().removeClinet(sysUser.getLoginname(), sysUser.getUsername());
			} else {
				ClientManager.getInstance().removeClinet(sysUser.getLoginname(), sysUser.getUsername());
			}
		}

		// 增加在线用户统计
		if ("true".equals(GlobalNames.sysConfig.get("PORTAL_CLUSTER_RMI_CACHE"))) {
			RmiServiceUtil.getClientRmiService().addClinet(session.getId(), client);
		} else {
			ClientManager.getInstance().addClinet(session.getId(), client);
		}

		// 添加登陆日志
		if ("1".equals(GlobalNames.sysConfig.get("SIGN_INOROUT_RECORD"))) {
			service.addLog(sysUser.getLoginname(), message, GlobalNames.LOG_TYPE_LOGIN);
		}
		j.setMsg(message);
		j.setSuccess(true);
		return j;
	}

	/**
	 * 客户端自定义用户登录前的操作
	 * 
	 * @param user
	 * @param req
	 * @param j
	 * @param session
	 * @return
	 * @throws UserException
	 */
	private AjaxJson psCustomLogin(Sysuser user, HttpServletRequest req, AjaxJson j, HttpSession session)
			throws UserException {
		String message = "第三方系统用户: " + user.getUsername() + "登录成功.";
		log.info(message);
		String ipAddr = IpUtil.getIpAddr(req);
		// 用户已经登录，不允许重复登录
		ClientDTO client = null;
		if ("2".equals(GlobalNames.sysConfig.get("DUP_LOGIN_CHECK"))) {
			if ("true".equals(GlobalNames.sysConfig.get("PORTAL_CLUSTER_RMI_CACHE"))) {
				client = RmiServiceUtil.getClientRmiService().getClient(user.getLoginname(), user.getUsername());
			} else {
				client = ClientManager.getInstance().getClient(user.getLoginname(), user.getUsername());
			}
			if ((client != null) && !StringUtils.isEmpty(client.getUser().getLoginname())
					&& !client.getIp().equals(ipAddr)) {
				j.setMsg("您好，您的账号在IP地址为[" + client.getIp() + "]的机器上被登录，不能重复登录，请确认！");
				j.setSuccess(false);
				return j;
			}
		}

		client = new ClientDTO();
		client.setIp(ipAddr);
		client.setLogindatetime(new Date());
		client.setUser(user);

		// 判断是否需要踢掉已经使用该账号登录的用户
		if ("1".equals(GlobalNames.sysConfig.get("DUP_LOGIN_CHECK"))) {
			if ("true".equals(GlobalNames.sysConfig.get("PORTAL_CLUSTER_RMI_CACHE"))) {
				RmiServiceUtil.getClientRmiService().removeClinet(user.getLoginname(), user.getUsername());
			} else {
				ClientManager.getInstance().removeClinet(user.getLoginname(), user.getUsername());
			}
		}

		// 增加在线用户统计
		if ("true".equals(GlobalNames.sysConfig.get("PORTAL_CLUSTER_RMI_CACHE"))) {
			RmiServiceUtil.getClientRmiService().addClinet(session.getId(), client);
		} else {
			ClientManager.getInstance().addClinet(session.getId(), client);
		}

		// 添加登陆日志
		if ("1".equals(GlobalNames.sysConfig.get("SIGN_INOROUT_RECORD"))) {
			service.addLog(user.getLoginname(), message, GlobalNames.LOG_TYPE_LOGIN);
		}
		j.setMsg(message);
		j.setSuccess(true);
		return j;
	}

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @param request
	 * @param session
	 * @return
	 * @throws GhhException
	 */
	@RequestMapping(params = "login")
	public String login(HttpServletRequest request) throws GhhException {

		ClientDTO dto = ResourceUtil.getSessionUser();
		if ((dto != null) && (dto.getUser() != null)) {
			// 1.持久化人员登录管理
			CurrentUser cUser = new CurrentUser();
			CopyIgnoreProperty.copy(dto.getUser(), cUser);
			cUser.setLogindatetime(dto.getLogindatetime());
			cUser.setIp(dto.getIp());
			cUser.setId(dto.getUser().getUserid());
			HttpSession httpSession = request.getSession(true);
			httpSession.setAttribute("cUser", cUser);

			// 2.清除缓存中的登录失败的信息2016-7-26
			failLoginRecords.remove(dto.getUser().getLoginname());
			/*
			 * // 3.cookie操作 Cookie[] cookies = request.getCookies(); for
			 * (Cookie cookie : cookies) { if (cookie == null ||
			 * StringUtils.isEmpty(cookie.getName())) { continue; } }
			 */
			// 系统超级用户,拥有所有权限,欢迎页面是AMain.jsp,其他人员都进入Main.jsp
			return dispatchPage();
		} else {
			return "pages/login/Login";
		}

	}

	/**
	 * 跳转到指定页面
	 * 
	 * @return
	 * @throws GhhException
	 */
	@RequestMapping(params = "dispatchPage")
	public String dispatchPage() throws GhhException {
		return service.getDispatchPage();
	}

	@RequestMapping(params = "checkIsLoginOrNot")
	@ResponseBody
	public AjaxJson checkIsLoginOrNot(HttpServletRequest request, @RequestParam("loginname") String loginname,
			@RequestParam("username") String username) throws GhhException {
		AjaxJson json = new AjaxJson();
		ClientDTO dto = null;
		if ("true".equals(GlobalNames.sysConfig.get("PORTAL_CLUSTER_RMI_CACHE"))) {
			dto = RmiServiceUtil.getClientRmiService().getClient(request.getSession().getId());
		} else {
			dto = ClientManager.getInstance().getClient(request.getSession().getId());
		}

		if (dto == null) {
			json.setSuccess(false);
			if ("true".equals(GlobalNames.sysConfig.get("PORTAL_CLUSTER_RMI_CACHE"))) {
				dto = RmiServiceUtil.getClientRmiService().getClient(loginname, username);
			} else {
				dto = ClientManager.getInstance().getClient(loginname, username);
			}
			json.setData(dto);
			return json;
		}
		return json;
	}

	/**
	 * 退出系统
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "logout")
	public ModelAndView logout(HttpServletRequest request) throws UserException {
		ModelAndView modelAndView = new ModelAndView("pages/login/Login");
		try {
			HttpSession session = ContextHolderUtils.getSession();
			if (session == null) {
				return modelAndView;
			}
			CurrentUser cUser = (CurrentUser) session.getAttribute("cUser");
			// Assert.isNull(cUser.getUsername(), "系统退出时获取用户信息为空！");
			if ((cUser != null) && !StringUtils.isEmpty(cUser.getUsername())) {
				String message = "用户: " + cUser.getUsername() + ",退出系统.";
				log.info(message);
			}
		} catch (Exception e) {
			log.info("自动退出发生异常！");
		}
		request.getSession().invalidate();
		return modelAndView;
	}

	/**
	 * 校验session是否超时的空方法
	 * 
	 * @throws GhhException
	 */
	@RequestMapping(params = "checkSessnIsTmoutOrNot")
	@ResponseBody
	public Boolean checkSessnIsTmoutOrNot(HttpServletRequest request) throws GhhException {
		Object obj = request.getSession().getAttribute("SESSNTMOUT");
		long currentTime = new Date().getTime();
		if (obj != null) {
			if (currentTime >= Long.parseLong(obj.toString())) {
				request.getSession().invalidate();
				GlobalNames.log.error("SESSIONId = " + request.getSession().getId() + " 超时自动退出系统！");
				return false;
			}
		}
		return true;
	}

	/**
	 * 校验密码有效性
	 * 
	 * @param request
	 * @return
	 * @throws GhhException
	 */
	@RequestMapping(params = "checkValidTime")
	@ResponseBody
	public Boolean checkValidTime(HttpServletRequest request) throws GhhException {
		String loginName = request.getParameter("loginName");
		Boolean flag = service.checkValidTime(loginName);
		return flag;
	}

}
