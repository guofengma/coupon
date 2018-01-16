package com.coupon.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateUtils {

	/*
	 * 将字符串转为date类型
	 */
	public static Date strToDate (String str) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
		return sdf.parse(str);
	}
	
	/*
	 * 将字符串转为date类型
	 */
	public static Date strToMinutes (String str) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");//小写的mm表示的是分钟  
		return sdf.parse(str);
	}
}
