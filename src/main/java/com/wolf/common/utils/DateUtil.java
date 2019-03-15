package com.wolf.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static void main(String[] args) {
		System.out.println(Float.MAX_VALUE);
		System.out.println(Float.MIN_VALUE);
		System.out.println(Double.MAX_VALUE +"");
	}

	public static final String	ORACLE_DATETIME_FORMAT	= "yyyy-MM-dd HH:mm:ss";
	
	public static final String FORMAT_1 = "yyyyMMddHHmmss";

	/**
	 * 把日期类型格式化成字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String convert2String(Date date, String format) {
		SimpleDateFormat formater = new SimpleDateFormat(format);
		try {
			return formater.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 把日期字符串格式化成日期类型
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date convert2Date(String dateStr, String format) {
		if (StringUtil.isEmpty(dateStr)) {
			return null;
		}
		SimpleDateFormat simple = new SimpleDateFormat(format);
		try {
			simple.setLenient(false);
			return simple.parse(dateStr);
		} catch (Exception e) {
			return null;
		}
	}
}
