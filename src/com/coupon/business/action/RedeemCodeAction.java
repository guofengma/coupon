package com.coupon.business.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.coupon.base.action.BaseAction;
import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageList;
import com.coupon.base.common.paging.PageListUtil;
import com.coupon.base.common.utils.CookieUtil;
import com.coupon.business.entity.Product;
import com.coupon.business.entity.RedeemCode;
import com.coupon.business.service.ProductService;
import com.coupon.business.service.RedeemCodeService;
import com.coupon.security.MyRealm;
import com.coupon.system.entity.City;
import com.coupon.system.entity.User;
import com.coupon.system.service.CityService;
import com.coupon.system.service.UserService;
import com.coupon.util.FolderUtil;

@Controller
public class RedeemCodeAction extends BaseAction{
	
	@Autowired
	private ProductService productService ;
	@Autowired
	private RedeemCodeService redeemCodeService ;
	@Autowired 
	private UserService userService;
	@Autowired 
	private CityService cityService;
	
	/*
	 * 根据product的id查找相关的批次，并分页
	 */
	@RequestMapping(value = "/business/redeemCode/batchlist")
	public String batchlist(HttpServletRequest request, ModelMap model) {
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
		return "business/redeemCode/batchList";
	}
	
	/*
	 * 单击批次后面的兑换码管理，跳转到兑换码页面
	 */
	@RequestMapping(value = "/business/redeemCode/list")
	public String list(HttpServletRequest request, ModelMap model) {
		super.addMenuParams(request, model);
		String id = request.getParameter("id");
		RedeemCode batch = redeemCodeService.findById(id);
		model.addAttribute("batch",batch);
		return "business/redeemCode/list";
	}
	
