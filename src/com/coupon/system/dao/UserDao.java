package com.coupon.system.dao;

import java.util.List;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.dao.BaseDao;
import com.coupon.system.entity.User;

public interface UserDao extends BaseDao<User, String> {

	public List<User> getAllUsers();

	public IPageList<User> getAllUsers(int pageNo, int pageSize);

	public User findByUserName(String userName);

	public List<User> findUserByName(String name);

}
