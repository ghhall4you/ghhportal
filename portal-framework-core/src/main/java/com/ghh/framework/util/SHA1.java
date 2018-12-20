package com.ghh.framework.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.MessageDigest;

import com.ghh.framework.core.annotation.NotProguard;

/*****************************************************************
 *
 * Hash算法
 *
 * @author ghh
 * @date 2018年12月19日下午10:48:00
 * @since v1.0.1
 ****************************************************************/
@NotProguard
public class SHA1 {

	public SHA1() {

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 计算某个字符串的hash码,采用SHA1算法
	 * 
	 * @param str
	 */
	public static String sha1(String str) {
		MessageDigest sha = null;
		try {
			// Hash算法
			sha = MessageDigest.getInstance("SHA-1");
			sha.update(str.getBytes());
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return "";
		}

		byte[] re = sha.digest(); // 计算消息摘要放入byte数组中

		/*
		 * 下面把消息摘要转换为16进制字符串
		 */
		String result = "";
		for (int i = 0; i < re.length; i++) {
			result += Integer.toHexString((0x000000ff & re[i]) | 0xffffff00).substring(6);
		}
		return result;
	}

	/**
	 * 获取指定文件的sha1摘要信息
	 * 
	 * @param file
	 * @throws FileNotFoundException
	 */
	public static String sha1(File file) throws IOException {
		MessageDigest sha = null;
		RandomAccessFile randomfile = new RandomAccessFile(file, "r");
		byte[] b = new byte[(int) randomfile.length()];
		randomfile.read(b);
		try {
			// Hash算法
			sha = MessageDigest.getInstance("SHA-1");
			sha.update(b);
		} catch (Exception e) {
			randomfile.close();
			System.out.print(e.getMessage());
			return "";
		}
		randomfile.close();
		byte[] re = sha.digest(); // 计算消息摘要放入byte数组中

		/*
		 * 下面把消息摘要转换为16进制字符串
		 */
		String result = "";
		for (int i = 0; i < re.length; i++) {
			result += Integer.toHexString((0x000000ff & re[i]) | 0xffffff00).substring(6);
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println(SHA1.sha1(new java.util.Date().getTime() + ""));
	}

}