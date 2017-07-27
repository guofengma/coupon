package com.coupon.business.action;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coupon.base.action.BaseAction;
import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageListUtil;
import com.coupon.business.entity.Customer;
import com.coupon.business.service.CustomerService;
import com.coupon.security.MyRealm;
import com.coupon.system.entity.City;
import com.coupon.system.entity.Role;
import com.coupon.system.entity.User;
import com.coupon.system.service.UserService;

@Controller
public class CustomerAction extends BaseAction{

	@Autowired UserService userService;
	
	@Autowired CustomerService customerService;
	
	@RequestMapping(value = "/business/customer/list")
	public String list(HttpServletRequest request, ModelMap model) {
		super.addMenuParams(request, model);
		int pageNo = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_NO_NAME, PageListUtil.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_SIZE_NAME, PageListUtil.DEFAULT_PAGE_SIZE);
		boolean check = request.getParameter("statu").equals("check")? true : false ;
		System.out.println(request.getParameter("statu")+check);
		User user = userService.findByUserName(MyRealm.hardName);
		StringBuilder roleString = new StringBuilder() ;
		Set<Role> roles = user.getRoles();
		for(Role temp : roles){
			roleString.append(temp.getName()+";");
		}
		if(roleString.toString().contains("管理员")){
			IPageList<Customer> customers = customerService.findByAdmin(pageNo, pageSize,check);
			model.addAttribute("customers",customers);
			return "business/customer/"+(check?"checkLsit":"uncheckList");	
		}
		if(roleString.toString().contains("大区经理")){
			IPageList<Customer> customers = customerService.findByManager(pageNo, pageSize,check,user.getCity().getId());
			model.addAttribute("customers",customers);
			return "business/customer/"+(check?"checkLsit":"uncheckList");	
		}
		if(roleString.toString().contains("员工")){
			IPageList<Customer> customers = customerService.findByStaff(pageNo, pageSize,check,user.getId());
			model.addAttribute("customers",customers);
			return "business/customer/"+(check?"checkLsit":"uncheckList");	
		}
		return "business/customer/"+(check?"checkLsit":"uncheckList");		
	}

}
