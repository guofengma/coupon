package com.coupon.system.dao;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.coupon.system.entity.Organization;
import com.coupon.system.entity.User;
import com.coupon.system.service.OrganizationService;
import com.coupon.system.service.RoleService;
import com.coupon.system.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/application-context.xml" })
public class UserDaoTest {

	@Autowired
	private UserService userService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private RoleService roleService;

	// @BeforeClass
	// public static void init() {
	// @SuppressWarnings("resource")
	// ApplicationContext context = new FileSystemXmlApplicationContext(
	// "classpath:config/application-context.xml");
	// // ApplicationContext context = new ClassPathXmlApplicationContext(
	// // "application-context.xml");
	// @SuppressWarnings("unused")
	// SessionFactory sessionFactory = (SessionFactory) context
	// .getBean("sessionFactory");
	// userService = context.getBean(UserService.class);
	// organizationService = context.getBean(OrganizationService.class);
	//
	// }

	@Test
	public void test1()
	{
		String temp = "12345;43256;87656788;";
		System.out.println(temp.substring(0, temp.indexOf(";")));
		temp = temp.substring(temp.indexOf(";")+1,temp.length());
		System.out.println(temp);
	}
	@Test
	public void testSaveUser() throws SQLException {
		Organization org = organizationService
				.findById("5fa94114-1e6a-47bb-aa17-27a123ff58d0");

		for (int i = 0; i < 1; i++) {
			User user = new User();
			user.setName("camille" + i);
			user.setPassword("password");
			user.setOraganzation(org);
			// 更新角色
			String[] roleIds = new String[] { "84ea9e67-2278-4317-89ed-923d2cfb0555" };
			user.getRoles().clear();// 先清空角色
			if (roleIds != null) {
				for (String id : roleIds) {
					user.addToRoles(roleService.findById(id));
				}
			}
			user = userService.save(user);
			System.out.println("userId :" + user.getId());
		}
	}

	@Test
	public void testSaveOrg() throws SQLException {
		Organization org = new Organization();
		org.setName("new org");
		org = organizationService.save(org);
		System.out.println("organizationId :" + org.getId());
	}

	@Test
	public void testGetUser() {
		User user = userService
				.findById("4163d4d5-c8fd-436b-9b76-0306908a0382");
		System.out.println("userName :" + user.getName());
		System.out.println("organizationId :"
				+ user.getOraganzation().getName());
	}

	@Test
	public void testDeleteUser() {
		String id = "90";
		userService.delete(id);
	}

	@Test
	public void testUpdateUser() {
		User user = userService
				.findById("4163d4d5-c8fd-436b-9b76-0306908a0382");
		user.setName("ndnd");
		userService.update(user);
		System.out.println("organizationId :"
				+ user.getOraganzation().getName());
	}

	@Test
	public void testGetAllUser() {

		List<User> users = userService.getAllUsers();
		System.out.println("users.size():" + users.size());
		System.out.println("users.get(0).getOraganzation().getName():"
				+ users.get(0).getOraganzation().getName());
	}

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
