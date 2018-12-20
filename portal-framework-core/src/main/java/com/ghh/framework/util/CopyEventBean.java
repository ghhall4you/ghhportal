package com.ghh.framework.util;

import java.util.Date;

/*****************************************************************
 *
 * 横纵表值的互相拷贝(横表即基本表，纵表即事件表)
 *
 * @author ghh
 * @date 2018年12月19日下午10:39:27
 * @since v1.0.1
 ****************************************************************/
public class CopyEventBean {

	/**
	 * @$comment 判断是否是基础类型
	 * @param cls
	 * @return true 基础类型 false 不是基础类型
	 */
	public static boolean isBasicClass(Class<?> cls) {
		if (cls == String.class) {
			return true;
		} else if (cls == Integer.class || cls == int.class) {
			return true;
		} else if (cls == Short.class || cls == short.class) {
			return true;
		} else if (cls == Float.class || cls == float.class) {
			return true;
		} else if (cls == Double.class || cls == double.class) {
			return true;
		} else if (cls == Long.class || cls == long.class) {
			return true;
		} else if (cls == Date.class) {
			return true;
		} else if (cls == Boolean.class || cls == boolean.class) {
			return true;
		}

		return false;
	}
}
