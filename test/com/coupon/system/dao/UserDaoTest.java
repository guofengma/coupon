package com.coupon.system.dao;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.coupon.system.entity.User;
import com.coupon.system.service.RoleService;
import com.coupon.system.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/application-context.xml" })
public class UserDaoTest {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Test
	public void testFindByUserName() {
		User user = userService.findByUserName("222");
	}

	@Test
	public void clean() {
		List<User> users = this.userService.getAllUsers();
		for (User user : users) {
			userService.delete(user.getId());
		}
	}

}
