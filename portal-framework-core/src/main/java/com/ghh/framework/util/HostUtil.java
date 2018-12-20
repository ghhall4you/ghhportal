package com.ghh.framework.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.ghh.framework.core.annotation.NotProguard;

/*****************************************************************
 *
 * 服务器主机辅助类
 *
 * @author ghh
 * @date 2018年12月19日下午10:42:11
 * @since v1.0.1
 ****************************************************************/
@NotProguard
public abstract class HostUtil {

	public static String getHostMacAddr() throws SocketException, UnknownHostException {
		// 获得IP
		NetworkInterface netInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
		// 获得Mac地址的byte数组
		byte[] macAddr = netInterface.getHardwareAddress();
		// 循环输出
		StringBuilder mac = new StringBuilder(18);
		for (byte b : macAddr) {
			mac.append(toHexString(b) + ":");
		}
		return mac.substring(0, 17).toString();
	}

	private static String toHexString(int integer) {
		// 将得来的int类型数字转化为十六进制数
		String str = Integer.toHexString((int) (integer & 0xff));
		// 如果遇到单字符，前置0占位补满两格
		if (str.length() == 1) {
			str = "0" + str;
		}
		return str;
	}
}
