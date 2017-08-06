package com.coupon.business.action;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coupon.base.action.BaseAction;
import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageListUtil;
import com.coupon.business.entity.Customer;
import com.coupon.business.entity.Product;
import com.coupon.business.service.CustomerService;
import com.coupon.business.service.ProductService;
import com.coupon.system.entity.City;
import com.coupon.system.service.CityService;

@Controller
public class ProductAction extends BaseAction{
	
	@Autowired
	private ProductService productService ;
	
	@Autowired CityService cityService;
	
	@RequiresPermissions(value={"product:management"})
	@RequestMapping(value = "/business/product/list")
	public String list(HttpServletRequest request, ModelMap model) {
		super.addMenuParams(request, model);
		List<City> fCityUsedList = cityService.getFCityUsed();
		model.addAttribute("fCityUsedList",fCityUsedList);
		int pageNo = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_NO_NAME, PageListUtil.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_SIZE_NAME, PageListUtil.DEFAULT_PAGE_SIZE);
		IPageList<Product> products = productService.findAll(pageNo, pageSize);
		model.addAttribute("products",products);
		return "business/product/list";
	}
	
	@RequestMapping(value = "/business/product/save")
	public String save(HttpServletRequest request, ModelMap model,String id ,String city
			,String name , String points , String remark ) {
		super.addMenuParams(request, model);
		Product product = new Product();
		if(id.equals("")){
			
		}else{
			product = productService.findById(id);
		}
		Set<City> citys = cityService.findByIds(city.split(";"));
		product.setCity(citys);
		product.setName(name);
		product.setRemark(remark);
		product.setPoints(Integer.parseInt(points));
		if(id.equals("")){
			productService.save(product);
		}else{
			productService.update(product);
		}
		return "redirect:list";
	}
	
	

}
