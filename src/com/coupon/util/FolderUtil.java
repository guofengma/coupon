package com.coupon.util;

import java.util.Calendar;

public class FolderUtil {

	/*
	 * 生成文件夹 以年月日命名****-**-**
	 */
	public static String getFolder (){
		Calendar now = Calendar.getInstance(); 
		String year = now.get(Calendar.YEAR)+"";
		String month = (now.get(Calendar.MONTH) + 1)<10? "0"+(now.get(Calendar.MONTH) + 1):""+(now.get(Calendar.MONTH) + 1);
		String day = now.get(Calendar.DAY_OF_MONTH)<10? "0"+now.get(Calendar.DAY_OF_MONTH):""+now.get(Calendar.DAY_OF_MONTH);
		return year + "-" + month + "-" + day ;
	}
}
