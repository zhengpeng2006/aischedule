package com.asiainfo.schedule.core.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理类
 * 
 * @author LiuQQ
 * @company Asiainfo
 * @date 2014年8月29日
 * @modify
 */
public class DateUtils {

	public static Timestamp getCurrentDate() {

		return new Timestamp(System.currentTimeMillis());

	}

	public static String formatYYYYMMddHHmmss(Date date) {
		SimpleDateFormat DATA_FORMAT_yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return DATA_FORMAT_yyyyMMddHHmmss.format(date);
	}

	public static Date parseYYYYMMddHHmmss(String dateString) throws ParseException {
		SimpleDateFormat DATA_FORMAT_yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return DATA_FORMAT_yyyyMMddHHmmss.parse(dateString);
	}

}
