package com.coupon.business.action;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.coupon.base.action.BaseAction;
import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageListUtil;
import com.coupon.base.common.utils.CookieUtil;
import com.coupon.business.entity.Customer;
import com.coupon.business.entity.Product;
import com.coupon.business.service.CustomerService;
import com.coupon.business.service.ProductService;
import com.coupon.system.entity.City;
import com.coupon.system.service.CityService;
import com.coupon.util.FolderUtil;

@Controller
public class ProductAction extends BaseAction{
	
	@Autowired
	private ProductService productService ;
	
	@Autowired CityService cityService;
	
	@Autowired
	private CustomerService customerService ;
	
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
	
	/**
	 * 查看商品详情
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/business/app/productDetail")
	public String productDetail(HttpServletRequest request, ModelMap model,String id) {
		Product product = productService.findById(id);
		model.addAttribute("product",product);
		return "app/productDetail";
	}
	
	/**
	 * 客户兑换商品
	 * @param request
	 * @param model
	 * @throws IOException 
	 */
	@RequestMapping(value = "/business/app/exchange")
	public void exchange(HttpServletRequest request, HttpServletResponse response) throws IOException {
		StringBuilder result = new StringBuilder();
		String name = CookieUtil.getCookie(request, "name_EN");//获取客户信息
		Customer customer = customerService.findByPhone(name);
		int count = Integer.valueOf(request.getParameter("count"));//兑换数量
		String id = request.getParameter("id"); //商品id
		Product product = productService.findById(id);
		if(null == customer){	
			result.append("{\"errorCode\":\"1\",\"message\":\"登录过期，请重新登录！\"}");
		}else if(product.getPoints()*count > customer.getPoint()){
			result.append("{\"errorCode\":\"2\",\"message\":\"积分不够，请减少数量再兑换！\"}");
		}else{
			
		}
		response.getWriter().write(result.toString());
		response.setContentType("application/json");
	 	response.setCharacterEncoding("utf-8");
	}
	
	@RequestMapping(value = "/business/product/save")
	public String save(HttpServletRequest request, ModelMap model) throws Exception {
		String oldId = "";
		String city="";
		String name = "";
		String points="";
		String description="" ;
		super.addMenuParams(request, model);
		Product product = new Product();
		request.setCharacterEncoding("utf-8");
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			return "redirect:list";
		}
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List items = upload.parseRequest(request);
		Iterator iter = items.iterator();//用来获取普通input
		Iterator iter1 = items.iterator();//用来上传文件
		boolean fileChanged = false ;
		while (iter.hasNext()) // 表单中有几个input标签，就循环几次
		{
			FileItem item = (FileItem) iter.next();
			if (item.isFormField()) {
				if(item.getFieldName().equals("description"))
					description = item.getString("utf-8");
				if(item.getFieldName().equals("oldId"))
					oldId = item.getString("utf-8");
				if(item.getFieldName().equals("city"))
					city = item.getString("utf-8");
				if(item.getFieldName().equals("name"))
					name = item.getString("utf-8");
				if(item.getFieldName().equals("points"))
					points = item.getString("utf-8");
				if(item.getFieldName().equals("fileChanged"))
					fileChanged = item.getString("utf-8").equals("true");
			} else {
				
			}
		}
		if(oldId.equals("null")){
			
		}else{
			product = productService.findById(oldId);
			if(fileChanged){
				String root = request.getServletContext().getRealPath("/");
				String oldFilePath = root + "img\\"+product.getPicPath();
				File oldfile = new File(oldFilePath);
				if(oldfile.exists())
					oldfile.delete();
			}
		}
		while (iter1.hasNext()) // 表单中有几个input标签，就循环几次
		{
			FileItem item1 = (FileItem) iter1.next();
			if (item1.isFormField()) {
				
			} else {
				if(fileChanged){
					String fileName = item1.getName();
					// 这里发现ie获取的是路径加文件名，chrome获取的是文件名，这里我们只需要文件名，所以有路径的要先去路径
					fileName = fileName.substring(fileName.lastIndexOf("\\") + 1,
							fileName.length());
					String prefix = System.currentTimeMillis() + "";
					File file = new File(request.getServletContext().getRealPath(
							"/")
							+ "img\\" + FolderUtil.getFolder());
					if (!file.exists())
						file.mkdirs();
					File uploadedFile = new File(request.getServletContext()
							.getRealPath("/")
							+ "img\\"+FolderUtil.getFolder()
							+ "\\"
							+ prefix + fileName);
					item1.write(uploadedFile);
					product.setPicPath(FolderUtil.getFolder()+ "\\"
							+ prefix + fileName);
					product.setPicName(fileName);
				}
			}
		}
		Set<City> citys = cityService.findByIds(city.split(";"));
		product.setCity(citys);
		product.setName(name);
		product.setDescription(description);
		product.setPoints(Integer.parseInt(points));
		if(oldId.equals("null")){
			productService.save(product);
		}else{
			productService.update(product);
		}
		return "redirect:list";
	}
	
	/*
	 * 单个删除商品
	 */
	@RequestMapping(value = "/business/product/delete")
	public String delete(HttpServletRequest request, ModelMap model) {
		String id = request.getParameter("id");
		Product product =productService.findById(id);
		product.setDeleted(true);
		productService.update(product);
		return "redirect:list";
	}
	
	/*
	 * 单个上架商品
	 */
	@RequestMapping(value = "/business/product/online")
	public String online(HttpServletRequest request, ModelMap model) {
		String id = request.getParameter("id");
		Product product =productService.findById(id);
		product.setStatu(true);
		productService.update(product);
		return "redirect:list";
	}
	
	/*
	 * 单个下架商品
	 */
	@RequestMapping(value = "/business/product/offline")
	public String offline(HttpServletRequest request, ModelMap model) {
		String id = request.getParameter("id");
		Product product =productService.findById(id);
		product.setStatu(false);
		productService.update(product);
		return "redirect:list";
	}
	
	@RequestMapping(value = "/business/product/getProductInfo")
	public void getProductInfo(HttpServletRequest request, ModelMap model,HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		StringBuilder result = new StringBuilder();
		Product product =productService.findById(id);
		Set<City> citys = product.getCity();
		StringBuilder cityIds = new StringBuilder();
		for(City temp : citys){
			cityIds.append(temp.getId()+";");
		}
		if(citys.size()!=0)
			cityIds.deleteCharAt(cityIds.length()-1);
		if(product.getDescription()==null)
			result.append("{\"picName\":\""+product.getPicName()+"\",\"cityIds\":\""+cityIds.toString()+"\",\"name\":\""+product.getName()+"\",\"points\":"+product.getPoints()+",\"description\":\"\",\"picPath\":\""+product.getPicPath().replace("\\", "\\\\")+"\"}");
		else
			result.append("{\"picName\":\""+product.getPicName()+"\",\"cityIds\":\""+cityIds.toString()+"\",\"name\":\""+product.getName()+"\",\"points\":"+product.getPoints()+",\"description\":\""+product.getDescription().replace("\"", "'")+"\",\"picPath\":\""+product.getPicPath().replace("\\", "\\\\")+"\"}");
		System.out.println(result.toString());
		response.setContentType("application/json");
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write(result.toString());
	}
	

}
