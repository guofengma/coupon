package com.coupon.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageListUtil;
import com.coupon.base.dao.impl.BaseDaoImpl;
import com.coupon.system.dao.UserDao;
import com.coupon.system.entity.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User, String> implements UserDao {
	public User saveUser(User user) {
		return save(user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsers() {
		return (List<User>) this.queryByHql("from User where deleted=false",
				null);
	}

	@Override
	public IPageList<User> getAllUsers(int pageNo, int pageSize) {
		int first = (pageNo - 1) * pageSize;
		List<User> items = this.queryByHql(
				"from User where deleted=false and name!='admin' order by createTime desc", null,
				first, pageSize);
		// int count = this.queryByHql("select count(*) from User where
		// deleted=false", null).indexOf(0);
		int count = Integer.parseInt(this.findUnique(
				"select count(*) from User where deleted=false and name!='admin'", null)
				.toString());
		System.out.println("count: " + count);
		// http://www.cnblogs.com/mabaishui/archive/2009/10/16/1584510.html
		return PageListUtil.getPageList(count, pageNo, items, pageSize);
	}

	@Override
	public User findByUserName(String userName) {
		String hql = "from User where deleted=false and name=?";
		User user = (User) this.findUnique(hql, userName);
		return user;
	}

	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}

	@Override
	public List<User> findUserByName(String name) {
		// TODO Auto-generated method stub
		 List<User> users = this.queryByHql(
				"from User where deleted=false and name='"+name+"' order by createTime desc", null
				);
		 return users;
	}
}
