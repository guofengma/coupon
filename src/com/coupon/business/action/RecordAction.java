package com.coupon.business.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import com.coupon.business.entity.Customer;
import com.coupon.business.entity.Product;
import com.coupon.business.entity.Record;
import com.coupon.business.entity.RedeemCode;
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
public class RecordAction extends BaseAction{
	
	@Autowired UserService userService;
	
	@Autowired CustomerService customerService;
	
	@Autowired CityService cityService;
	
	@Autowired BankService bankService;
	
	@Autowired RecordService recordService;
	
	private static HSSFWorkbook workbook = null; 

	/**
	 * 获取交易记录
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/record/app/myRecord")
	public String myRecord(HttpServletRequest request, ModelMap model) {
		String name = CookieUtil.getCookie(request, "name_EN");
		Customer customer = customerService.findByPhone(name);
		if(null == customer)
			return "appindex";
		List<Record> records = customer.getRecord();
		List<Record> myRecords = new ArrayList<Record>();
		for(Record temp : records){
			if(null != temp.getProduct()){
				myRecords.add(temp);
			}
		}
		model.addAttribute("myRecords",myRecords);
		return "app/myRecord";
	}
	
	/**
	 * 订单详情
	 * @param request
	 * @param model
	 * @param 订单id
	 * @return
	 */
	@RequestMapping(value = "/record/app/recordDetail")
	public String recordDetail(HttpServletRequest request, ModelMap model,String id) {
		Record record = recordService.findById(id);
		Product product = record.getProduct();
		RedeemCode redeemCode = record.getRedeemCode();
		model.addAttribute("record",record);
		model.addAttribute("product",product); 
		model.addAttribute("redeemCode",redeemCode);
		return "app/recordDetail";
	}
	
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
	
	
	/*
	 * 查询所有的普通员工
	 */
	@RequestMapping(value = "/business/record/achievement")
	public String achievement(HttpServletRequest request, ModelMap model) {
		super.addMenuParams(request, model);
		List<User> users = userService.getAllUsers();
		List<User> yuangong = new ArrayList<User>();
		for(User temp : users){
			if(isStaff(temp))
				yuangong.add(temp);
		}
		model.addAttribute("yuangong",yuangong);
		return "business/record/achievement";
	}
	
	public static boolean isStaff(User user){
		StringBuilder roleString = new StringBuilder() ;
		Set<Role> roles = user.getRoles();
		for(Role temp : roles){
			roleString.append(temp.getName()+";");
		}
		if(roleString.toString().contains("员工"))
			return true ;
		else
			return false ;
	}
	
