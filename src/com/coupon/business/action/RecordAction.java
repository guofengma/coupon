package com.coupon.business.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.coupon.base.action.BaseAction;
import com.coupon.base.common.paging.PageList;
import com.coupon.base.common.paging.PageListUtil;
import com.coupon.business.entity.Customer;
import com.coupon.business.entity.Record;
import com.coupon.business.service.CustomerService;
import com.coupon.business.service.RecordService;

@Controller
public class RecordAction extends BaseAction{
	
	@Autowired
	private RecordService recordService ;
	@Autowired
	private CustomerService customerService ;

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
}
