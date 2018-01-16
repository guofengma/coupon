package com.coupon.business.action;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coupon.base.action.BaseAction;
import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageListUtil;
import com.coupon.base.common.utils.CookieUtil;
import com.coupon.business.entity.Customer;
import com.coupon.business.entity.Product;
import com.coupon.business.entity.Record;
import com.coupon.business.entity.ServiceInfo;
import com.coupon.business.service.CustomerService;
import com.coupon.business.service.RecordService;
import com.coupon.business.service.ServiceInfoService;
import com.coupon.system.entity.City;
import com.coupon.system.entity.Role;
import com.coupon.system.entity.User;
import com.coupon.system.service.UserService;
import com.coupon.util.MyDateUtils;
import com.coupon.util.RoleToString;

@Controller
public class ServiceInfoAction extends BaseAction{

	@Autowired
	private RecordService recordService ;
	@Autowired
	private CustomerService customerService ;
	@Autowired
	private ServiceInfoService serviceInfoService ;
	@Autowired
	private UserService userService ;
	
	/*
	 * 根据订单号，跳转到预约服务的页面
	 */
	@RequestMapping(value = "serviceInfo/app/toExchangeServicePage")
	public String batchlist(HttpServletRequest request, ModelMap model) {
		String name = CookieUtil.getCookie(request, "phone_EN");
		Customer customer = customerService.findByPhone(name);
		if(null == customer)
			return "appindex";
		String recordId = request.getParameter("recordId");
		Record record = recordService.findById(recordId);
		model.addAttribute("record",record);
		model.addAttribute("product",record.getProduct());
		return "app/exchangeService";
	}
	
	@RequestMapping(value = "serviceInfo/app/exchangeService")
	public String exchangeService(HttpServletRequest request, ModelMap model) throws ParseException {
		String name = CookieUtil.getCookie(request, "phone_EN");
		String recordId = request.getParameter("recordId");
		String reservationTime = request.getParameter("reservationTime");
		String reservationAddress = request.getParameter("reservationAddress");
		String contact = request.getParameter("contact");
		String comments = request.getParameter("comments");
		Customer customer = customerService.findByPhone(name);
		if(null == customer)
			return "appindex";
		Record record = recordService.findById(recordId);
		ServiceInfo serviceInfo = new ServiceInfo();
		serviceInfo.setRecord(record);
		serviceInfo.setReservationTime(MyDateUtils.strToDate(reservationTime.substring(0,10)));
		serviceInfo.setAmOrPm(reservationTime.contains("上")?"上午":"下午");
		serviceInfo.setReservationAddress(reservationAddress);
		serviceInfo.setContact(contact);
		serviceInfo.setComments(comments);
		serviceInfo.setDeal("0");
		serviceInfoService.save(serviceInfo);
		return "redirect:myService";
	}
	
	@RequestMapping(value = "serviceInfo/app/myService")
	public String myService(HttpServletRequest request, ModelMap model) {
		String name = CookieUtil.getCookie(request, "phone_EN");
		Customer customer = customerService.findByPhone(name);
		if(null == customer)
			return "appindex";
		List<ServiceInfo> serviceInfos = serviceInfoService.findMyServiceInfo(customer.getId());
		model.addAttribute("serviceInfos",serviceInfos);
		return "app/myService";
	}
	
	@RequestMapping(value = "business/serviceInfo/undealList")
	public String unealServiceInfo(HttpServletRequest request, ModelMap model) {
		super.addMenuParams(request, model);
		User user = userService.findByUserName(CookieUtil.getCookie(request, "name_EN"));
		if(null == user)
			return "index";
		int pageNo = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_NO_NAME, PageListUtil.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_SIZE_NAME, PageListUtil.DEFAULT_PAGE_SIZE);
		String roleString = RoleToString.roleToString(user.getRoles());
		if(roleString.contains("管理员")){
			IPageList<ServiceInfo> serviceInfos = serviceInfoService.findUndealByAdmin(pageNo, pageSize);
			model.addAttribute("serviceInfos",serviceInfos);
		}
		if(roleString.contains("大区经理")){
			StringBuilder cityIds = new StringBuilder();
			for(City temp : user.getCity()){
				cityIds.append(temp.getId()+";");
			}
			IPageList<ServiceInfo> serviceInfos = serviceInfoService.findUndealByManager(pageNo, pageSize,cityIds.toString());
			model.addAttribute("serviceInfos",serviceInfos);
		}
		if(roleString.toString().contains("员工")){
			IPageList<ServiceInfo> serviceInfos = serviceInfoService.findUndealByStaff(pageNo, pageSize,user.getId());
			model.addAttribute("serviceInfos",serviceInfos);
		}
		return "business/serviceInfo/undealList";	
	}
	
	@RequestMapping(value = "/business/serviceInfo/deal")
	public String dealServiceInfo(HttpServletRequest request, ModelMap model) throws ParseException {
		String serviceInfoId= request.getParameter("serviceInfoId");
		ServiceInfo serviceInfo = serviceInfoService.findById(serviceInfoId);
		serviceInfo.setConfirmReservationTime(MyDateUtils.strToMinutes(request.getParameter("confirmReservationTime"))); 
		serviceInfo.setConfirmReservationAddress(request.getParameter("confirmReservationAddress"));
		serviceInfo.setReservationPerson(request.getParameter("reservationPerson"));
		serviceInfo.setReservationPersonContact(request.getParameter("reservationPersonContact"));
		serviceInfo.setConfirmComment(request.getParameter("confirmComment"));
		serviceInfo.setDeal("1");
		serviceInfoService.update(serviceInfo);
		return "redirect:undealList";
	}
	
	@RequestMapping(value = "/business/serviceInfo/getServiceInfoById")
	public void getServiceInfoById(HttpServletRequest request, ModelMap model,HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		JSONObject json = new JSONObject();
		ServiceInfo serviceInfo =serviceInfoService.findById(id);
		json.put("productName",serviceInfo.getRecord().getProduct().getName());
		json.put("redeemCode", serviceInfo.getRecord().getRedeemCode().getCode());
		json.put("reservationTime", serviceInfo.getReservationTime().toString().substring(0, 10)+serviceInfo.getAmOrPm());
		json.put("reservationAddress", serviceInfo.getReservationAddress());
		json.put("contact", serviceInfo.getContact());
		json.put("comments", serviceInfo.getComments());
		response.setContentType("application/json");
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write(json.toString());
	}
}
