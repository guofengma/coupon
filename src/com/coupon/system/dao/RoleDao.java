package com.coupon.system.dao;

import java.util.List;

import com.coupon.base.dao.BaseDao;
import com.coupon.system.entity.Role;

public interface RoleDao extends BaseDao<Role, String> {

	public List<Role> getList();

}