	/*
	 * 根据员工，查询各自对应的待办事项
	 */
	@RequestMapping(value = "/business/record/undeal")
	public String list(HttpServletRequest request, ModelMap model) {
		super.addMenuParams(request, model);
		int pageNo = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_NO_NAME, PageListUtil.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_SIZE_NAME, PageListUtil.DEFAULT_PAGE_SIZE);
		User user = userService.findByUserName(CookieUtil.getCookie(request, "name_EN"));
		StringBuilder roleString = new StringBuilder() ;
		Set<Role> roles = user.getRoles();
		for(Role temp : roles){
			roleString.append(temp.getName()+";");
		}
		if(roleString.toString().contains("管理员")){
			IPageList<Record> records = recordService.findUndealByAdmin(pageNo, pageSize);
			model.addAttribute("records",records);
		}
		if(roleString.toString().contains("大区经理")){
			StringBuilder cityIds = new StringBuilder();
			for(City temp : user.getCity()){
				cityIds.append(temp.getId()+";");
			}
			IPageList<Record> records = recordService.findUndealByManager(pageNo, pageSize,cityIds.toString());
			model.addAttribute("records",records);
		}
		if(roleString.toString().contains("员工")){
			IPageList<Record> records = recordService.findUndealByStaff(pageNo, pageSize,user.getId());
			model.addAttribute("records",records);
		}
		return "business/record/undealList";		
	}
	
	/*
	 * 单个审核新增的记录（后台充值）
	 */
	@RequestMapping(value = "/business/record/check")
	public String check(HttpServletRequest request, ModelMap model) {
		User user = userService.findByUserName(CookieUtil.getCookie(request, "name_EN"));
		String id = request.getParameter("id");
		boolean pass = request.getParameter("pass").equals("true");
		Record record = recordService.findById(id);
		Customer customer = record.getCustomer();
		record.setDeal(pass);
		record.setCheckUser(user);
		record.setDeal(true);
		if(pass){//通过审核
			record.setStatu(true);
			customer.setTotalAddUp(customer.getTotalAddUp()+record.getPoints());
			customer.setPoint(customer.getPoint()+record.getPoints());
			customer.setLatestChargeTime(new Date());
			customer.setLatestChargeUser(record.getUser());
		}
		recordService.update(record);
		customerService.update(customer);
		return "redirect:undeal";
	}
	
	/*
	 * 单个记录重新请求审核
	 */
	@RequestMapping(value = "/business/record/requestCheck")
	public String requestCheck(HttpServletRequest request, ModelMap model) {
		String id = request.getParameter("id");
		Record record = recordService.findById(id);
		record.setDeal(false);
		recordService.update(record);
		return "redirect:undeal";
	}
	
	/*
	 * 批量审核新增的用户
	 */
	@RequestMapping(value = "/business/record/findAchievementByStaff")
	public void findAchievementByStaff(HttpServletRequest request, ModelMap model ,HttpServletResponse response) throws IOException {
		StringBuilder result = new StringBuilder("[");
		String userId = request.getParameter("id");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		List<Record> records = recordService.findAchievementByStaff(userId,startTime,endTime);
		for(Record temp : records){
			String type = "后台充值";
			if(temp.getRechargeCode()!=null)
				type = temp.getRechargeCode().getCode();
			if(temp.getCustomer()!=null)
				result.append("{\"name\":\""+temp.getCustomer().getName()+"\",\"points\":"+temp.getPoints()+",\"type\":\""+type+"\",\"time\":\""+temp.getModifiedTime().toString()+"\"},");
			else
				result.append("{\"name\":\"\",\"points\":"+temp.getPoints()+",\"type\":\""+type+"\",\"time\":\""+temp.getModifiedTime().toString()+"\"},");
		}
		if(records.size()>0)
			result.deleteCharAt(result.length()-1);
		result.append("]");
		response.setContentType("application/json");
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write(result.toString());
	}
	
	/*
	 * 批量审核新增的用户
	 */
	@RequestMapping(value = "/business/record/multiCheck")
	public void multiCheck(HttpServletRequest request, ModelMap model ,HttpServletResponse response) throws IOException {
		User user = userService.findByUserName(CookieUtil.getCookie(request, "name_EN"));
		String ids[] = request.getParameter("ids").split(";");
		boolean pass = request.getParameter("pass").equals("true");
		List<Record> records = recordService.findByIds(ids);
		List<Customer> customers = new ArrayList<Customer>();
		for(Record record : records){
			Customer customer = record.getCustomer();
			record.setDeal(pass);
			record.setCheckUser(user);
			record.setDeal(true);
			if(pass){//通过审核
				record.setStatu(true);
				customer.setTotalAddUp(customer.getTotalAddUp()+record.getPoints());
				customer.setPoint(customer.getPoint()+record.getPoints());
				customer.setLatestChargeTime(new Date());
				customer.setLatestChargeUser(record.getUser());
			}
			customers.add(customer);
		}
		customerService.batchUpdate(customers);
		recordService.batchUpdate(records);
		response.setContentType("application/json");
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write("{\"msg\":\"批量审核充值记录成功\"}");
	}
	
	/*
	 * 导出查询结果
	 */
	@RequestMapping(value = "/business/record/exportAchievement")
	public void exportAchievement(HttpServletResponse response,HttpServletRequest request, ModelMap model) throws Exception {
		String userId = request.getParameter("id");
		User user = userService.findById(userId);
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		List<Record> records = recordService.findAchievementByStaff(userId,startTime,endTime);
		String prefix = System.currentTimeMillis() + "";
		String path = request.getSession().getServletContext().getRealPath("/")+ "excel\\" + FolderUtil.getFolder();
		String temp = path +"\\" + prefix +user.getDisplayName()+"业绩.xls";
		String fileDir = temp;
		if(fileExist(fileDir))
			deleteExcel(fileDir);
		File dir = new File(path);
		if(!dir.exists()){
			dir.mkdirs();
		}
		File excelFile = new File(fileDir);
		    excelFile.createNewFile();
		createExcel(fileDir,"sheet1",new String[]{"客户姓名","充值分数","充值类型","时间"});
		if(sheetExist(fileDir,"sheet1"))
			writeToExcel(fileDir,"sheet1",records);
		//设置文件MIME类型  
        response.setContentType("application/x-msdownload");
        //设置编码方式
        response.setCharacterEncoding("utf-8");
        //设置Content-Disposition  
        response.setHeader("Content-Disposition", "attachment;filename="+ new String((user.getDisplayName()+"业绩"+startTime+"到"+endTime+".xls").getBytes("GBK"), "ISO-8859-1")); 
         
         //读取目标文件，通过response将目标文件写到客户端  
         //读取文件  
        InputStream in = new FileInputStream(fileDir);  
        OutputStream out = response.getOutputStream();  
        //写文件  
        int b;  
        while((b=in.read())!= -1)  
        {  
           out.write(b);  
        }  		           
        in.close();  
        out.close();  
}

