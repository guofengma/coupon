package com.coupon.business.action;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

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
import com.coupon.business.entity.Product;
import com.coupon.business.entity.RedeemCode;
import com.coupon.business.service.ProductService;
import com.coupon.business.service.RedeemCodeService;
import com.coupon.security.MyRealm;
import com.coupon.system.service.UserService;

@Controller
public class RedeemCodeAction extends BaseAction{
	
	@Autowired
	private ProductService productService ;
	@Autowired
	private RedeemCodeService redeemCodeService ;
	@Autowired 
	private UserService userService;
	
	@RequestMapping(value = "/business/redeemCode/list")
	public String list(HttpServletRequest request, ModelMap model) {
		super.addMenuParams(request, model);
		String id = request.getParameter("id");
		Product product = productService.findById(id);
		model.addAttribute("product",product);
		int pageNo = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_NO_NAME, PageListUtil.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_SIZE_NAME, PageListUtil.DEFAULT_PAGE_SIZE);
		IPageList<RedeemCode>  redeemCodes =  redeemCodeService.findBatch(pageNo, pageSize,id);
		model.addAttribute("redeemCodes",redeemCodes);
		return "business/redeemCode/list";
	}
	
	@RequestMapping(value = "/business/redeemCode/batchSave")
	public String batchSave(HttpServletRequest request, ModelMap model,String productId,String oldId,String batch,String endTime,String remark) throws ParseException {
		super.addMenuParams(request, model);
		RedeemCode redeemCode = new RedeemCode();
		Product product = productService.findById(productId);
		if(oldId.equals("null")){
			redeemCode.setProduct(product);
			redeemCode.setUsed(true);
		}else{
			redeemCode = redeemCodeService.findById(oldId);
		}
		redeemCode.setBatch(batch);
		redeemCode.setEndTime(new SimpleDateFormat("yyyy-MM-dd").parse(endTime));
		redeemCode.setRemark(remark);
		redeemCode.setUser(userService.findByUserName(MyRealm.hardName));
		if(oldId.equals("null")){
			redeemCodeService.save(redeemCode);
		}else{
			redeemCodeService.update(redeemCode);
		}
		model.addAttribute("id",productId);
		return "redirect:list";
	}
	/*
	 * 删除该批次和该批次下的所有兑换码
	 */
	@RequestMapping(value = "/business/redeemCode/deleteBatch")
	public String deleteBatch(HttpServletRequest request, ModelMap model,String productId,String oldId,String batch,String endTime,String remark) throws ParseException {
		super.addMenuParams(request, model);
		String id = request.getParameter("id");
		RedeemCode redeemCode = redeemCodeService.findById(id);
		redeemCode.setDeleted(true);
		redeemCodeService.update(redeemCode);
		List<RedeemCode> children = redeemCode.getChildren();
		for(RedeemCode temp : children){
			temp.setDeleted(true);
		}
		redeemCodeService.batchUpdate(children);
		model.addAttribute("id",redeemCode.getProduct().getId());
		return "redirect:list";
	}
	
	/*
	 * 获取批次信息
	 */
	@RequestMapping(value = "/business/redeemCode/editBatch")
	public void editBatch(HttpServletRequest request, ModelMap model,HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		StringBuilder result = new StringBuilder();
		RedeemCode redeemCode = redeemCodeService.findById(id);
		result.append("{\"batch\":\""+redeemCode.getBatch()+"\",\"endTime\":\""+redeemCode.getEndTime().toString().substring(0, 10)+"\",\"remark\":\""+redeemCode.getRemark()+"\"}");
		System.out.println(result.toString());
		response.setContentType("application/json");
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write(result.toString());
	}
	
	/*
	 * 获取批次信息
	 */
	@RequestMapping(value = "/business/redeemCode/changeBatchState")
	public void changeBatchState(HttpServletRequest request, ModelMap model,HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		boolean state = request.getParameter("state").equals("false");
		RedeemCode redeemCode = redeemCodeService.findById(id);
		redeemCode.setUsed(state);
		redeemCodeService.update(redeemCode);
		response.setContentType("application/json");
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write("{\"flag\":\"success\",\"msg\":\"改变批次可用状况成功\"}");
	}
}
