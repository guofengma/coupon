package com.coupon.system.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.coupon.system.entity.base.UserEntity;

@Entity
@Table(name = "coupon_user")
public class User extends UserEntity {
	
	public List<City> getUserCityByPriority(){
		List<City> cityList = new ArrayList<City>();
		for(City temp : this.getCity()){
			cityList.add(temp);
		}
		Collections.sort(cityList);
		return cityList;
	}

	/**
	 * 给用户添加角色
	 * 
	 * @param role
	 *            要添加的角色
	 */
	public void addToRoles(Role role) {
		if (role == null) {
			return;
		}
		Set<Role> roles = getRoles();
		if (roles == null) {
			roles = new HashSet<Role>();
			setRoles(roles);
		}
		roles.add(role);
	}

	/**
	 * 获得该用户的权限
	 * 
	 * @return
	 */
	public Set<String> getPerms() {
		if (getDeleted()) {
			return null;
		}
		Set<Role> roles = getRoles();
		if (roles == null) {
			return null;
		}
		boolean isSuper = false;
		Set<String> allPerms = new HashSet<String>();
		for (Role role : getRoles()) {
			if (role.getIsSuper()) {
				isSuper = true;
				break;
			}
			allPerms.addAll(role.getPerms());
		}
		if (isSuper) {
			allPerms.clear();
			allPerms.add("*");
		}
		return allPerms;
	}
}
