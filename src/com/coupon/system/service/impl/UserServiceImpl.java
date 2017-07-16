package com.coupon.system.service.impl;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.dao.BaseDao;
import com.coupon.base.service.impl.BaseServiceImpl;
import com.coupon.system.dao.UserDao;
import com.coupon.system.entity.User;
import com.coupon.system.service.UserService;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, String> implements
		UserService {
	@Autowired
	protected void setUserDao(UserDao userDao) {
		setBaseDao(userDao);
	}

	protected UserDao getUserDao() {
		return (UserDao) this.baseDao;
	}

	@Override
	public List<User> getAllUsers() {
		return (List<User>) getUserDao().getAllUsers();
	}

	@Override
	public IPageList<User> getAllUsers(int pageNo, int pageSize) {
		return (IPageList<User> ) getUserDao().getAllUsers(pageNo,pageSize);
	}
	
	@Override
	public User findByUserName(String userName){
		return getUserDao().findByUserName(userName);
	}

	@Override
	public List<User> findUserByName(String name) {
		// TODO Auto-generated method stub
		return getUserDao().findUserByName(name);
	}
}
