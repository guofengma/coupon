package com.coupon.system.action;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.coupon.base.action.BaseAction;
import com.coupon.base.common.utils.CookieUtil;
import com.coupon.business.entity.Activity;
import com.coupon.business.entity.Customer;
import com.coupon.business.entity.Product;
import com.coupon.business.service.ActivityService;
import com.coupon.business.service.CustomerService;
import com.coupon.business.service.ProductService;
import com.coupon.security.MyRealm;
import com.coupon.system.entity.City;
import com.coupon.system.service.CityService;
import com.coupon.system.service.UserService;

@Controller
public class WelcomeAction extends BaseAction {

	@Autowired
	UserService userService;
	@Autowired
	CustomerService customerService;
	@Autowired
	CityService cityService;
	@Autowired
	ProductService productService;
	@Autowired
	ActivityService activityService;
	

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
	public String main(HttpServletResponse response,HttpServletRequest request,ModelMap model) {
		return "main";
	}

	@RequestMapping(value = "/appmain", method = RequestMethod.GET)
	public String appmain(HttpServletResponse resppose,HttpServletRequest request,ModelMap model) {
		List<City> citys = new ArrayList<City>();
		List<Product> product = new ArrayList<Product>();
		if(CookieUtil.getCookie(request , "name_EN") == null){//未登录，显示所有product
			citys = cityService.getCityUsed();
		}
		else{
			Customer customer = customerService.findByPhone(CookieUtil.getCookie(request , "name_EN"));
			citys = new ArrayList(customer.getCity().size()==0?cityService.getCityUsed():customer.getCity());
		}
		product = productService.findProductByCityIds(cityToStringIds(citys));
		List<Activity> activitys = activityService.findAll();
		model.addAttribute("activitys",activitys);
		model.addAttribute("productsAll",product);
		return "appmain";
	}
	
	@RequestMapping(value = "/app/myInfo", method = RequestMethod.GET)
	public String myInfo(HttpServletResponse resppose,HttpServletRequest request,ModelMap model) {
		String name = CookieUtil.getCookie(request , "name_EN") ;
		Customer customer = customerService.findByPhone(name);
		if(null == customer)
			return "appindex";//未登录，跳转登录页面
		model.addAttribute("customer",customer);
		return "app/myInfo";
	}
	
	private String[] cityToStringIds(List<City> citys){
		StringBuilder result = new StringBuilder();
		for(City temp : citys){
			result.append(temp.getId()+";");
		}
		return result.toString().split(";");
	}
}
