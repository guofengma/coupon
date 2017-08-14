package com.coupon.util;

import java.util.Calendar;
import java.util.Random;

public class RandomCode {
	
	 private static String base = "abcdefghijklmnopqrstuvwxyz0123456789";
	
	/*
	 * 生成18位随机码 前八位位20170906ka/20170906mi
	 */
	public static String generate_18 (String type){
		Calendar now = Calendar.getInstance(); 
		String year = now.get(Calendar.YEAR)+"";
		String month = (now.get(Calendar.MONTH) + 1)<10? "0"+(now.get(Calendar.MONTH) + 1):""+(now.get(Calendar.MONTH) + 1);
		String day = now.get(Calendar.DAY_OF_MONTH)<10? "0"+now.get(Calendar.DAY_OF_MONTH):""+now.get(Calendar.DAY_OF_MONTH);
		String prefix = year + month + day;
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer(prefix+type);     
	    for (int i = 0; i < 8; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
	}
}
