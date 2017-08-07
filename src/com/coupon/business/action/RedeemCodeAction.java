package com.coupon.business.action;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.coupon.base.action.BaseAction;
import com.coupon.business.entity.Product;
import com.coupon.business.service.ProductService;

@Controller
public class RedeemCodeAction extends BaseAction{
	
	@Autowired
	private ProductService productService ;
	
	@RequestMapping(value = "/business/redeemCode/list")
	public String list(HttpServletRequest request, ModelMap model) {
		String id = request.getParameter("id");
		Product product = productService.findById(id);
		model.addAttribute("product",product);
		return "business/redeemCode/list";
	}
}
