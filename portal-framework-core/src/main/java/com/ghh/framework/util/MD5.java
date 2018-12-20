package com.ghh.framework.util;

import java.security.MessageDigest;

import com.ghh.framework.core.annotation.NotProguard;

/*****************************************************************
 *
 * MD5加密
 *
 * @author ghh
 * @date 2018年12月19日下午10:43:31
 * @since v1.0.1
 ****************************************************************/
@NotProguard
public class MD5 {

	public final static String md5(final String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.print(MD5.md5("XX")); // XX
	}

}
