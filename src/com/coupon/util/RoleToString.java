package com.coupon.util;

import java.util.Set;

import com.coupon.system.entity.Role;

public class RoleToString {

	public static String roleToString (Set<Role> roles){
		StringBuilder sb = new StringBuilder("");
		if(null == roles||roles.size()==0){
			
		}
		else{
			for(Role role : roles){
				sb.append(role.getName()+";");
			}
		}
		return sb.toString();
	}
}