/** 
 * 判断文件是否存在. 
 * @param fileDir  文件路径 
 * @return 
 */  
public static boolean fileExist(String fileDir){  
     boolean flag = false;  
     File file = new File(fileDir);  
     flag = file.exists();  
     return flag;  
}  
/** 
 * 判断文件的sheet是否存在. 
 * @param fileDir   文件路径 
 * @param sheetName  表格索引名 
 * @return 
 */  
public static boolean sheetExist(String fileDir,String sheetName) throws Exception{  
     boolean flag = false;  
     File file = new File(fileDir);  
     if(file.exists()){    //文件存在  
        //创建workbook  
         try {  
            workbook = new HSSFWorkbook(new FileInputStream(file));  
            //添加Worksheet（不添加sheet时生成的xls文件打开时会报错)  
            HSSFSheet sheet = workbook.getSheet(sheetName);    
            if(sheet!=null)  
                flag = true;  
        } catch (Exception e) {  
            throw e;
        }   
          
     }else{    //文件不存在  
         flag = false;  
     }  
     return flag;  
}  
/** 
 * 创建新excel. 
 * @param fileDir  excel的路径 
 * @param sheetName 要创建的表格索引 
 * @param titleRow excel的第一行即表格头 
 */  
public static void createExcel(String fileDir,String sheetName,String titleRow[]) throws Exception{  
    //创建workbook  
    workbook = new HSSFWorkbook();  
    //添加Worksheet（不添加sheet时生成的xls文件打开时会报错)  
    HSSFSheet sheet1 = workbook.createSheet(sheetName);    
    //新建文件  
    FileOutputStream out = null;  
    try {  
        //添加表头  
        HSSFRow row = workbook.getSheet(sheetName).createRow(0);    //创建第一行    
        for(short i = 0;i < titleRow.length;i++){  
            HSSFCell cell = row.createCell(i);  
            cell.setCellValue(titleRow[i]);  
        }  
        out = new FileOutputStream(fileDir);  
        workbook.write(out);  
    } catch (Exception e) {  
        throw e;
    } finally {    
        try {  
        	if(out!=null)
        		out.close();    
        } catch (IOException e) {    
            e.printStackTrace();  
        }    
    }    
}  
/** 
 * 删除文件. 
 * @param fileDir  文件路径 
 */  
public static boolean deleteExcel(String fileDir) {  
    boolean flag = false;  
    File file = new File(fileDir);  
    // 判断目录或文件是否存在    
    if (!file.exists()) {  // 不存在返回 false    
        return flag;    
    } else {    
        // 判断是否为文件    
        if (file.isFile()) {  // 为文件时调用删除文件方法    
            file.delete();  
            flag = true;  
        }   
    }  
    return flag;  
}  
/** 
 * 往excel中写入(已存在的数据无法写入). 
 * @param fileDir    文件路径 
 * @param sheetName  表格索引 
 * @param object 
 * @throws Exception 
 */  
public static void writeToExcel(String fileDir,String sheetName,List<Record> list) throws Exception{  
    //创建workbook  
    File file = new File(fileDir);  
    try {  
        workbook = new HSSFWorkbook(new FileInputStream(file));  
    } catch (FileNotFoundException e) {  
        e.printStackTrace();  
    } catch (IOException e) {  
        e.printStackTrace();  
    }  
    //流  
    FileOutputStream out = null; 
    HSSFSheet sheet = workbook.getSheet(sheetName);  
    // 获取表格的总行数  
    // int rowCount = sheet.getLastRowNum() + 1; // 需要加一  
    try {  
        // 获得表头行对象  
        HSSFRow titleRow = sheet.getRow(0);  
        if(titleRow!=null){ 
            for(int rowId=0;rowId<list.size();rowId++){
                HSSFRow newRow=sheet.createRow(rowId+1);
                HSSFCell cell0 = newRow.createCell(0);
                HSSFCell cell1 = newRow.createCell(1); 
                HSSFCell cell2 = newRow.createCell(2); 
                HSSFCell cell3 = newRow.createCell(3);
              
                cell0.setCellValue(list.get(rowId).getCustomer()==null?"":list.get(rowId).getCustomer().getName());  
                cell1.setCellValue(list.get(rowId).getPoints());
                cell2.setCellValue(list.get(rowId).getRechargeCode()==null?"后台充值":list.get(rowId).getRechargeCode().getCode()); 
                cell3.setCellValue(list.get(rowId).getModifiedTime().toString());  
            }
        }  
        out = new FileOutputStream(fileDir);
        workbook.write(out);  
    } catch (Exception e) {  
        throw e;
    } finally {    
        try {    
        	out.close();    
        } catch (IOException e) {    
            e.printStackTrace();  
        }    
    }    
}  
}
