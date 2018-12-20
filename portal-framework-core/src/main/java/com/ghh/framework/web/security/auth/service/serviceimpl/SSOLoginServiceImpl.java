package com.ghh.framework.web.security.auth.service.serviceimpl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ghh.framework.GhhException;
import com.ghh.framework.common.dto.ClientDTO;
import com.ghh.framework.common.entities.Sysfunction;
import com.ghh.framework.core.support.ServiceSupport;
import com.ghh.framework.util.GlobalNames;
import com.ghh.framework.util.UUIDHexUtil;
import com.ghh.framework.web.security.auth.service.ISSOLoginService;
import com.ghh.framework.web.security.rsrcfilter.service.IRsrcFilterService;

/*****************************************************************
 *
 * 单点登录接口实现类，作用是对外提供单点登录到Portal的功能
 *
 * @author ghh
 * @date 2018年12月19日下午10:51:02
 * @since v1.0.1
 ****************************************************************/
@Service
public class SSOLoginServiceImpl extends ServiceSupport implements ISSOLoginService {

	private final Map<String, String> tokenForSSO = new ConcurrentHashMap<>();

	@Autowired
	private IRsrcFilterService rsrcFilterService;

	@Override
	public String getToken(String loginName) throws GhhException {
		@SuppressWarnings("deprecation")
		String token = UUIDHexUtil.generate32bit();// 动态生成，设置有效时间
		tokenForSSO.put(loginName, token);
		return token;
	}

	@Override
	public Boolean checkToken(String loginName, String token) throws GhhException {
		if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(token)) {
			return false;
		}
		if(token.equals(tokenForSSO.get(loginName))){
			return true;
		}
		return false;
	}

	@Override
	public Boolean isSuperUser(String loginName) throws GhhException {
		String superUsers = GlobalNames.sysConfig.get("PORTAL_SUPER_USERS");
		String[] usrs = superUsers.split(",");
		for (int i = 0; i < usrs.length; i++) {
			if (loginName.equals(usrs[i])) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void setUserFunctionByLoginName(String loginName, ClientDTO dto) throws GhhException {
		List<Sysfunction> sysfunctionList = null;
		try {
			sysfunctionList = rsrcFilterService.getNavInfoByLoginname(loginName, IRsrcFilterService.RSRC_CONT_BTN_FLAG);
		} catch (GhhException e) {
			throw new GhhException(e.getMessage());
		}

		Set<String> sysfunctionSet = new HashSet<String>();
		if ((sysfunctionList != null) && (sysfunctionList.size() > 0)) {
			for (Sysfunction sysfunction : sysfunctionList) {
				sysfunctionSet.add(sysfunction.getLocation());
			}
		}
		dto.setFuncLocations(sysfunctionSet);
	}

	@Override
	public String getLocationByUrlParam(String urlParam) throws GhhException {
		Query query = session.createQuery("from Sysfunction a where a.location like :location");
		query.setString("location", "%" + urlParam + "%");
		Sysfunction function = (Sysfunction) query.uniqueResult();
		if (function != null) {
			return function.getLocation();
		}
		return null;
	}

}
