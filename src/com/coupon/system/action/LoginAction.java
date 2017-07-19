package com.coupon.system.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.coupon.base.common.utils.CookieUtil;
import com.coupon.base.common.utils.UserUtil;
import com.coupon.security.MyRealm;
import com.coupon.system.entity.User;
import com.coupon.system.service.UserService;

@Controller
public class LoginAction {
	@Autowired
	UserService userService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		System.out.println("index");
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String username, String password, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) {
		UserUtil.userList = userService.getAllUsers();
		CookieUtil.delCookie(request, response, "menus");
		Subject subject = SecurityUtils.getSubject();
		SecurityUtils.getSecurityManager().logout(subject);
		// 登录后存放进shiro token
		UsernamePasswordToken token = new UsernamePasswordToken(username,
				password);
		try {
			subject.login(token);
			subject.getSession().setTimeout(1000 * 60 * 60 * 8);//将seesion过期时间设置为8小时
			return "redirect:main";
		} catch (AuthenticationException e) {
			e.printStackTrace();
			model.addAttribute("loginFlag", "failed");
			return "index";
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(String username, String password, ModelMap model,
			HttpServletRequest request) {
		System.out.println("logout");
		SecurityUtils.getSecurityManager().logout(SecurityUtils.getSubject());
		return "index";
	}

	@RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
	public void getUserInfo(ModelMap model, HttpServletResponse resp,
			HttpServletRequest request) throws IOException {
		User user = userService.findByUserName(MyRealm.hardName);
		String result = "{\"username\":\"" + user.getName()
				+ "\",\"displayName\":\"" + user.getDisplayName() + "\"}";
		resp.setContentType("application/json");
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().write(result);
	}
}
