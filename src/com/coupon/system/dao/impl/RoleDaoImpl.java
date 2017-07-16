package com.coupon.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.coupon.base.dao.impl.BaseDaoImpl;
import com.coupon.system.dao.RoleDao;
import com.coupon.system.entity.Role;

@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role, String> implements RoleDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<Role> getList() {
		return (List<Role>) this.queryByHql("from Role where deleted=false order by priority",
				null);
	}

	@Override
	protected Class<Role> getEntityClass() {
		return Role.class;
	}
}
