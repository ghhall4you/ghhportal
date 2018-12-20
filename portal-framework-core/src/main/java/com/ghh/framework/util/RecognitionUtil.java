package com.ghh.framework.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ghh.framework.core.annotation.NotProguard;

/*****************************************************************
 *
 * 字符串内容识别辅助类
 *
 * @author ghh
 * @date 2018年12月19日下午10:45:42
 * @since v1.0.1
 ****************************************************************/
@NotProguard
public class RecognitionUtil {

	private RecognitionUtil() {
	}

	/**
	 * 检测是否存在有中文字符
	 * 
	 * @param str
	 *            待检测的字符串
	 * @return 如果检测含有一个或多个中文字符,则返回true,否则,返回false
	 */
	public static boolean containsChinese(String str) {
		// 对每一个字符进行检测
		for (int i = 0; i < str.length(); i++) {
			// 如果字符在此区间，则认为含有中文字符，返回true
			if (str.substring(i, i + 1).matches("[\\u4e00-\\u9fa5]+"))
				return true;
		}
		return false;
	}

	/**
	 * 检测是否属于邮箱
	 * 
	 * @param str
	 *            待检测的字符串
	 * @return 如果检测符合邮箱格式,则返回true,否则,返回false
	 */
	public static boolean checkEmail(String str) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(str);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 检测是否含有@符号
	 * 
	 * @param str
	 *            待检测的字符串
	 * @return 如果检测符合邮箱格式,则返回true,否则,返回false
	 */
	public static boolean containsEmailSymbol(String str) {
		return str.indexOf("@") != -1;
	}

}