	/*
	 * 根据查询条件，查询符合条件的兑换码
	 */
	@RequestMapping(value = "/search/redeemCode/findByCondition")
	public String findByCondition(HttpServletRequest request, ModelMap model) {
		super.addMenuParams(request, model);
		List<City> fCityUsedList = cityService.getFCityUsed();
		model.addAttribute("fCityUsedList",fCityUsedList);
		int pageNo = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_NO_NAME, PageListUtil.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_SIZE_NAME, PageListUtil.DEFAULT_PAGE_SIZE);
		String exStartTime = request.getParameter("exStartTime")==null?"":request.getParameter("exStartTime");
		String exEndTime = request.getParameter("exEndTime")==null?"":request.getParameter("exEndTime");
		String startTime = request.getParameter("startTime")==null?"":request.getParameter("startTime");
		String endTime = request.getParameter("endTime")==null?"":request.getParameter("endTime");
		String name = request.getParameter("name")==null?"": request.getParameter("name");
		String code = request.getParameter("code")==null?"":request.getParameter("code");
		String statu = request.getParameter("statu")==null?"null":request.getParameter("statu");
		String fCity = request.getParameter("fCity")==null?"null":request.getParameter("fCity");
		String sCity = request.getParameter("sCity")==null?"null":request.getParameter("sCity");
		String city = sCity.equals("null")?fCity:sCity;
		String phone = request.getParameter("phone")==null?"":request.getParameter("phone");
		String condition[] = new String[]{exStartTime,exEndTime,startTime,endTime,name,code,statu,city,phone};
		PageList<RedeemCode> redeemCodes = redeemCodeService.findByCondition(pageNo,pageSize,condition);
		model.addAttribute("redeemCodes",redeemCodes);
		model.addAttribute("exStartTime",exStartTime);
		model.addAttribute("exEndTime",exEndTime);
		model.addAttribute("startTime",startTime);
		model.addAttribute("endTime",endTime);
		model.addAttribute("name",name);
		model.addAttribute("code",code);
		model.addAttribute("statu",statu);
		model.addAttribute("fCity",fCity);
		model.addAttribute("sCity",sCity);
		model.addAttribute("phone",phone);
		model.addAttribute("date",FolderUtil.getFolder());
		return "search/redeemCode/list";
	}
	
	
	/*
	 *根据批次，获取批次下的兑换码
	 */
	@RequestMapping(value = "/business/redeemCode/getRedeemCodeByBatch")
	public void getRedeemCodeByBatch(HttpServletRequest request, ModelMap model,HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		RedeemCode batch = redeemCodeService.findById(id);
		StringBuilder result = new StringBuilder("[");
		for(RedeemCode temp : batch.getChildrenOrderByUsed()){
			result.append("{\"code\":\""+temp.getCode()+"\",\"used\":"+temp.isUsed()+",\"statu\":"+temp.isStatu()+",\"id\":\""+temp.getId()+"\"},");
		}
		if(batch.getChildren().size()!=0)
			result.deleteCharAt(result.length()-1);
		result.append("]");
		System.out.println(result.toString());
		response.setContentType("application/json");
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write(result.toString());
	}
	
	@RequestMapping(value = "/business/redeemCode/changeState")//启用停用单个兑换码
	public void closeRedeemCode(HttpServletRequest request, ModelMap model,HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		RedeemCode redeemCode = redeemCodeService.findById(id);
		boolean state = request.getParameter("state").equals("false")?false:true;
		redeemCode.setStatu(state);
		redeemCodeService.update(redeemCode);
		response.setContentType("application/json");
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write("{\"result\":\"success\"}");
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
		redeemCode.setUser(userService.findByUserName(CookieUtil.getCookie(request, "name_EN")));
		if(oldId.equals("null")){
			redeemCodeService.save(redeemCode);
		}else{
			redeemCodeService.update(redeemCode);
		}
		model.addAttribute("id",productId);
		return "redirect:batchlist";
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
		return "redirect:batchlist";
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
	 * 改变批次可用状况
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
	
	/*
	 * 为某批次导入兑换码
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/business/redeemCode/importRedeemCode")
	public String importRedeemCode(HttpServletRequest request, ModelMap model,HttpServletResponse response) throws Exception {
		super.addMenuParams(request, model);
		@SuppressWarnings("unused")
		String importResult = "";
		RedeemCode batch = new RedeemCode();//是虚拟出来的批次
		request.setCharacterEncoding("utf-8");
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List items = upload.parseRequest(request);
		Iterator iter = items.iterator();//用来上传文件
		while (iter.hasNext()) // 表单中有几个input标签，就循环几次
		{
			FileItem item = (FileItem) iter.next();
			if (item.isFormField()) {
				if(item.getFieldName().equals("fatherId"))
					batch = redeemCodeService.findById(item.getString("utf-8"));
			} else {
					String fileName = item.getName();
					// 这里发现ie获取的是路径加文件名，chrome获取的是文件名，这里我们只需要文件名，所以有路径的要先去路径
					fileName = fileName.substring(fileName.lastIndexOf("\\") + 1,
							fileName.length());
					String prefix = System.currentTimeMillis() + "";
					File file = new File(request.getSession().getServletContext().getRealPath("/")
							+ "excel\\" + FolderUtil.getFolder());
					if (!file.exists())
						file.mkdirs();
					File uploadedFile = new File(request.getSession().getServletContext().getRealPath("/")+ "excel\\"+FolderUtil.getFolder()+ "\\"+ prefix + fileName);
					item.write(uploadedFile);
					importResult = importToBase(request.getSession().getServletContext().getRealPath("/")+ "excel\\"+FolderUtil.getFolder()+ "\\"+ prefix + fileName,batch.getId());
			}
		}
		model.addAttribute("id",batch.getProduct().getId());
		return "redirect:batchlist";
	}
	
	/*
	 * 删除单个兑换码（硬删除）
	 */
	@RequestMapping(value = "/business/redeemCode/deleteRedeemCode")
	public void deleteRedeemCode(HttpServletRequest request, ModelMap model,HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		RedeemCode redeemCode = redeemCodeService.findById(id);
		redeemCode.setParent(null);;
		redeemCode.setDeleted(true);
		redeemCodeService.update(redeemCode); 
		response.setContentType("application/json");
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write("{\"flag\":\"success\",\"msg\":\"删除成功\"}");
	}
	
	@SuppressWarnings("resource")
	private String importToBase(String filePath,String batchId) throws Exception{
		RedeemCode batch = redeemCodeService.findById(batchId);
		List<RedeemCode> addList = new ArrayList<RedeemCode>();
		String result  = "" ;
		int count = 0 ;
	 		try { 
	 			  String fileType = filePath.substring(filePath.lastIndexOf(".")+1);
		 		  Workbook workbook = null;
		          InputStream is = new FileInputStream(filePath);
		          if (fileType.equalsIgnoreCase("xlsx")) {
		            workbook = new XSSFWorkbook(is);
		          }else if(fileType.equalsIgnoreCase("xls")){
		            workbook = new HSSFWorkbook(is);
		          }else {
		        	throw new Exception("暂时只支持xlsx和xls格式的excel读取");
		          }
		          
		          for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
	        	        Sheet sheetAt = workbook.getSheetAt(numSheet);
	        	        if (sheetAt == null) {
	        	         continue;
	        	        }
	        	        // 循环行Row
	        	        for (int rowNum = 0; rowNum <= sheetAt.getLastRowNum(); rowNum++) {
		        	         Row row = sheetAt.getRow(rowNum);
		        	         if (row != null) {
		        	        	    count++;
			                        row.getCell(0).setCellType(Cell.CELL_TYPE_STRING); 
			                        RedeemCode temp = new RedeemCode();
			                        temp.setParent(batch);
			                        temp.setCode(row.getCell(0).getStringCellValue());
			                        temp.setUsed(false);
			                        addList.add(temp)  ;
		        	         }
	        	        }
		            redeemCodeService.batchSave(addList);
		            result = "成功添加兑换码"+count+"个";
		        }
	 		}catch (Exception e) {  
	        	result = e.getMessage();
	            e.printStackTrace();  
		     }  
	 	return result;
	}
}
