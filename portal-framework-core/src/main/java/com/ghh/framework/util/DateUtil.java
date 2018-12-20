package com.ghh.framework.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ghh.framework.GhhException;
import com.ghh.framework.core.annotation.NotProguard;

/*****************************************************************
 *
 * 时间辅助类
 *
 * @author ghh
 * @date 2018年12月19日下午10:40:26
 * @since v1.0.1
 ****************************************************************/
@NotProguard
public class DateUtil {

	/**
	 * 日期紧凑格式
	 */
	public static final String COMPACT_DATE_FORMAT = "yyyyMMdd";

	/**
	 * 日期普通格式
	 */
	public static final String NORMAL_DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * 日期格式 年月日 时分秒
	 */
	public static final String NORMAL_DATE_FORMAT_NEW = "yyyy-mm-dd hh24:mi:ss";

	public final static String DATE_FORMAT = "yyyy-MM-dd";

	public final static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 将字符串日期转换成yyyyMMdd的形式，strDate格式必须"yyyy-MM-dd"。
	 * 将字符串日期转换成yyyyMM的形式，strDate格式必须"yyyy-MM"。
	 * 
	 * @param strDate
	 * @return
	 * @throws Exception
	 */
	public static Long strDateToNum(String strDate) throws GhhException {
		if (strDate == null) {
			return null;
		}
		String[] date = null;
		String newDate = "";
		if (strDate.indexOf("-") >= 0) {
			date = strDate.split("-");
			for (int i = 0; i < date.length; i++) {
				newDate = newDate + date[i];
			}
			return Long.parseLong(newDate);
		}

		return Long.parseLong(strDate);
	}

	/**
	 * 将字符串日期转换成yyyyMMdd的形式，strDate格式为"yyyy-MM-dd"或"yyyy-M-d"。
	 * 将字符串日期转换成yyyyMM的形式，strDate格式必须"yyyy-M"。
	 * 
	 * @param strDate
	 * @return
	 * @throws Exception
	 */
	public static Long strDateToNum1(String strDate) throws GhhException {
		if (strDate == null) {
			return null;
		}
		String[] date = null;
		String newDate = "";
		if (strDate.indexOf("-") >= 0) {
			date = strDate.split("-");
			for (int i = 0; i < date.length; i++) {
				if (date[i].length() == 1) {
					newDate = newDate + "0" + date[i];
				} else {
					newDate = newDate + date[i];
				}
			}
			return Long.parseLong(newDate);
		}

		return Long.parseLong(strDate);
	}

