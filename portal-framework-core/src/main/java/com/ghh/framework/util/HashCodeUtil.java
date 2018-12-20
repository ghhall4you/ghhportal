package com.ghh.framework.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ghh.framework.GhhException;
import com.ghh.framework.core.annotation.NotProguard;

/*****************************************************************
 *
 * 散列值生成和校验处理公共类
 *
 * @author ghh
 * @date 2018年12月19日下午10:41:51
 * @since v1.0.1
 ****************************************************************/
@NotProguard
public class HashCodeUtil {

	/**
	 * 日志记录对象
	 */
	public static Logger log = Logger.getLogger(HashCodeUtil.class);

	/**
	 * 私有化构造类，不允许通过new的方式使用
	 */
	private HashCodeUtil() {

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 获取系统资源的主键
	 * 
	 * @param parent
	 * @param location
	 * @param title
	 * @return
	 * @throws GhhException
	 */
	@SuppressWarnings("deprecation")
	public static String getSysFunctionId(String parent, String location, String title) throws GhhException {
		// return SHA1.sha1(parent+location+title+new
		// Date().getTime()+UUIDHexUtil.generate36bit()).substring(3, 35);
		return UUIDHexUtil.generate32bit();
	}

	/**
	 * 获取SYSROLEACL(角色权限表)主键
	 * 
	 * @param functionid
	 * @return
	 * @throws GhhException
	 */
	@SuppressWarnings("deprecation")
	public static String getAclResourceId(String functionid) throws GhhException {
		return SHA1.sha1(new Date().getTime() + functionid + UUIDHexUtil.generate36bit());
	}

	/**
	 * 获取系统资源的主键
	 * 
	 * @param parent
	 * @param location
	 * @param title
	 * @return
	 * @throws GhhException
	 */
	@SuppressWarnings("deprecation")
	public static String getGroupId(String parentid, String name) throws GhhException {
		// return SHA1.sha1(parentid+name+new
		// Date().getTime()+UUIDHexUtil.generate36bit()).substring(2, 34);
		return UUIDHexUtil.generate32bit();
	}

	/**
	 * 获取用户的主键
	 * 
	 * @param parent
	 * @param location
	 * @param title
	 * @return
	 * @throws GhhException
	 */
	@SuppressWarnings("deprecation")
	public static String getUserId(String loginname, String username) throws GhhException {
		// return SHA1.sha1(loginname+username+new
		// Date().getTime()+UUIDHexUtil.generate36bit()).substring(1, 33);
		return UUIDHexUtil.generate32bit();
	}

	/**
	 * 用户用户组关联表主键
	 * 
	 * @param parent
	 * @param location
	 * @param title
	 * @return
	 * @throws GhhException
	 */
	@SuppressWarnings("deprecation")
	public static String getUserGroupId() throws GhhException {
		return UUIDHexUtil.generate32bit();
	}

	/**
	 * 用户用户组关联表主键
	 * 
	 * @param parent
	 * @param location
	 * @param title
	 * @return
	 * @throws GhhException
	 */
	@SuppressWarnings("deprecation")
	public static String getRoleId(String rolename) throws GhhException {
		// return SHA1.sha1(new
		// Date().getTime()+rolename+UUIDHexUtil.generate36bit()).substring(6,
		// 38);
		return UUIDHexUtil.generate32bit();
	}

	/**
	 * 获取SYSROLEACL(角色权限表)主键
	 * 
	 * @param functionid
	 * @return
	 * @throws GhhException
	 */
	@SuppressWarnings("deprecation")
	public static String getSysAclId(String roleid) throws GhhException {
		return SHA1.sha1(new Date().getTime() + roleid + UUIDHexUtil.generate36bit());
	}

	/**
	 * 获取该Object（除hashcode属性外）的hashcode，如果该Object有hashcode属性，则同时修改该属性的值为计算所得hashcode的值
	 * 
	 * @param obj
	 *            java bean对象
	 * @return 返回obj对象的散列值
	 * @throws GhhException
	 */
	public static String getBeanHashCode(Object obj) throws GhhException {
		String rtn = "";
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propDes = beanInfo.getPropertyDescriptors();
			Arrays.sort(propDes, new Comparator<PropertyDescriptor>() {
				public int compare(PropertyDescriptor o1, PropertyDescriptor o2) {
					return (o1.getName().compareTo(o2.getName()));
				}
			});
			StringBuffer buf = new StringBuffer();
			boolean isHaveHashCodeField = false;
			PropertyDescriptor hashPro = null;
			for (int i = 0; i < propDes.length; i++) {
				PropertyDescriptor p = propDes[i];
				Object value = p.getReadMethod().invoke(obj);
				if (value != null && p.getWriteMethod() != null && !p.getName().toLowerCase().equals("hashcode")) {
					String isFormatDatePro = GlobalNames.sysConfig.get("HASH_DATE_FORMAT");
					if (isFormatDatePro != null && isFormatDatePro.equals("1") && value instanceof Date) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						if (value != null) {
							buf.append(format.format(value));
						}
					} else {
						buf.append(value.toString());
					}
				}
				if (p.getName().toLowerCase().equals("hashcode")) {
					isHaveHashCodeField = true;
					hashPro = p;
				}
			}
			rtn = SHA1.sha1(buf.toString());
			if (isHaveHashCodeField) {
				hashPro.getWriteMethod().invoke(obj, new Object[] { rtn });
			}
		} catch (Exception e) {
			throw new GhhException("生成散列值时发生错误！", e);
		}
		return rtn;
	}

	/**
	 * 验证hashcode是否有效，计算除hashcode属性外的其它所有属性的摘要信息，并与hashcode属性做对比，如果一致则返回true，否则false
	 * 
	 * @param obj
	 *            待校验散列值的对象
	 * @param cueHashCode
	 *            要进行校验对象的散列值
	 * @return true表示散列有效，否则无效
	 * @throws GhhException
	 */
	public static boolean validateHashCode(Object obj, String cueHashCode) throws GhhException {
		if (cueHashCode != null && cueHashCode.equals(HashCodeUtil.getBeanHashCode(obj))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取一个对象经过toString转换后的散列值
	 * 
	 * @param obj
	 * @return
	 */
	public static String getObjectHashCode(Object obj) {
		return SHA1.sha1(obj.toString());
	}

	/**
	 * 对属性集合进行一个排序，防止每次算出的散列值都不一致
	 * 
	 * @param propDes
	 */
	/*
	 * private static void sort(PropertyDescriptor[] propDes){ for(int
	 * i=propDes.length;i>1;i--){ for(int j=0;j<(i-1);j++){ PropertyDescriptor p
	 * = propDes[j]; PropertyDescriptor pr = propDes[j+1]; String name =
	 * p.getName(); String name2 = pr.getName(); if(name.compareTo(name2)>=0){
	 * propDes[j] = pr; propDes[j+1] = p; } } } }
	 */

}