package com.coupon.business.action;

import java.io.IOException;
import java.util.ArrayList;
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
import com.coupon.base.common.paging.PageList;
import com.coupon.base.common.paging.PageListUtil;
import com.coupon.business.entity.Customer;
import com.coupon.business.entity.Record;
import com.coupon.business.service.BankService;
import com.coupon.business.service.CustomerService;
import com.coupon.business.service.RecordService;
import com.coupon.security.MyRealm;
import com.coupon.system.entity.City;
import com.coupon.system.entity.Role;
import com.coupon.system.entity.User;
import com.coupon.system.service.CityService;
import com.coupon.system.service.UserService;

@Controller
public class RecordAction extends BaseAction{
	
	@Autowired UserService userService;
	
	@Autowired CustomerService customerService;
	
	@Autowired CityService cityService;
	
	@Autowired BankService bankService;
	
	@Autowired RecordService recordService;

	/*
	 * 根据客户id，查询客户消费记录
	 */
	@RequestMapping(value = "/search/record/findByCustomerId")
	public String findByCustomerId(HttpServletRequest request, ModelMap model) {
		super.addMenuParams(request, model);
		int pageNo = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_NO_NAME, PageListUtil.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_SIZE_NAME, PageListUtil.DEFAULT_PAGE_SIZE);
		String customerId = request.getParameter("id");
		Customer customer = customerService.findById(customerId);
		model.addAttribute("customer",customer);
		PageList<Record> records = recordService.findByCustomerId(pageNo,pageSize,customerId);
		model.addAttribute("records",records);
		return "search/customer/detail";
	}
	
	/*
	 * 根据员工，查询各自对应的待办事项
	 */
	@RequestMapping(value = "/business/record/undeal")
	public String list(HttpServletRequest request, ModelMap model) {
		super.addMenuParams(request, model);
		int pageNo = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_NO_NAME, PageListUtil.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_SIZE_NAME, PageListUtil.DEFAULT_PAGE_SIZE);
		User user = userService.findByUserName(MyRealm.hardName);
		StringBuilder roleString = new StringBuilder() ;
		Set<Role> roles = user.getRoles();
		for(Role temp : roles){
			roleString.append(temp.getName()+";");
		}
		if(roleString.toString().contains("管理员")){
			IPageList<Record> records = recordService.findUndealByAdmin(pageNo, pageSize);
			model.addAttribute("records",records);
		}
		if(roleString.toString().contains("大区经理")){
			IPageList<Record> records = recordService.findUndealByManager(pageNo, pageSize,user.getCity().getId());
			model.addAttribute("records",records);
		}
		if(roleString.toString().contains("员工")){
			IPageList<Record> records = recordService.findUndealByStaff(pageNo, pageSize,user.getId());
			model.addAttribute("records",records);
		}
		return "business/record/undealList";		
	}
	
	/*
	 * 单个审核新增的记录（后台充值）
	 */
	@RequestMapping(value = "/business/record/check")
	public String check(HttpServletRequest request, ModelMap model) {
		User user = userService.findByUserName(MyRealm.hardName);
		String id = request.getParameter("id");
		boolean pass = request.getParameter("pass").equals("true");
		Record record = recordService.findById(id);
		Customer customer = record.getCustomer();
		record.setDeal(pass);
		record.setCheckUser(user);
		record.setDeal(true);
		if(pass){//通过审核
			record.setStatu(true);
			customer.setTotalAddUp(customer.getPoint()+record.getPoints());
			customer.setPoint(customer.getPoint()+record.getPoints());
		}
		recordService.update(record);
		customerService.update(customer);
		return "redirect:undeal";
	}
	
	/*
	 * 单个记录重新请求审核
	 */
	@RequestMapping(value = "/business/record/requestCheck")
	public String requestCheck(HttpServletRequest request, ModelMap model) {
		String id = request.getParameter("id");
		Record record = recordService.findById(id);
		record.setDeal(false);
		recordService.update(record);
		return "redirect:undeal";
	}
	
	/*
	 * 批量审核新增的用户
	 */
	@RequestMapping(value = "/business/record/multiCheck")
	public void multiCheck(HttpServletRequest request, ModelMap model ,HttpServletResponse response) throws IOException {
		User user = userService.findByUserName(MyRealm.hardName);
		String ids[] = request.getParameter("ids").split(";");
		boolean pass = request.getParameter("pass").equals("true");
		List<Record> records = recordService.findByIds(ids);
		for(Record record : records){
			Customer customer = record.getCustomer();
			record.setDeal(pass);
			record.setCheckUser(user);
			record.setDeal(true);
			if(pass){//通过审核
				record.setStatu(true);
				customer.setTotalAddUp(customer.getPoint()+record.getPoints());
				customer.setPoint(customer.getPoint()+record.getPoints());
			}
			recordService.update(record);
			customerService.update(customer);
		}
		response.setContentType("application/json");
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write("{\"msg\":\"批量审核用户成功\"}");
	}
}
