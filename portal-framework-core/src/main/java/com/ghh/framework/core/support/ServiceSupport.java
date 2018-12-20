package com.ghh.framework.core.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ghh.framework.GhhException;
import com.ghh.framework.common.dto.AjaxJson;
import com.ghh.framework.core.dao.GhhSession;

/*****************************************************************
 *
 * 业务层辅助类
 *
 * @author ghh
 * @date 2018年12月19日下午10:34:47
 * @since v1.0.1
 ****************************************************************/
@Component
public abstract class ServiceSupport {

	@Autowired
	public GhhSession session;

	/**
	 * 获取分页查询的总数
	 * 
	 * @param sql
	 * @return
	 * @throws GhhException
	 */
	private int getTotalCount(String sql, String sqlType, Map<String, Object> mp) throws GhhException {
		int totalCount = 0;
		if (sqlType.equalsIgnoreCase("hql")) {
			Query query = session.createQuery("select count(*) " + sql);
			if (mp != null) {
				Iterator<String> it = mp.keySet().iterator();
				while (it.hasNext()) {
					String obj = it.next();
					if (mp.get(obj).getClass() == String.class) {
						query.setString(obj, mp.get(obj) == null ? null : mp.get(obj).toString());
					} else if ((mp.get(obj).getClass() == Integer.class) || (mp.get(obj).getClass() == int.class)) {
						if (mp.get(obj) != null) {
							query.setInteger(obj, Integer.parseInt(mp.get(obj).toString()));
						}
					} else if ((mp.get(obj).getClass() == Short.class) || (mp.get(obj).getClass() == short.class)) {
						if (mp.get(obj) != null) {
							query.setShort(obj, Short.parseShort(mp.get(obj).toString()));
						}
					} else if ((mp.get(obj).getClass() == Float.class) || (mp.get(obj).getClass() == float.class)) {
						if (mp.get(obj) != null) {
							query.setFloat(obj, Float.parseFloat(mp.get(obj).toString()));
						}
					} else if ((mp.get(obj).getClass() == Double.class) || (mp.get(obj).getClass() == double.class)) {
						if (mp.get(obj) != null) {
							query.setDouble(obj, Double.parseDouble(mp.get(obj).toString()));
						}
					} else if ((mp.get(obj).getClass() == Long.class) || (mp.get(obj).getClass() == long.class)) {
						if (mp.get(obj) != null) {
							query.setLong(obj, Long.parseLong(mp.get(obj).toString()));
						}
					} else if (mp.get(obj).getClass() == Date.class) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
						try {
							query.setDate(obj, mp.get(obj) == null ? null : sf.parse((String) mp.get(obj)));
						} catch (ParseException e) {
							throw new GhhException(e.getMessage());
						}
					} else if ((mp.get(obj).getClass() == Boolean.class) || (mp.get(obj).getClass() == boolean.class)) {
						if (mp.get(obj) != null) {
							query.setBoolean(obj, Boolean.parseBoolean(mp.get(obj).toString()));
						}
					}
				}
			}
			totalCount = Integer.parseInt(query.uniqueResult().toString());
		} else {

			Query query = session.createSQLQuery("select count(*) from (" + sql + ") x");
			if (mp != null) {
				Iterator<String> it = mp.keySet().iterator();
				while (it.hasNext()) {
					String obj = it.next();
					if (mp.get(obj).getClass() == String.class) {
						query.setString(obj, mp.get(obj) == null ? null : mp.get(obj).toString());
					} else if ((mp.get(obj).getClass() == Integer.class) || (mp.get(obj).getClass() == int.class)) {
						if (mp.get(obj) != null) {
							query.setInteger(obj, Integer.parseInt(mp.get(obj).toString()));
						}
					} else if ((mp.get(obj).getClass() == Short.class) || (mp.get(obj).getClass() == short.class)) {
						if (mp.get(obj) != null) {
							query.setShort(obj, Short.parseShort(mp.get(obj).toString()));
						}
					} else if ((mp.get(obj).getClass() == Float.class) || (mp.get(obj).getClass() == float.class)) {
						if (mp.get(obj) != null) {
							query.setFloat(obj, Float.parseFloat(mp.get(obj).toString()));
						}
					} else if ((mp.get(obj).getClass() == Double.class) || (mp.get(obj).getClass() == double.class)) {
						if (mp.get(obj) != null) {
							query.setDouble(obj, Double.parseDouble(mp.get(obj).toString()));
						}
					} else if ((mp.get(obj).getClass() == Long.class) || (mp.get(obj).getClass() == long.class)) {
						if (mp.get(obj) != null) {
							query.setLong(obj, Long.parseLong(mp.get(obj).toString()));
						}
					} else if (mp.get(obj).getClass() == Date.class) {
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
						try {
							query.setDate(obj, mp.get(obj) == null ? null : sf.parse((String) mp.get(obj)));
						} catch (ParseException e) {
							throw new GhhException(e.getMessage());
						}
					} else if ((mp.get(obj).getClass() == Boolean.class) || (mp.get(obj).getClass() == boolean.class)) {
						if (mp.get(obj) != null) {
							query.setBoolean(obj, Boolean.parseBoolean(mp.get(obj).toString()));
						}
					}
				}
			}
			totalCount = Integer.parseInt(query.uniqueResult().toString());
		}
		return totalCount;
	}

	/**
	 * 通用的HQL分页查询
	 * 
	 * @param hql
	 * @param mp
	 * @param start
	 * @param limit
	 * @return
	 * @throws GhhException
	 */
	public AjaxJson pageHqlQuery(String hql, HashMap<String, Object> mp, Integer start, Integer limit)
			throws GhhException {
		AjaxJson dto = pageHqlQueryNoTotalCount(hql, mp, start, limit);
		dto.setTotalCount(getTotalCount(hql, "HQL", mp));
		return dto;
	}

	public AjaxJson pageHqlQueryNoTotalCount(String hql, HashMap<String, Object> mp, Integer start, Integer limit)
			throws GhhException {
		if (start == null) {
			start = 0;
		}
		AjaxJson dto = new AjaxJson();
		Query query = session.createQuery(hql);
		if (mp != null) {
			Iterator<String> it = mp.keySet().iterator();
			while (it.hasNext()) {
				String obj = it.next();
				if (mp.get(obj).getClass() == String.class) {
					query.setString(obj, mp.get(obj) == null ? null : mp.get(obj).toString());
				} else if ((mp.get(obj).getClass() == Integer.class) || (mp.get(obj).getClass() == int.class)) {
					if (mp.get(obj) != null) {
						query.setInteger(obj, Integer.parseInt(mp.get(obj).toString()));
					}
				} else if ((mp.get(obj).getClass() == Short.class) || (mp.get(obj).getClass() == short.class)) {
					if (mp.get(obj) != null) {
						query.setShort(obj, Short.parseShort(mp.get(obj).toString()));
					}
				} else if ((mp.get(obj).getClass() == Float.class) || (mp.get(obj).getClass() == float.class)) {
					if (mp.get(obj) != null) {
						query.setFloat(obj, Float.parseFloat(mp.get(obj).toString()));
					}
				} else if ((mp.get(obj).getClass() == Double.class) || (mp.get(obj).getClass() == double.class)) {
					if (mp.get(obj) != null) {
						query.setDouble(obj, Double.parseDouble(mp.get(obj).toString()));
					}
				} else if ((mp.get(obj).getClass() == Long.class) || (mp.get(obj).getClass() == long.class)) {
					if (mp.get(obj) != null) {
						query.setLong(obj, Long.parseLong(mp.get(obj).toString()));
					}
				} else if (mp.get(obj).getClass() == Date.class) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					try {
						query.setDate(obj, mp.get(obj) == null ? null : sf.parse((String) mp.get(obj)));
					} catch (ParseException e) {
						throw new GhhException(e.getMessage());
					}
				} else if ((mp.get(obj).getClass() == Boolean.class) || (mp.get(obj).getClass() == boolean.class)) {
					if (mp.get(obj) != null) {
						query.setBoolean(obj, Boolean.parseBoolean(mp.get(obj).toString()));
					}
				}
			}
		}
		dto.setData(query.setFirstResult(start).setMaxResults(limit).list());
		return dto;
	}

	/**
	 * 通用的SQL分页查询
	 * 
	 * @param sql
	 * @param hp
	 *            查询条件赋值
	 * @param start
	 * @param limit
	 * @return
	 * @throws GhhException
	 */
	public AjaxJson pageSqlQuery(String sql, HashMap<String, Object> mp, Integer start, Integer limit)
			throws GhhException {
		AjaxJson json = pageSqlQueryNoTotalCount(sql, mp, start, limit);
		json.setTotalCount(getTotalCount(sql, "SQL", mp));
		return json;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AjaxJson pageSqlQueryNoTotalCount(String sql, HashMap<String, Object> mp, Integer start, Integer limit)
			throws GhhException {
		if (start == null) {
			start = 0;
		}
		Query query = session.createSQLQuery(sql);
		if (mp != null) {
			Iterator<String> it = mp.keySet().iterator();
			while (it.hasNext()) {
				String obj = it.next();
				// query.setParameter(obj, mp.get(obj));
				if (mp.get(obj).getClass() == String.class) {
					query.setString(obj, mp.get(obj) == null ? null : mp.get(obj).toString());
				} else if ((mp.get(obj).getClass() == Integer.class) || (mp.get(obj).getClass() == int.class)) {
					if (mp.get(obj) != null) {
						query.setInteger(obj, Integer.parseInt(mp.get(obj).toString()));
					}
				} else if ((mp.get(obj).getClass() == Short.class) || (mp.get(obj).getClass() == short.class)) {
					if (mp.get(obj) != null) {
						query.setShort(obj, Short.parseShort(mp.get(obj).toString()));
					}
				} else if ((mp.get(obj).getClass() == Float.class) || (mp.get(obj).getClass() == float.class)) {
					if (mp.get(obj) != null) {
						query.setFloat(obj, Float.parseFloat(mp.get(obj).toString()));
					}
				} else if ((mp.get(obj).getClass() == Double.class) || (mp.get(obj).getClass() == double.class)) {
					if (mp.get(obj) != null) {
						query.setDouble(obj, Double.parseDouble(mp.get(obj).toString()));
					}
				} else if ((mp.get(obj).getClass() == Long.class) || (mp.get(obj).getClass() == long.class)) {
					if (mp.get(obj) != null) {
						query.setLong(obj, Long.parseLong(mp.get(obj).toString()));
					}
				} else if (mp.get(obj).getClass() == Date.class) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					try {
						query.setDate(obj, mp.get(obj) == null ? null : sf.parse((String) mp.get(obj)));
					} catch (ParseException e) {
						throw new GhhException(e.getMessage());
					}
				} else if ((mp.get(obj).getClass() == Boolean.class) || (mp.get(obj).getClass() == boolean.class)) {
					if (mp.get(obj) != null) {
						query.setBoolean(obj, Boolean.parseBoolean(mp.get(obj).toString()));
					}
				}
			}
		}
		List objLst = query.setFirstResult(start).setMaxResults(limit).list();
		List<HashMap> hpLst = new java.util.ArrayList<HashMap>();
		List<String> colLst = this.getSqlColumn(sql);
		for (int i = 0; i < objLst.size(); i++) {
			Object object = objLst.get(i);
			HashMap hp = new HashMap();
			Object[] obj;
			if (object instanceof Object[]) {
				obj = (Object[]) object;
			} else {
				obj = new Object[] { object };
			}
			if (obj.length != colLst.size()) {
				throw new GhhException("原生SQL字段解析异常，请确认！");
			}
			for (int j = 0; j < colLst.size(); j++) {
				hp.put(colLst.get(j), obj[j]);
			}
			hpLst.add(hp);
		}
		AjaxJson json = new AjaxJson();
		json.setData(hpLst);
		return json;
	}

	private List<String> getSqlColumn(String sql) {
		String s = sql.toLowerCase();
		List<String> lst = new java.util.ArrayList<String>();
		if (s.indexOf("from") != -1) {
			String s2 = s.split("from")[0].replace("select", "");
			String[] s3 = s2.trim().split(",");
			for (int i = 0; i < s3.length; i++) {
				String s4 = s3[i];
				if (s4.indexOf(" ") != -1) {
					String[] s5 = s4.split(" ");
					if (!"".equals(s5[s5.length - 1].trim())) {
						String s6 = s5[s5.length - 1].trim();
						if (s6.indexOf(".") != -1) {
							lst.add(s6.split("\\.")[1]);
						} else {
							lst.add(s6.trim());
						}
					} else {
						lst.add("nothing");
					}
				} else {
					if (s4.indexOf(".") != -1) {
						lst.add(s4.split("\\.")[1]);
					} else {
						lst.add(s4.trim());
					}
				}
			}
		}
		return lst;
	}
}
