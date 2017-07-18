package com.coupon.business.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coupon.base.action.BaseAction;
import com.coupon.business.service.CustomerService;
import com.coupon.business.service.ProductService;

@Controller
public class ProductAction extends BaseAction{
	
	@Autowired
	private ProductService productService ;
	
	@RequiresPermissions(value={"product:management"})
	@RequestMapping(value = "/business/product/list")
	public String list(HttpServletRequest request, ModelMap model) {
		return "business/product/list";
	}

}
