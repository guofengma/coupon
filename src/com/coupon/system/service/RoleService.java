package com.coupon.system.service;

import java.util.List;
import java.util.Set;

import com.coupon.base.service.BaseService;
import com.coupon.system.entity.Role;

public interface RoleService extends BaseService<Role, String> {

	/**
	 * 获得角色列表
	 * 
	 * @return
	 */
	public List<Role> getList();

	/**
	 * 删除角色中的成员
	 * 
	 * @param role
	 *            角色
	 * @param userIds
	 *            要删除的成员id
	 */
	public void deleteMembers(Role role, String[] userIds);

	/**
	 * 保存角色
	 * 
	 * @param role
	 *            角色实体
	 * @param perms
	 *            角色对应的权限集合
	 * @return
	 */
	public Role save(Role role, Set<String> perms);

	/**
	 * 编辑角色
	 * 
	 * @param role
	 *            角色实体
	 * @param perms
	 *            角色对应的权限集合
	 * @return
	 */
	public Role update(Role role, Set<String> perms);

}
