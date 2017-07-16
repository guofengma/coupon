package com.coupon.base.common.utils;

public class StringUtil {

	/**
	 * 将ID数组转换成可以直接放到查询语句中的字符串
	 * 
	 * @param ids
	 * @return
	 */
	public static String toIdString(String[] ids) {
		StringBuilder sb = new StringBuilder();
		if (ids != null && ids.length != 0) {

			for (int i = 0; i < ids.length; i++) {
				if (i != 0) {
					sb.append(",");
				}
				sb.append("'" + ids[i] + "'");
			}
		}
		return sb.toString();
	}
}
