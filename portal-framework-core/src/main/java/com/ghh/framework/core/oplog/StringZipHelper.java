package com.ghh.framework.core.oplog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/*****************************************************************
 *
 * 把字符串使用ZIP压缩和解压缩的代码
 *
 * @author ghh
 * @date 2018年12月19日下午10:31:31
 * @since v1.0.1
 ****************************************************************/
public final class StringZipHelper {

	public static byte[] zipString(String str) {
		try {
			ByteArrayOutputStream bos = null;
			GZIPOutputStream os = null;
			byte[] bs = null;
			try {
				bos = new ByteArrayOutputStream();
				os = new GZIPOutputStream(bos);
				os.write(str.getBytes());
				os.close();
				bos.close();
				bs = bos.toByteArray();
				return bs;
			} finally {
				bs = null;
				bos = null;
				os = null;
			}
		} catch (Exception ex) {
			return null;
		}
	}

	public static String unzipString(byte[] b) {
		ByteArrayInputStream bis = null;
		ByteArrayOutputStream bos = null;
		GZIPInputStream is = null;
		byte[] buf = null;
		try {
			bis = new ByteArrayInputStream(b);
			bos = new ByteArrayOutputStream();
			is = new GZIPInputStream(bis);
			buf = new byte[1024];
			int len;
			while ((len = is.read(buf)) != -1) {
				bos.write(buf, 0, len);
			}
			is.close();
			bis.close();
			bos.close();
			return new String(bos.toByteArray());
		} catch (Exception ex) {
			return null;
		} finally {
			bis = null;
			bos = null;
			is = null;
			buf = null;
		}
	}

}
