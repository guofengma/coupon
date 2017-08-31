package com.coupon.business.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
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
import com.coupon.business.entity.Customer;
import com.coupon.business.entity.RechargeCode;
import com.coupon.business.entity.Record;
import com.coupon.business.service.BankService;
import com.coupon.business.service.CustomerService;
import com.coupon.business.service.RechargeCodeService;
import com.coupon.business.service.RecordService;
import com.coupon.security.MyRealm;
import com.coupon.system.entity.City;
import com.coupon.system.entity.Role;
import com.coupon.system.entity.User;
import com.coupon.system.service.CityService;
import com.coupon.system.service.UserService;
import com.coupon.util.FolderUtil;

@Controller
public class CustomerAction extends BaseAction{
	
	@Autowired UserService userService;
	
	@Autowired CustomerService customerService;
	
	@Autowired RechargeCodeService rechargeCodeService;
	
	@Autowired CityService cityService;
	
	@Autowired BankService bankService;
	
	@Autowired RecordService recordService;
	
	private static HSSFWorkbook workbook = null;  
	
	@RequestMapping(value = "/business/customer/list")
	public String list(HttpServletRequest request, ModelMap model) {
		super.addMenuParams(request, model);
		List<City> fCityUsedList = cityService.getFCityUsed();
		model.addAttribute("fCityUsedList",fCityUsedList);
		int pageNo = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_NO_NAME, PageListUtil.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_SIZE_NAME, PageListUtil.DEFAULT_PAGE_SIZE);
		boolean check = request.getParameter("statu").equals("check")? true : false ;
		String condition = request.getParameter("condition")==null?"":request.getParameter("condition");
		model.addAttribute("condition",condition);
		System.out.println(request.getParameter("statu")+check);
		User user = userService.findByUserName(MyRealm.hardName);
		StringBuilder roleString = new StringBuilder() ;
		Set<Role> roles = user.getRoles();
		for(Role temp : roles){
			roleString.append(temp.getName()+";");
		}
		if(roleString.toString().contains("管理员")){
			IPageList<Customer> customers = customerService.findByAdmin(pageNo, pageSize,check,condition);
			model.addAttribute("customers",customers);
			return "business/customer/"+(check?"checkList":"uncheckList");	
		}
		if(roleString.toString().contains("大区经理")){
			StringBuilder cityIds = new StringBuilder();
			for(City temp : user.getCity()){
				cityIds.append(temp.getId()+";");
			}
			IPageList<Customer> customers = customerService.findByManager(pageNo, pageSize,check,cityIds.toString(),condition);
			model.addAttribute("customers",customers);
			return "business/customer/"+(check?"checkList":"uncheckList");	
		}
		if(roleString.toString().contains("员工")){
			IPageList<Customer> customers = customerService.findByStaff(pageNo, pageSize,check,user.getId(),condition);
			model.addAttribute("customers",customers);
			return "business/customer/"+(check?"checkList":"uncheckList");	
		}
		return "business/customer/"+(check?"checkLsit":"uncheckList");		
	}
	
	/*
	 * 根据查询条件，查询符合条件的客户
	 */
	@RequestMapping(value = "/search/customer/findByCondition")
	public String findByCondition(HttpServletRequest request, ModelMap model) {
		super.addMenuParams(request, model);
		int pageNo = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_NO_NAME, PageListUtil.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_SIZE_NAME, PageListUtil.DEFAULT_PAGE_SIZE);
		List<City> fCityUsedList = cityService.getFCityUsed();
		model.addAttribute("fCityUsedList",fCityUsedList);
		User user = userService.findByUserName(MyRealm.hardName);
		StringBuilder roleString = new StringBuilder() ;
		String roleType = "";
		Set<Role> roles = user.getRoles();
		for(Role temp : roles){
			roleString.append(temp.getName()+";");
		}
		if(roleString.toString().contains("管理员")){
			roleType = "1";
		}
		if(roleString.toString().contains("大区经理")){
			roleType = "2";
		}
		if(roleString.toString().contains("员工")){
			roleType = "3";
		}
		String startTime = request.getParameter("startTime")==null?"":request.getParameter("startTime");
		String endTime = request.getParameter("endTime")==null?"":request.getParameter("endTime");
		String name = request.getParameter("name")==null?"": request.getParameter("name");
		String latestName = request.getParameter("latestName")==null?"":request.getParameter("latestName");
		String fCity = request.getParameter("fCity")==null?"null":request.getParameter("fCity");
		String sCity = request.getParameter("sCity")==null?"null":request.getParameter("sCity");
		String city = sCity.equals("null")?fCity:sCity;
		String phone = request.getParameter("phone")==null?"":request.getParameter("phone");
		String condition[] = new String[]{startTime,endTime,name,latestName,city,phone,roleType};
		PageList<Customer> customers = customerService.findByCondition(pageNo,pageSize,user,condition);
		model.addAttribute("customers",customers);
		model.addAttribute("startTime",startTime);
		model.addAttribute("endTime",endTime);
		model.addAttribute("name",name);
		model.addAttribute("latestName",latestName);
		model.addAttribute("fCity",fCity);
		model.addAttribute("sCity",sCity);
		model.addAttribute("phone",phone);
		model.addAttribute("date",FolderUtil.getFolder());
		return "search/customer/list";
	}
	

	/*
	 * 获取可用的积分码批次
	 */
	@RequestMapping(value = "/business/customer/recharegeCodeGive")
	public String recharegeCodeGive(HttpServletRequest request, ModelMap model) {
		super.addMenuParams(request, model);
		List<RechargeCode> batch = rechargeCodeService.findCanBeGivenBatch();
		model.addAttribute("batch",batch);
		return "business/customer/list";
	}
	
	/*
	 * 导出查询结果
	 */
	@RequestMapping(value = "/search/customer/exportCustomer")
	public void exportCustomer(HttpServletResponse response,HttpServletRequest request, ModelMap model) throws Exception {
		User user = userService.findByUserName(MyRealm.hardName);
		StringBuilder roleString = new StringBuilder() ;
		String roleType = "";
		Set<Role> roles = user.getRoles();
		for(Role temp : roles){
			roleString.append(temp.getName()+";");
		}
		if(roleString.toString().contains("管理员")){
			roleType = "1";
		}
		if(roleString.toString().contains("大区经理")){
			roleType = "2";
		}
		if(roleString.toString().contains("员工")){
			roleType = "3";
		}
		String startTime = request.getParameter("startTime")==null?"":request.getParameter("startTime");
		String endTime = request.getParameter("endTime")==null?"":request.getParameter("endTime");
		String name = request.getParameter("name")==null?"": request.getParameter("name");
		String latestName = request.getParameter("latestName")==null?"":request.getParameter("latestName");
		String fCity = request.getParameter("fCity")==null?"null":request.getParameter("fCity");
		String sCity = request.getParameter("sCity")==null?"null":request.getParameter("sCity");
		String city = sCity.equals("null")?fCity:sCity;
		String phone = request.getParameter("phone")==null?"":request.getParameter("phone");
		String condition[] = new String[]{startTime,endTime,name,latestName,city,phone,roleType};
		List <Customer> customers = customerService.exportByCondition(user,condition);
		String prefix = System.currentTimeMillis() + "";
		String path = request.getServletContext().getRealPath("/")+ "excel\\" + FolderUtil.getFolder();
		String temp = path +"\\" + prefix +"customer.xls";
		String fileDir = temp;
		if(fileExist(fileDir))
			deleteExcel(fileDir);
		File dir = new File(path);
		if(!dir.exists()){
			dir.mkdirs();
		}
		File excelFile = new File(fileDir);
		    excelFile.createNewFile();
		createExcel(fileDir,"sheet1",new String[]{"客户姓名","电话","最后一次充值人员","最后一次充值时间","累计兑换积分数量","剩余积分数量","城市","备注"});
		if(sheetExist(fileDir,"sheet1"))
			writeToExcel(fileDir,"sheet1",customers);
		//设置文件MIME类型  
        response.setContentType("application/x-msdownload");
        //设置编码方式
        response.setCharacterEncoding("utf-8");
        //设置Content-Disposition  
        response.setHeader("Content-Disposition", "attachment;filename="+ new String((FolderUtil.getFolder()+".xls").getBytes("GBK"), "ISO-8859-1")); 
         
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
public static void writeToExcel(String fileDir,String sheetName,List<Customer> list) throws Exception{  
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
                HSSFCell cell4 = newRow.createCell(4);
                HSSFCell cell5 = newRow.createCell(5);
                HSSFCell cell6 = newRow.createCell(6);
                HSSFCell cell7 = newRow.createCell(7);
                cell0.setCellValue(list.get(rowId).getName().toString());  
                cell1.setCellValue(list.get(rowId).getPhone().toString());
                cell2.setCellValue(list.get(rowId).getLatestChargeUser()==null?"":list.get(rowId).getLatestChargeUser().getName()); 
                cell3.setCellValue(list.get(rowId).getLatestChargeTime()==null?"":list.get(rowId).getLatestChargeTime().toString());  
                cell4.setCellValue(list.get(rowId).getTotalAddUp()+"");
                cell5.setCellValue(list.get(rowId).getPoint()+"");
                List<City> city = list.get(rowId).getCustomerCityByPriority();
                StringBuilder cityNames = new StringBuilder();
                for(City temp : city){
                	if(temp.getParent()==null)
                		cityNames.append("【"+temp.getName()+"】 ");
                	else
                		cityNames.append(temp.getName()+" ");
                }
                cell6.setCellValue(cityNames.toString());
                cell7.setCellValue(list.get(rowId).getRemark());
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

	/*
	 * 单个审核新增的用户
	 */
	@RequestMapping(value = "/business/customer/check")
	public String check(HttpServletRequest request, ModelMap model) {
		User user = userService.findByUserName(MyRealm.hardName);
		String id = request.getParameter("id");
		boolean pass = request.getParameter("pass").equals("true");
		Customer customer = customerService.findById(id);
		if(pass){
			Record record = new Record();
			record.setCustomer(customer);
			record.setPoints(customer.getPoint());
			record.setRaise(true);
			record.setDeal(true);
			record.setStatu(true);
			record.setUser(customer.getUser());
			record.setCheckUser(user);
			recordService.save(record);
		}
		customer.setDeal(true);
		customer.setStatu(pass);
		customer.setCheckUser(user);
		customerService.update(customer);
		model.addAttribute("statu",false);
		model.addAttribute("condition",request.getParameter("condition"));
		return "redirect:list";
	}
	
	/*
	 * 单个用户重新请求审核
	 */
	@RequestMapping(value = "/business/customer/requestCheck")
	public String requestCheck(HttpServletRequest request, ModelMap model) {
		String id = request.getParameter("id");
		Customer customer = customerService.findById(id);
		customer.setDeal(false);
		customerService.update(customer);
		model.addAttribute("statu",false);
		model.addAttribute("condition",request.getParameter("condition"));
		return "redirect:list";
	}
	
	/*
	 * 批量审核新增的用户
	 */
	@RequestMapping(value = "/business/customer/multiCheck")
	public void multiCheck(HttpServletRequest request, ModelMap model ,HttpServletResponse response) throws IOException {
		User user = userService.findByUserName(MyRealm.hardName);
		String ids[] = request.getParameter("ids").split(";");
		boolean pass = request.getParameter("pass").equals("true");
		List<Customer> customers = customerService.findByIds(ids);
		List<Record> records = new ArrayList<Record>();
		for(Customer temp:customers){
			if(pass){
				Record record = new Record();
				record.setCustomer(temp);
				record.setPoints(temp.getPoint());
				record.setRaise(true);
				record.setDeal(true);
				record.setStatu(true);
				record.setUser(temp.getUser());
				record.setCheckUser(user);
				records.add(record);
			}
			temp.setDeal(true);
			temp.setStatu(pass);
			temp.setCheckUser(user);
		}
		recordService.batchSave(records);
		customerService.batchUpdate(customers);
		response.setContentType("application/json");
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write("{\"msg\":\"批量审核用户成功\"}");
	}

	/*
	 * 保存未审核的用户
	 */
	@RequestMapping(value = "/business/customer/unCheckSave")
	public void save(HttpServletRequest request, ModelMap model,HttpServletResponse response) throws IOException {
		Customer customer = new Customer();
		boolean isNew = request.getParameter("isNew").equals("true")?true:false;
		String fCity = request.getParameter("fCity")==null?"null":request.getParameter("fCity");
		String sCity = request.getParameter("sCity")==null?"null":request.getParameter("sCity");
		String bankAddress = request.getParameter("bankAddress");
		Set<City> customerCity = new HashSet<City>();
		if(fCity.equals("null")){
			customerCity = null ;
		}else if(sCity.equals("null")){
			customerCity = cityService.findByIds(fCity.split(","));
		}else{
			customerCity = cityService.findByIds((fCity+","+sCity).split(","));
		}
		if(isNew){
			
		}else{
			String id = request.getParameter("id");
			customer = customerService.findById(id);
		}
		customer.setName(request.getParameter("name"));
		customer.setPhone(request.getParameter("phone"));
		customer.setPoint(Integer.parseInt(request.getParameter("points")));
		customer.setTotalAddUp(Integer.parseInt(request.getParameter("points")));
		customer.setRemark(request.getParameter("remark"));
		customer.setDeleted(false);
		customer.setBankAddress(bankAddress);
		User user = userService.findByUserName(MyRealm.hardName);
		customer.setUser(user);
		customer.setCity(customerCity);
		if(isNew)
			customerService.save(customer);
		else
			customerService.update(customer);
		response.setContentType("application/json");
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write("{\"msg\":\"保存成功\"}");
	}
	
	/*
	 * 单个删除还未通过审核的用户
	 */
	@RequestMapping(value = "/business/customer/delete")
	public String delete(HttpServletRequest request, ModelMap model) {
		String id = request.getParameter("id");
		Customer customer = customerService.findById(id);
		customer.setDeleted(true);
		customerService.update(customer);
		model.addAttribute("statu",false);
		model.addAttribute("condition",request.getParameter("condition"));
		return "redirect:list";
	}
	
	/*
	 * 获取未通过审核用户的信息
	 */
	@RequestMapping(value = "/business/customer/getCustomerInfo")
	public void getCustomerInfo(HttpServletRequest request, ModelMap model,HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		StringBuilder result = new StringBuilder();
		Customer customer = customerService.findById(id);
		result.append("{\"name\":\""+customer.getName()+"\",\"bankAddress\":\""+customer.getBankAddress()+"\",\"points\":"+customer.getPoint()+",\"phone\":\""+customer.getPhone()+"\",\"remark\":\""+customer.getRemark()+"\",");
		Set<City> city = customer.getCity();
		if(city.size()==0){//不属于任何城市
			result.append("\"fCityId\":\"null\",\"sCityId\":[]}");
		}else{
			StringBuilder sCity = new StringBuilder("[");
			City fCity = new City();
			for(City temp : city){
				if(temp.getParent()==null)
					fCity = temp ;
				else{
					sCity.append("\""+temp.getId()+"\",") ;
				}
			}
			if(city.size()>1){
				sCity.deleteCharAt(sCity.length()-1);
			}
			sCity.append("]");
			result.append("\"fCityId\":\""+fCity.getId()+"\",\"sCityId\":"+sCity.toString()+"}");
		}
		System.out.println(result.toString());
		response.setContentType("application/json");
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write(result.toString());
	}

	/*
	 * 为客户充值积分，增加一条记录，并设置成为未审核状态
	 */
	@RequestMapping(value = "/business/customer/recharge")
	public void recharge(HttpServletRequest request, ModelMap model,HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		int point = Integer.parseInt(request.getParameter("point"));
		Customer customer = customerService.findById(id);
		User user = userService.findByUserName(MyRealm.hardName);
		Record record = new Record();
		record.setCustomer(customer);
		record.setPoints(point);
		record.setRaise(true);
		record.setStatu(false);
		record.setUser(user);
		recordService.save(record);
		response.setContentType("application/json");
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write("{}");
	}
}
