package com.coupon.system.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.coupon.system.entity.Role;
import com.coupon.system.service.RoleService;

//用于配置spring中测试的环境
@RunWith(SpringJUnit4ClassRunner.class)
// 用于指定配置文件所在的位置
@ContextConfiguration(locations = { "classpath:config/application-context.xml" })
public class RoleServiceImplTest {

	@Resource
	private RoleService roleService;

	@Test
	public void testAddRole() {
		Role role = new Role();
		role.setName("testRole");
		role.setPriority(1);
		role.setIsSuper(false);
		String[] perms = new String[] { "perm11", "perm22", "perm33" };
		roleService.save(role, Role.splitPerms(perms));
	}

	@Test
	public void testCleanRole() {
		List<Role> roles = roleService.getList();
		for (Role role : roles) {
			roleService.delete(role.getId());
		}
	}

	@Test
	public void testDeleteMembers() {
		Role role = roleService
				.findById("9a070819-1821-4d35-99ee-0ef2dc91917e");
		String[] userIds = new String[] { "f67bc80e-a4fa-4641-8211-fd0862e3b1a0" };
		roleService.deleteMembers(role, userIds);
	}
}
