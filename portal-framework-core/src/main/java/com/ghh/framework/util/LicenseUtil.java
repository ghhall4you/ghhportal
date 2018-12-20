package com.ghh.framework.util;

import java.io.InputStream;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Iterator;

import org.apache.commons.codec.binary.Base64;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ghh.framework.GhhException;

/*****************************************************************
 *
 * license校验辅助类
 *
 * @author ghh
 * @date 2018年12月19日下午10:43:14
 * @since v1.0.1
 ****************************************************************/
public class LicenseUtil {

	/**
	 * 初始化传入秘钥的参数
	 * 
	 * @return
	 * @throws GhhException
	 * @throws UnknownHostException
	 */
	public static String initKeySeed() throws Exception {
		StringBuilder builder = new StringBuilder(200);
		builder.append(GlobalNames.sysConfig.get("appcontext"));
		String ip = Inet4Address.getLocalHost().getHostAddress();
		if ("any".equals(GlobalNames.sysConfig.get("ip"))) {
			builder.append("any");
		} else {
			if (GlobalNames.sysConfig.get("ip").indexOf(ip) != -1) {
				builder.append(GlobalNames.sysConfig.get("ip"));
			}
		}
		if ("any".equals(GlobalNames.sysConfig.get("mac"))) {
			builder.append("00:00:00:00:00:00");
		} else {
			if (GlobalNames.sysConfig.get("mac").indexOf(HostUtil.getHostMacAddr()) != -1) {
				builder.append(GlobalNames.sysConfig.get("mac"));
			}
		}
		builder.append(GlobalNames.sysConfig.get("serial"));

		if ("never".equals(GlobalNames.sysConfig.get("expiration"))) {
			builder.append("9999-99-99");
		} else {
			Long expireDate = DateUtil.strDateToNum(GlobalNames.sysConfig.get("expiration"));
			Long currentDate = DateUtil.strDateToNum(DateUtil.dateToNormalString(new java.util.Date()));
			if (currentDate <= expireDate) {
				builder.append(GlobalNames.sysConfig.get("expiration"));
			}
		}
		String keySeed = MD5.md5(builder.toString());
		GlobalNames.sysConfig.put("keySeed", keySeed);
		return keySeed;
	}

	/**
	 * 使用BASE64+SHA1加密参数
	 * 
	 * @param seed
	 * @return
	 */
	private static String encrySeed(String seed) {
		return new String(Base64.encodeBase64(SHA1.sha1(seed).getBytes()));
	}

	/**
	 * 使用BASE64解密参数
	 * 
	 * @param seed
	 * @return
	 */
	private static String decrySeed(String seed) {
		return new String(Base64.decodeBase64(seed.getBytes()));
	}

	/**
	 * 校验数字签名
	 * 
	 **/
	@Deprecated
	public static boolean verify() throws Exception {
		Long expireDate = null;
		if ("never".equals(GlobalNames.sysConfig.get("expiration"))) {
			expireDate = 99999999L;
		} else {
			expireDate = DateUtil.strDateToNum(GlobalNames.sysConfig.get("expiration"));
		}
		Long currentDate = DateUtil.strDateToNum(DateUtil.dateToNormalString(new java.util.Date()));
		if (currentDate <= expireDate) {
			String sign = GlobalNames.sysConfig.get("signature");
			String initKey = GlobalNames.sysConfig.get("keySeed");
			if (decrySeed(sign).equals(SHA1.sha1(initKey))) {
				return true;
			}
		}
		return false;

	}

	/**
	 * 解析license.xml
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void parseLicenseXml() throws GhhException {
		InputStream in = LicenseUtil.class.getResourceAsStream("/conf/license.xml");
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(in);
			Element eles = document.getRootElement();
			for (Iterator<Element> i = eles.elementIterator(); i.hasNext();) {
				Element ele = i.next();
				for (Iterator<Element> j = ele.elementIterator(); j.hasNext();) {
					Element node = j.next();
					GlobalNames.sysConfig.put(node.getName(), node.getText());
				}
			}
		} catch (Exception e) {
			throw new GhhException("dom4j解析xml异常：" + e.getMessage());
		}
	}

	/**
	 * 获取随机的序列号
	 * 
	 * @return
	 */
	public static String getRandSerial() {
		StringBuilder sb = new StringBuilder(30);
		for (int i = 0; i < 6; i++) {
			switch (i) {
			case 0:
				sb.append(RandCodeImageEnum.UPPER_CHAR.generateStr(1) + RandCodeImageEnum.LOWER_CHAR.generateStr(1)
						+ "-");
				break;
			case 1:
				sb.append(RandCodeImageEnum.LOWER_CHAR.generateStr(1) + RandCodeImageEnum.LOWER_CHAR.generateStr(1)
						+ "-");
				break;
			case 2:
				sb.append(RandCodeImageEnum.UPPER_CHAR.generateStr(1) + RandCodeImageEnum.LOWER_CHAR.generateStr(1)
						+ "-");
				break;
			case 3:
				sb.append(RandCodeImageEnum.UPPER_CHAR.generateStr(1) + RandCodeImageEnum.UPPER_CHAR.generateStr(1)
						+ "-");
				break;
			case 4:
				sb.append(RandCodeImageEnum.LOWER_CHAR.generateStr(1) + RandCodeImageEnum.LOWER_CHAR.generateStr(1)
						+ "-");
				break;
			default:
				sb.append(RandCodeImageEnum.NUMBER_CHAR.generateStr(15));
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		// System.out.println("xml[serial]：" + getRandSerial());

		long beg = System.currentTimeMillis();
		String seed = null;
		try {
			parseLicenseXml();
			seed = initKeySeed();
		} catch (Exception e1) {
		}
		System.out.println("原文：" + seed);
		String sign = encrySeed(seed);
		System.out.println("签名：" + sign);

		System.out.println("签名解密：" + decrySeed(sign));
		System.out.println("原文SHA加密：" + SHA1.sha1(seed));
		System.out.println("验证结果：" + decrySeed(sign).equals(SHA1.sha1(seed)));
		long end = System.currentTimeMillis();
		System.out.println(end - beg);
	}
}