	/**
	 * 将数字日期转换成yyyy-MM-dd的字符串形式"。
	 * 
	 * @param numDate
	 * @return
	 */
	public static String numDateToStr(Long numDate) {
		if (numDate == null) {
			return null;
		}
		String str = numDate.toString();
		if (str.length() == 8) {
			String strDate = null;
			strDate = str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8);
			return strDate;
		}
		if (str.length() == 14) {
			String strDate = null;
			strDate = str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8) + " "
					+ str.substring(8, 10) + ":" + str.substring(10, 12) + ":" + str.substring(12, 14);
			return strDate;
		}
		return null;

	}

	// /**
	// * 将系统日期转换成yyyyMMdd的形式
	// * @return
	// * @throws Exception
	// */
	// public static Long sysDateToNum() throws GhhException
	// {
	// SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	// return strDateToNum(sdf.format(HBUtil.getSysdate()));
	// }

	/**
	 * 将传入的字符串，根据给定的格式转换为Date类型
	 * 
	 * @param str
	 *            待转换的字符串
	 * @param format
	 *            指定的格式
	 * @return 转换后的日期
	 * @throws GhhException
	 *             如果转换出错将抛出此异常
	 */
	public static Date stringToDate(String str, String format) throws GhhException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			throw new GhhException("解析日期字符串时出错！");
		}
	}

	/**
	 * 将传入的日期，根据给定的格式，格式化为字符串
	 * 
	 * @param date
	 *            需要转换的日期
	 * @param format
	 *            指定的格式
	 * @return 格式化后的字符串
	 */
	public static String dateToString(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 将字符串转换为日期类型，字符串的格式为紧凑格式，格式为 COMPACT_DATE_FORMAT
	 * 
	 * @param str
	 *            需要转换的字符串
	 * @return 转换后得到的日期
	 * @throws GhhException
	 *             转换失败将抛出此异常
	 */
	public static Date compactStringToDate(String str) throws GhhException {
		return stringToDate(str, COMPACT_DATE_FORMAT);
	}

	/**
	 * 将日期类型格式化为字符串，字符串的格式为紧凑格式，格式为 COMPACT_DATE_FORMAT
	 * 
	 * @param date
	 *            需要格式化的日期
	 * @return 格式化得到的字符
	 */
	public static String dateToCompactString(Date date) {
		return dateToString(date, COMPACT_DATE_FORMAT);
	}

	/**
	 * 将日期转换为普通日期格式字符串，字符串的格式为 NORMAL_DATE_FORMAT
	 * 
	 * @param date
	 *            需要格式化的日期
	 * @return 格式化得到的字符
	 */
	public static String dateToNormalString(Date date) {
		return dateToString(date, NORMAL_DATE_FORMAT);
	}

	/**
	 * 将日期转换为普通日期格式字符串，字符串的格式为yyyy-mm-dd hh24:mi:ss
	 * 
	 * @param date
	 *            需要格式化的日期
	 * @return 格式化得到的字符
	 */
	public static String dateToTimeString(Date date) {
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
		return df.format(date);
	}

	/**
	 * 将紧凑格式日期字符串转换为普通日期格式字符串
	 * 
	 * @param str
	 *            紧凑格式日期字符串
	 * @return 普通日期格式字符串
	 * @throws GhhException
	 *             如果转换不成功将抛出此异常
	 */
	public static String compactStringDateToNormal(String str) throws GhhException {
		return dateToNormalString(compactStringToDate(str));
	}

	/**
	 * 取二个日期之间的天数
	 * 
	 * @param date_str
	 *            起始日期
	 * @param date_end
	 *            终止日期
	 * @return 日期间天数
	 */
	public static int getDaysBetween(Date date_str, Date date_end) throws GhhException {
		Calendar d1 = Calendar.getInstance();
		Calendar d2 = Calendar.getInstance();
		d1.setTime(date_str);
		d2.setTime(date_end);
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
			throw new GhhException("起始日期小于终止日期!");
		}
		int days = d2.get(java.util.Calendar.DAY_OF_YEAR) - d1.get(java.util.Calendar.DAY_OF_YEAR);
		int y2 = d2.get(java.util.Calendar.YEAR);
		if (d1.get(java.util.Calendar.YEAR) != y2) {
			d1 = (java.util.Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
				d1.add(java.util.Calendar.YEAR, 1);
			} while (d1.get(java.util.Calendar.YEAR) != y2);
		}
		return days;
	}

	/**
	 * 日期加N天(正负天数)
	 * 
	 * @param date
	 *            日期
	 * @param days
	 *            天数
	 * @return 日期间天数
	 */
	public static Date addDays(Date date, int days) throws GhhException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int days1 = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.set(Calendar.DAY_OF_YEAR, days1 + days);
		return calendar.getTime();

	}

	/**
	 * 字符型日期加N天(正负天数)
	 * 
	 * @param str
	 *            字符型日期
	 * @param format
	 *            字符型格式(实际的字符型日期格式：yyyyMMdd yyyy-MM-dd)
	 * @param days
	 *            天数
	 * @return 日期间天数
	 */
	public static Date addDays(String str, String format, int days) throws GhhException {
		Calendar calendar = Calendar.getInstance();
		Date date = stringToDate(str, format);
		calendar.setTime(date);
		int days1 = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.set(Calendar.DAY_OF_YEAR, days1 + days);
		return calendar.getTime();

	}

	/**
	 * @$comment 将java.util.Date 转成 java.sql.Date
	 * @param date
	 *            java.util.Date
	 * @return java.sql.Date
	 * @throws GhhException
	 */
	public static java.sql.Date getSqlDate(Date date) throws GhhException {
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;
	}

	/**
	 * 将日期转换成yyyy-MM-dd格式的字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.format(date);
	}

	/**
	 * 将日期转换成年-月-日 时:分:秒格式的字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateTime(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT);
		return sdf.format(date);
	}

	/**
	 * 将格式为yyyy-MM-dd的字符串转换成日期
	 * 
	 * @param date
	 * @return
	 * @throws GhhException
	 */
	public static Date parseDate(String strDate) throws GhhException {
		if (strDate == null || strDate.trim().equals("")) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		try {
			return sdf.parse(strDate);
		} catch (ParseException e) {
			throw new GhhException("日期解析出错！", e);
		}
	}

	/**
	 * 将格式为年-月-日 时:分:秒格式的字符串转换成日期
	 * 
	 * @param strDate
	 * @return
	 * @throws Exception
	 */
	public static Date parseDateTime(String strDate) throws GhhException {
		if (strDate == null || strDate.trim().equals("")) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT);
		try {
			return sdf.parse(strDate);
		} catch (ParseException e) {
			throw new GhhException("时间解析异常！", e);
		}
	}

	/**
	 * 将字符串日期转换成yyyyMM的形式，strDate格式必须"yyyy-MM-dd"。
	 * 
	 * @param strDate
	 * @return
	 * @throws Exception
	 */
	public static Integer getYM(String strDate) throws GhhException {
		if (strDate == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Date date;
		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			throw new GhhException("时间解析异常！", e);
		}
		return getYM(date);
	}

	/**
	 * 将日期转换成yyyyMM的形式
	 * 
	 * @param date
	 * @return
	 */
	public static Integer getYM(Date date) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int yearno = cal.get(Calendar.YEAR);
		int monthno = cal.get(Calendar.MONTH) + 1;
		return new Integer(yearno * 100 + monthno);
	}

	/**
	 * @$comment 将ym向前或向后推add个月
	 * @param ym
	 *            格式为yyyymm的整型
	 * @param add
	 *            偏移量
	 * @return
	 */
	public static int addMonths(int ym, int add) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, (int) ym / 100);
		cal.set(Calendar.MONTH, ym % 100 - 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.MONTH, add);
		return getYM(cal.getTime()).intValue();

		// 下面是原方法
		// int step=1;
		// if(add<0){
		// step=-1;
		// add=Math.abs(add);
		// }
		// for(int i=0;i<add;i++){
		// int tmp=ym%100+step;
		// if(tmp==0){
		// ym=((int)(ym/100)-1)*100+12;
		// }else if(tmp==13){
		// ym=((int)(ym/100)+1)*100+1;
		// }else{
		// ym=ym+step;
		// }
		// }
		// return ym;
	}

	/**
	 * @$comment 将日期向前或向后推add个月
	 * @param oldDate
	 * @param add
	 * @return
	 */
	public static Date addMonths(Date oldDate, int add) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(oldDate);
		cal.add(Calendar.MONTH, add);
		return cal.getTime();
	}

	/**
	 * @$comment 计算两个日期（yyyymm）之间的月数差，相同日期返回0
	 * @param sym
	 *            开始年月（小）
	 * @param eym
	 *            结束年月（大）
	 * @return
	 */
	public static int monthsBetween(int sym, int eym) {
		int between = ((int) (eym / 100)) * 12 + eym % 100 - (((int) (sym / 100)) * 12 + sym % 100);
		return between;
	}

	/**
	 * @$comment 计算两个日期之间的月数差，忽略日，年月相同的日期返回0
	 * @param sdate
	 *            开始时间（小）
	 * @param edate
	 *            结束时间（大）
	 * @return
	 */
	public static int monthsBetween(Date sdate, Date edate) {
		return monthsBetween(getYM(sdate).intValue(), getYM(edate).intValue());
	}

	/**
	 * 根据日期获取中文日期
	 * 
	 * @param cal
	 * @return 日期对于的中文字符串 例如 2008年6月13日
	 */
	public static String getChineseDate(Calendar cal) {
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);

		StringBuffer sb = new StringBuffer();
		sb.append(year);
		sb.append("年");
		sb.append(month + 1);
		sb.append("月");
		sb.append(day);
		sb.append("日");

		return sb.toString();
	}

	/**
	 * 获取中文的星期
	 * 
	 * @param cal
	 * @return
	 */
	public static String getChineseWeekday(Calendar cal) {
		switch (cal.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.MONDAY:
			return "星期一";
		case Calendar.TUESDAY:
			return "星期二";
		case Calendar.WEDNESDAY:
			return "星期三";
		case Calendar.THURSDAY:
			return "星期四";
		case Calendar.FRIDAY:
			return "星期五";
		case Calendar.SATURDAY:
			return "星期六";
		case Calendar.SUNDAY:
			return "星期日";
		default:
			return "未知";
		}
	}

	/**
	 * 获取存入数据库的14位日期
	 */
	public static Long dateToNum14(Date date) {
		String ymd = new java.sql.Date(date.getTime()).toString();
		DateFormat df2 = DateFormat.getDateTimeInstance();// 可以精确到时分秒
		String d2 = df2.format(date);
		String rd = "";
		String[] s1 = ymd.split("-");
		for (int i = 0; i < s1.length; i++) {
			rd = rd + s1[i];
		}
		String[] s2 = d2.split(" ")[1].split(":");
		for (int i = 0; i < s2.length; i++) {
			if (i == 0) {
				if (Integer.parseInt(s2[i]) < 10) {
					rd = rd + "0" + s2[i];
				} else {
					rd = rd + "" + s2[i];
				}
			} else {
				rd = rd + s2[i];
			}
		}
		return Long.parseLong(rd);
	}

	public static void main(String[] args) {
		try {
			for (int i = 0; i < 100; i++) {
				Thread.sleep(1000);
				System.out.println(dateToNum14(new java.util.Date()));
			}

			System.out.println(formatDate(addMonths(parseDate("2006-01-06"), 12)));

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
