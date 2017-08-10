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
import com.coupon.business.entity.Bank;
import com.coupon.business.service.BankService;
import com.coupon.security.MyRealm;
import com.coupon.system.entity.City;
import com.coupon.system.entity.User;
import com.coupon.system.service.CityService;

@Controller
public class BankAction extends BaseAction{
	
	@Autowired CityService cityService;
	@Autowired BankService bankService;
	
	@RequestMapping(value = "/business/bank/list")
	public String list(HttpServletRequest request, ModelMap model) {
		super.addMenuParams(request, model);
		List<City> fCityUsedList = cityService.getFCityUsed();
		model.addAttribute("fCityUsedList",fCityUsedList);
		int pageNo = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_NO_NAME, PageListUtil.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_SIZE_NAME, PageListUtil.DEFAULT_PAGE_SIZE);
		IPageList<Bank> banks = bankService.findAll(pageNo, pageSize);
		model.addAttribute("banks",banks);
		return "business/bank/list";		
	}
	
	@RequestMapping(value = "/business/bank/save")
	public void save(HttpServletRequest request, ModelMap model,HttpServletResponse response) throws IOException {
		Bank bank = new Bank();
		boolean isNew = request.getParameter("isNew").equals("true")?true:false;
		if(isNew){
			
		}else{
			String id = request.getParameter("id");
			bank = bankService.findById(id);
		}
		bank.setName(request.getParameter("name"));
		bank.setAddress(request.getParameter("address"));
		bank.setPhone(request.getParameter("phone"));
		bank.setRemark(request.getParameter("remark"));
		bank.setDeleted(false);
		String fCityId = request.getParameter("fCityId");
		String sCityId = request.getParameter("sCityId");
		City fCity = cityService.findById(fCityId);
		City sCity = cityService.findById(sCityId);
		if(sCity==null)
			bank.setCity(fCity);
		else
			bank.setCity(sCity);
		if(isNew)
			bankService.save(bank);
		else
			bankService.update(bank);
		response.setContentType("application/json");
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write("{\"msg\":\"保存成功\"}");
	}
	
	/*
	 * 单个删除兑换服务地址
	 */
	@RequestMapping(value = "/business/bank/delete")
	public String delete(HttpServletRequest request, ModelMap model) {
		String id = request.getParameter("id");
		Bank bank = bankService.findById(id);
		bank.setDeleted(true);
		bankService.update(bank);
		return "redirect:list";
	}
	
	/*
	 * 获取兑换服务地址的信息
	 */
	@RequestMapping(value = "/business/bank/getBankInfo")
	public void getBankInfo(HttpServletRequest request, ModelMap model,HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		StringBuilder result = new StringBuilder();
		Bank bank = bankService.findById(id);
		result.append("{\"name\":\""+bank.getName()+"\",\"address\":\""+bank.getAddress()+"\",\"phone\":\""+bank.getPhone()+"\",\"remark\":\""+bank.getRemark()+"\",");
		City city = bank.getCity();
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
