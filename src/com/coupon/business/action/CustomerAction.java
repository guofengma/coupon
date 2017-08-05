package com.coupon.business.action;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.coupon.system.service.CityService;
import com.coupon.system.service.UserService;

@Controller
public class CustomerAction extends BaseAction{

	@Autowired UserService userService;
	
	@Autowired CustomerService customerService;
	
	@Autowired CityService cityService;
	
	@RequestMapping(value = "/business/customer/list")
	public String list(HttpServletRequest request, ModelMap model) {
		super.addMenuParams(request, model);
		List<City> fCityUsedList = cityService.getFCityUsed();
		model.addAttribute("fCityUsedList",fCityUsedList);
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
			return "business/customer/"+(check?"checkList":"uncheckList");	
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
	
	
	/*
	 * 单个审核新增的用户
	 */
	@RequestMapping(value = "/business/customer/check")
	public String check(HttpServletRequest request, ModelMap model) {
		String id = request.getParameter("id");
		Customer customer = customerService.findById(id);
		customer.setStatu(true);
		customerService.update(customer);
		model.addAttribute("statu",false);
		return "redirect:list";
	}
	
	/*
	 * 批量审核新增的用户
	 */
	@RequestMapping(value = "/business/customer/multiCheck")
	public void multiCheck(HttpServletRequest request, ModelMap model ,HttpServletResponse response) throws IOException {
		String ids[] = request.getParameter("ids").split(";");
		List<Customer> customers = customerService.findByIds(ids);
		for(Customer temp:customers){
			temp.setStatu(true);
		}
		customerService.batchUpdate(customers);
		response.setContentType("application/json");
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write("{\"msg\":\"批量审核用户成功\"}");
	}

	/*
	 * 保存未审核的用户
	 */
	@RequestMapping(value = "/business/customer/unCheckSave")
	public void save(HttpServletRequest request, ModelMap model,HttpServletResponse response) throws IOException {
		Customer customer = new Customer();
		boolean isNew = request.getParameter("isNew").equals("true")?true:false;
		if(isNew){
			
		}else{
			String id = request.getParameter("id");
			customer = customerService.findById(id);
		}
		customer.setName(request.getParameter("name"));
		customer.setPhone(request.getParameter("phone"));
		customer.setPoint(Integer.parseInt(request.getParameter("points")));
		customer.setRemark(request.getParameter("remark"));
		customer.setDeleted(false);
		String fCityId = request.getParameter("fCityId");
		String sCityId = request.getParameter("sCityId");
		City fCity = cityService.findById(fCityId);
		City sCity = cityService.findById(sCityId);
		User user = userService.findByUserName(MyRealm.hardName);
		customer.setUser(user);
		if(sCity==null)
			customer.setCity(fCity);
		else
			customer.setCity(sCity);
		if(isNew)
			customerService.save(customer);
		else
			customerService.update(customer);
		response.setContentType("application/json");
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write("{\"msg\":\"保存成功\"}");
	}
	
	/*
	 * 单个删除还未通过审核的用户
	 */
	@RequestMapping(value = "/business/customer/delete")
	public String delete(HttpServletRequest request, ModelMap model) {
		String id = request.getParameter("id");
		Customer customer = customerService.findById(id);
		customer.setDeleted(true);
		customerService.update(customer);
		model.addAttribute("statu",false);
		return "redirect:list";
	}
	
	/*
	 * 获取未通过审核用户的信息
	 */
	@RequestMapping(value = "/business/customer/getCustomerInfo")
	public void getCustomerInfo(HttpServletRequest request, ModelMap model,HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		StringBuilder result = new StringBuilder();
		Customer customer = customerService.findById(id);
		result.append("{\"name\":\""+customer.getName()+"\",\"points\":"+customer.getPoint()+",\"phone\":\""+customer.getPhone()+"\",\"remark\":\""+customer.getRemark()+"\",");
		City city = customer.getCity();
		if(city.getParent()==null){//说明属于一级行政单位
			result.append("\"fCityId\":\""+city.getId()+"\",\"sCityId\":\"null\"}");
		}else{
			City fCity = city.getParent();
			result.append("\"fCityId\":\""+fCity.getId()+"\",\"sCityId\":\""+city.getId()+"\"}");
		}
		System.out.println(result.toString());
		response.setContentType("application/json");
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write(result.toString());
	}

	
}
