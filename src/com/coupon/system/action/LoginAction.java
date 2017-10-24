package com.coupon.system.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.coupon.base.common.utils.CookieUtil;
import com.coupon.base.common.utils.UserUtil;
import com.coupon.business.entity.Customer;
import com.coupon.business.service.CustomerService;
import com.coupon.business.service.ProductService;
import com.coupon.system.entity.User;
import com.coupon.system.service.CityService;
import com.coupon.system.service.UserService;

@Controller
public class LoginAction {
	@Autowired
	UserService userService;
	@Autowired
	CustomerService customerService;
	@Autowired
	CityService cityService;
	@Autowired
	ProductService productService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		System.out.println("index");
		return "index";
	}
	
	@RequestMapping(value = "/appindex", method = RequestMethod.GET)
	public String appindex(Model model) {
		return "appindex";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String username, String password, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
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
			User user = userService.findByUserName(username);
			if(null == user){
				model.addAttribute("loginFlag", "failed");
				return "index";
			}
			CookieUtil.addCookie(response, "isUser", "true");
			CookieUtil.addCookie(response, "name_ZN", URLEncoder.encode(user.getDisplayName(),"UTF-8"));
			CookieUtil.addCookie(response, "name_EN", URLEncoder.encode(user.getName(),"UTF-8"));
			return "redirect:main";
		} catch (AuthenticationException e) {
			e.printStackTrace();
			model.addAttribute("loginFlag", "failed");
			return "index";
		}
		/*response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(username);*/
	}
	
	@RequestMapping(value = "/applogin", method = RequestMethod.POST)
	public String applogin(String phone, String password, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		UserUtil.customerList = customerService.getAllCustomers();
		Subject subject = SecurityUtils.getSubject();
		SecurityUtils.getSecurityManager().logout(subject);
		// 登录后存放进shiro token
		UsernamePasswordToken token = new UsernamePasswordToken(phone,
				password);
		try {
			subject.login(token);
			subject.getSession().setTimeout(1000 * 60 * 60 * 8);//将seesion过期时间设置为8小时
			Customer customer = customerService.findByPhone(phone);
			if(null == customer){ //没有该客户
				model.addAttribute("loginFlag", "failed");
				return "appindex";
			}
			if(!customer.isStatu()){
				model.addAttribute("loginFlag", "uncheck");
				return "appindex";
			}
			CookieUtil.addCookie(response, "isUser", "false");
			CookieUtil.addCookie(response, "name_ZN", URLEncoder.encode(customer.getName(),"UTF-8"));
			CookieUtil.addCookie(response, "name_EN", URLEncoder.encode(customer.getPhone(),"UTF-8"));
			return "redirect:appmain";
		} catch (AuthenticationException e) {
			e.printStackTrace();
			model.addAttribute("loginFlag", "failed");
			return "appindex";
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
		User user = userService.findByUserName(CookieUtil.getCookie(request, "name_EN"));
		String result = "{\"username\":\"" + user.getName()
				+ "\",\"displayName\":\"" + user.getDisplayName() + "\"}";
		resp.setContentType("application/json");
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().write(result);
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public void test(ModelMap model, HttpServletResponse resp,
			HttpServletRequest request){
		
	}
	
	@RequestMapping(value = "/app/toPasswordPage")
	public String toPasswordPage(ModelMap model,HttpServletRequest request, HttpServletResponse response){
		String phone = CookieUtil.getCookie(request, "name_EN");
		Customer customer = customerService.findByPhone(phone);
		if(null == customer){
			return "appindex";
		}
		model.addAttribute("phone",phone);
		return "app/changePassword";
	}
	
	@RequestMapping(value = "/app/changePassword", method = RequestMethod.POST)
	public String changePassword(ModelMap model,HttpServletRequest request, HttpServletResponse response){
		String phone = CookieUtil.getCookie(request, "name_EN");
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		Customer customer = customerService.findByPhone(phone);
		if(customer.getPassword().equals(oldPassword)){//验证旧密码成功
			customer.setPassword(newPassword);
			customerService.update(customer);
			model.addAttribute("loginFlag","passwordChange");
			return "appindex";
		}else{
			model.addAttribute("status","oldPasswordError");
			return "app/changePassword";
		}
	}
	
}
