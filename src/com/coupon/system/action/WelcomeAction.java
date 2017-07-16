package com.coupon.system.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.coupon.base.action.BaseAction;
import com.coupon.system.entity.User;
import com.coupon.system.service.UserService;

@Controller
public class WelcomeAction extends BaseAction {

	@Autowired
	UserService userService;
	

	@RequestMapping(value = "/main_center", method = RequestMethod.GET)
	public String main_center() {
		System.out.println("main_center");

		return "main/main_center";
	}

	@RequestMapping(value = "/main_header", method = RequestMethod.GET)
	public String main_left() {
		System.out.println("main_header");

		return "main/main_header";
	}
	
	@RequestMapping(value = "/main_nav", method = RequestMethod.GET)
	public String main_nav() {
		System.out.println("main_nav");

		return "main/main_nav";
	}
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main(HttpServletResponse resppose,HttpServletRequest request,ModelMap model) {
		return "main";
	}

}
