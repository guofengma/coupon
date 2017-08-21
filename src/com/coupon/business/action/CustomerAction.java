package com.coupon.business.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
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
import com.coupon.business.entity.Bank;
import com.coupon.business.entity.Customer;
import com.coupon.business.entity.RechargeCode;
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
import com.coupon.util.FolderUtil;

@Controller
public class CustomerAction extends BaseAction{

	@Autowired UserService userService;
	
	@Autowired CustomerService customerService;
	
	@Autowired CityService cityService;
	
	@Autowired BankService bankService;
	
	@Autowired RecordService recordService;
	
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
		String condition = request.getParameter("condition")==null?"":request.getParameter("condition");
		model.addAttribute("condition",condition);
		System.out.println(request.getParameter("statu")+check);
		User user = userService.findByUserName(MyRealm.hardName);
		StringBuilder roleString = new StringBuilder() ;
		Set<Role> roles = user.getRoles();
		for(Role temp : roles){
			roleString.append(temp.getName()+";");
		}
		if(roleString.toString().contains("管理员")){
			IPageList<Customer> customers = customerService.findByAdmin(pageNo, pageSize,check,condition);
			model.addAttribute("customers",customers);
			return "business/customer/"+(check?"checkList":"uncheckList");	
		}
		if(roleString.toString().contains("大区经理")){
			IPageList<Customer> customers = customerService.findByManager(pageNo, pageSize,check,user.getId(),condition);
			model.addAttribute("customers",customers);
			return "business/customer/"+(check?"checkList":"uncheckList");	
		}
		if(roleString.toString().contains("员工")){
			IPageList<Customer> customers = customerService.findByStaff(pageNo, pageSize,check,user.getId(),condition);
			model.addAttribute("customers",customers);
			return "business/customer/"+(check?"checkList":"uncheckList");	
		}
		return "business/customer/"+(check?"checkLsit":"uncheckList");		
	}
	
	/*
	 * 根据查询条件，查询符合条件的客户
	 */
	@RequestMapping(value = "/search/customer/findByCondition")
	public String findByCondition(HttpServletRequest request, ModelMap model) {
		super.addMenuParams(request, model);
		int pageNo = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_NO_NAME, PageListUtil.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_SIZE_NAME, PageListUtil.DEFAULT_PAGE_SIZE);
		List<City> fCityUsedList = cityService.getFCityUsed();
		model.addAttribute("fCityUsedList",fCityUsedList);
		String startTime = request.getParameter("startTime")==null?"":request.getParameter("startTime");
		String endTime = request.getParameter("endTime")==null?"":request.getParameter("endTime");
		String name = request.getParameter("name")==null?"": request.getParameter("name");
		String latestName = request.getParameter("latestName")==null?"":request.getParameter("latestName");
		String fCity = request.getParameter("fCity")==null?"null":request.getParameter("fCity");
		String sCity = request.getParameter("sCity")==null?"null":request.getParameter("sCity");
		String city = sCity.equals("null")?fCity:sCity;
		String phone = request.getParameter("phone")==null?"":request.getParameter("phone");
		String condition[] = new String[]{startTime,endTime,name,latestName,city,phone};
		PageList<Customer> customers = customerService.findByCondition(pageNo,pageSize,condition);
		model.addAttribute("customers",customers);
		model.addAttribute("startTime",startTime);
		model.addAttribute("endTime",endTime);
		model.addAttribute("name",name);
		model.addAttribute("latestName",latestName);
		model.addAttribute("fCity",fCity);
		model.addAttribute("sCity",sCity);
		model.addAttribute("phone",phone);
		model.addAttribute("date",FolderUtil.getFolder());
		return "search/customer/list";
	}
	
	/*
	 * 单个审核新增的用户
	 */
	@RequestMapping(value = "/business/customer/check")
	public String check(HttpServletRequest request, ModelMap model) {
		User user = userService.findByUserName(MyRealm.hardName);
		String id = request.getParameter("id");
		boolean pass = request.getParameter("pass").equals("true");
		Customer customer = customerService.findById(id);
		if(pass){
			Record record = new Record();
			record.setCustomer(customer);
			record.setPoints(customer.getPoint());
			record.setRaise(true);
			record.setDeal(true);
			record.setStatu(true);
			record.setUser(customer.getUser());
			record.setCheckUser(user);
			recordService.save(record);
		}
		customer.setDeal(true);
		customer.setStatu(pass);
		customer.setCheckUser(user);
		customerService.update(customer);
		model.addAttribute("statu",false);
		model.addAttribute("condition",request.getParameter("condition"));
		return "redirect:list";
	}
	
	/*
	 * 单个用户重新请求审核
	 */
	@RequestMapping(value = "/business/customer/requestCheck")
	public String requestCheck(HttpServletRequest request, ModelMap model) {
		String id = request.getParameter("id");
		Customer customer = customerService.findById(id);
		customer.setDeal(false);
		customerService.update(customer);
		model.addAttribute("statu",false);
		model.addAttribute("condition",request.getParameter("condition"));
		return "redirect:list";
	}
	
	/*
	 * 批量审核新增的用户
	 */
	@RequestMapping(value = "/business/customer/multiCheck")
	public void multiCheck(HttpServletRequest request, ModelMap model ,HttpServletResponse response) throws IOException {
		User user = userService.findByUserName(MyRealm.hardName);
		String ids[] = request.getParameter("ids").split(";");
		boolean pass = request.getParameter("pass").equals("true");
		List<Customer> customers = customerService.findByIds(ids);
		List<Record> records = new ArrayList<Record>();
		for(Customer temp:customers){
			if(pass){
				Record record = new Record();
				record.setCustomer(temp);
				record.setPoints(temp.getPoint());
				record.setRaise(true);
				record.setDeal(true);
				record.setStatu(true);
				record.setUser(temp.getUser());
				record.setCheckUser(user);
				records.add(record);
			}
			temp.setDeal(true);
			temp.setStatu(pass);
			temp.setCheckUser(user);
		}
		recordService.batchSave(records);
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
		String fCity = request.getParameter("fCity")==null?"null":request.getParameter("fCity");
		String sCity = request.getParameter("sCity")==null?"null":request.getParameter("sCity");
		String bankAddress = request.getParameter("bankAddress");
		Set<City> customerCity = new HashSet<City>();
		if(fCity.equals("null")){
			customerCity = null ;
		}else if(sCity.equals("null")){
			customerCity = cityService.findByIds(fCity.split(","));
		}else{
			customerCity = cityService.findByIds((fCity+","+sCity).split(","));
		}
		if(isNew){
			
		}else{
			String id = request.getParameter("id");
			customer = customerService.findById(id);
		}
		customer.setName(request.getParameter("name"));
		customer.setPhone(request.getParameter("phone"));
		customer.setPoint(Integer.parseInt(request.getParameter("points")));
		customer.setTotalAddUp(Integer.parseInt(request.getParameter("points")));
		customer.setRemark(request.getParameter("remark"));
		customer.setDeleted(false);
		customer.setBankAddress(bankAddress);
		User user = userService.findByUserName(MyRealm.hardName);
		customer.setUser(user);
		customer.setCity(customerCity);
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
		model.addAttribute("condition",request.getParameter("condition"));
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
		result.append("{\"name\":\""+customer.getName()+"\",\"bankAddress\":\""+customer.getBankAddress()+"\",\"points\":"+customer.getPoint()+",\"phone\":\""+customer.getPhone()+"\",\"remark\":\""+customer.getRemark()+"\",");
		Set<City> city = customer.getCity();
		if(city.size()==0){//不属于任何城市
			result.append("\"fCityId\":\"null\",\"sCityId\":[]}");
		}else{
			StringBuilder sCity = new StringBuilder("[");
			City fCity = new City();
			for(City temp : city){
				if(temp.getParent()==null)
					fCity = temp ;
				else{
					sCity.append("\""+temp.getId()+"\",") ;
				}
			}
			if(city.size()>1){
				sCity.deleteCharAt(sCity.length()-1);
			}
			sCity.append("]");
			result.append("\"fCityId\":\""+fCity.getId()+"\",\"sCityId\":"+sCity.toString()+"}");
		}
		System.out.println(result.toString());
		response.setContentType("application/json");
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write(result.toString());
	}

	/*
	 * 为客户充值积分，增加一条记录，并设置成为未审核状态
	 */
	@RequestMapping(value = "/business/customer/recharge")
	public void recharge(HttpServletRequest request, ModelMap model,HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		int point = Integer.parseInt(request.getParameter("point"));
		Customer customer = customerService.findById(id);
		User user = userService.findByUserName(MyRealm.hardName);
		Record record = new Record();
		record.setCustomer(customer);
		record.setPoints(point);
		record.setRaise(true);
		record.setStatu(false);
		record.setUser(user);
		recordService.save(record);
		response.setContentType("application/json");
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write("{}");
	}
}
