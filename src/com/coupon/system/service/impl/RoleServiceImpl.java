package com.coupon.system.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coupon.base.service.impl.BaseServiceImpl;
import com.coupon.system.dao.RoleDao;
import com.coupon.system.entity.Role;
import com.coupon.system.entity.User;
import com.coupon.system.service.RoleService;
import com.coupon.system.service.UserService;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, String> implements
		RoleService {

	@Autowired
	protected void setUserDao(RoleDao roleDao) {
		setBaseDao(roleDao);
	}

	protected RoleDao getRoleDao() {
		return (RoleDao) this.baseDao;
	}
	
	@Override
	public Role save(Role role,Set<String> perms){
		role.setPerms(perms);
		this.getRoleDao().save(role);
		return role;
	}
	
	@Override
	public Role update(Role role,Set<String> perms){
		role.setPerms(perms);
		this.getRoleDao().update(role);
		return role;
	}

	@Override
	public List<Role> getList() {
		return (List<Role>) getRoleDao().getList();
	}

	@Override
	public void deleteMembers(Role role, String[] userIds) {
		if (userIds != null) {
			User user;
			for (String uid : userIds) {
				user = userService.findById(uid);
				role.deleteFromUsers(user);
			}
		}
		System.out.println(role.getUsers().size());
		getRoleDao().update(role);
	}

	@Autowired
	private UserService userService;

}
