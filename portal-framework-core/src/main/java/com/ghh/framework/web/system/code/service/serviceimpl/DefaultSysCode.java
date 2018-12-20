package com.ghh.framework.web.system.code.service.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ghh.framework.GhhException;
import com.ghh.framework.common.entities.VSyscode;
import com.ghh.framework.core.dao.GhhSession;
import com.ghh.framework.web.system.code.service.AbsSysCode;

/*****************************************************************
 *
 * 默认的系统数据字典类
 *
 * @author ghh
 * @date 2018年12月20日下午7:03:08
 * @since v1.0.1
 ****************************************************************/
@Service("DefaultSysCode")
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class DefaultSysCode extends AbsSysCode {

	@Autowired
	private GhhSession session;

	@SuppressWarnings("unchecked")
	public List<VSyscode> getCodeList(String codeType, String filter) {
		if (codeType == null || codeType.equals(""))
			return null;
		String hql = "from VSyscode a where a.code='" + codeType.toUpperCase() + "'";
		if (filter != null && !filter.equals("")) {
			hql = hql + " and (" + filter + ")";
		}
		hql = hql + " order by a.codevalue";
		List<VSyscode> codeList = session.createQuery(hql).list();
		return codeList;
	}

	@SuppressWarnings("unchecked")
	public String getValueByCode(String codeType, String code) {
		if (codeType == null || codeType.equals(""))
			return code;
		if (code == null || code.equals(""))
			return code;
		String hql = "from VSyscode a where a.code='" + codeType.toUpperCase() + "' and a.codevalue='" + code + "'";
		List<VSyscode> codeList = session.createQuery(hql).list();
		if (codeList != null && codeList.size() > 0) {
			VSyscode codeObj = (VSyscode) codeList.get(0);
			return codeObj.getCodeexplain();
		} else {
			return code;
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>> getAllCodeMap() throws GhhException {
		String hql = " from VSyscode a where a.codestate = '1' order by a.code asc,a.codevalue asc";
		List<VSyscode> codeList = session.createQuery(hql).list();
		Map<String, Map<String, String>> ddMap = new HashMap<String, Map<String, String>>();
		Map<String, String> hp = null;
		String code = null;
		for (int i = 0; i < codeList.size(); i++) {
			VSyscode aa10 = (VSyscode) codeList.get(i);
			if (code == null) {
				code = aa10.getCode();
				hp = new TreeMap<String, String>();
			}
			if (!code.equals(aa10.getCode())) {
				ddMap.put(code, hp);
				code = aa10.getCode();
				hp = new TreeMap<String, String>();
			}
			hp.put(aa10.getCodevalue(), aa10.getCodeexplain());
			if (i == codeList.size() - 1 && code.equals(aa10.getCode())) {
				ddMap.put(code, hp);
			}
		}
		return ddMap;
	}

}
