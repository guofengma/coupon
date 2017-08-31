package com.coupon.business.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.coupon.business.entity.RechargeCode;
import com.coupon.business.entity.Record;
import com.coupon.business.entity.RedeemCode;
import com.coupon.business.service.RechargeCodeService;
import com.coupon.business.service.RecordService;
import com.coupon.security.MyRealm;
import com.coupon.system.entity.City;
import com.coupon.system.entity.User;
import com.coupon.system.service.CityService;
import com.coupon.system.service.UserService;
import com.coupon.util.FolderUtil;
import com.coupon.util.RandomCode;

@Controller
public class RechargeCodeAction extends BaseAction{

	@Autowired
	private RechargeCodeService rechargeCodeService ;
	@Autowired 
	private UserService userService;
	@Autowired 
	private CityService cityService;
	@Autowired 
	private RecordService recordService;
	
	private static HSSFWorkbook workbook = null;  
	/*
	 * 查询所有的积分充值卡批次，并分页
	 */
	@RequestMapping(value = "/business/rechargeCode/batchlist")
	public String batchlist(HttpServletRequest request, ModelMap model) {
		super.addMenuParams(request, model);
		int pageNo = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_NO_NAME, PageListUtil.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_SIZE_NAME, PageListUtil.DEFAULT_PAGE_SIZE);
		IPageList<RechargeCode>  rechargeCodes =  rechargeCodeService.findBatch(pageNo, pageSize);
		model.addAttribute("rechargeCodes",rechargeCodes);
		return "business/rechargeCode/batchList";
	}
	
	/*
	 * 根据查询条件，查询符合条件的e兑卡兑换码
	 */
	@RequestMapping(value = "/search/rechargeCode/findByCondition")
	public String findByCondition(HttpServletRequest request, ModelMap model) {
		super.addMenuParams(request, model);
		int pageNo = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_NO_NAME, PageListUtil.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_SIZE_NAME, PageListUtil.DEFAULT_PAGE_SIZE);
		List<City> fCityUsedList = cityService.getFCityUsed();
		model.addAttribute("fCityUsedList",fCityUsedList);
		String madeStartTime = request.getParameter("madeStartTime")==null?"":request.getParameter("madeStartTime");
		String madeEndTime = request.getParameter("madeEndTime")==null?"":request.getParameter("madeEndTime");
		String startTime = request.getParameter("startTime")==null?"":request.getParameter("startTime");
		String endTime = request.getParameter("endTime")==null?"":request.getParameter("endTime");
		String batch = request.getParameter("batch")==null?"": request.getParameter("batch");
		String points = request.getParameter("points")==null?"":request.getParameter("points");
		String code = request.getParameter("code")==null?"":request.getParameter("code");
		String keyt = request.getParameter("keyt")==null?"":request.getParameter("keyt");
		String statu = request.getParameter("statu")==null?"null":request.getParameter("statu");
		String fCity = request.getParameter("fCity")==null?"null":request.getParameter("fCity");
		String sCity = request.getParameter("sCity")==null?"null":request.getParameter("sCity");
		String city = sCity.equals("null")?fCity:sCity;
		String phone = request.getParameter("phone")==null?"":request.getParameter("phone");
		String condition[] = new String[]{madeStartTime,madeEndTime,startTime,endTime,batch,points,code,keyt,statu,city,phone};
		PageList<RechargeCode> rechargeCodes = rechargeCodeService.findByCondition(pageNo,pageSize,condition);
		model.addAttribute("rechargeCodes",rechargeCodes);
		model.addAttribute("madeStartTime",madeStartTime);
		model.addAttribute("madeEndTime",madeEndTime);
		model.addAttribute("startTime",startTime);
		model.addAttribute("endTime",endTime);
		model.addAttribute("batch",batch);
		model.addAttribute("points",points);
		model.addAttribute("code",code);
		model.addAttribute("keyt",keyt);
		model.addAttribute("statu",statu);
		model.addAttribute("fCity",fCity);
		model.addAttribute("sCity",sCity);
		model.addAttribute("phone",phone);
		model.addAttribute("date",FolderUtil.getFolder());
		return "search/rechargeCode/list";
	}
	

	/*
	 * 获取批次信息
	 */
	@RequestMapping(value = "/business/rechargeCode/editBatch")
	public void editBatch(HttpServletRequest request, ModelMap model,HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		StringBuilder result = new StringBuilder();
		RechargeCode rechargeCode = rechargeCodeService.findById(id);
		result.append("{\"batch\":\""+rechargeCode.getBatch()+"\",\"points\":\""+rechargeCode.getChildren().get(0).getPoints()+"\",\"count\":\""+rechargeCode.getChildren().size()+"\",\"endTime\":\""+rechargeCode.getEndTime().toString().substring(0, 10)+"\",\"remark\":\""+rechargeCode.getRemark()+"\"}");
		System.out.println(result.toString());
		response.setContentType("application/json");
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write(result.toString());
	}
	
	@RequestMapping(value = "/business/rechargeCode/changeStatu")//启用停用单个兑换码
	public void changeStatu(HttpServletRequest request, ModelMap model,HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		RechargeCode rechargeCode = rechargeCodeService.findById(id);
		boolean state = request.getParameter("state").equals("false")?false:true;
		rechargeCode.setStatu(state);
		rechargeCodeService.update(rechargeCode);
		response.setContentType("application/json");
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write("{\"result\":\"success\"}");
	}
	
	@RequestMapping(value = "/business/rechargeCode/changeGiven")
	public void changeGiven(HttpServletRequest request, ModelMap model,HttpServletResponse response) throws IOException, ParseException {
		User user = userService.findByUserName(MyRealm.hardName);
		String id = request.getParameter("id");
		String fafangTime =  request.getParameter("fafangTime").equals("")?FolderUtil.getFolder():request.getParameter("fafangTime");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		RechargeCode rechargeCode =rechargeCodeService.findById(id);
		boolean state = request.getParameter("state").equals("false")?false:true;
		if(state){//如果是发放，则记录到record中
			Record record = new Record();
			record.setModifiedTime(new Timestamp(formatter.parse(fafangTime).getTime()));
			record.setUser(user);
			record.setDeal(true);
			record.setStatu(true);
			record.setPoints(rechargeCode.getPoints());
			record.setRaise(true);
			record.setRechargeCode(rechargeCode);
			record.setDescription("e兑码");
			recordService.save(record);
		}else{//如果是取消发放，则删除记录
			String recordId = rechargeCode.getRecord().getId();
			rechargeCode.setRecord(null);
			recordService.delete(recordId);
		}
		rechargeCode.setGiven(state);
		rechargeCodeService.update(rechargeCode);
		response.setContentType("application/json");
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write("{\"result\":\"success\"}");
	}
	
	@RequestMapping(value = "/business/rechargeCode/batchSave")
	public String batchSave(HttpServletRequest request, ModelMap model,String productId,String oldId,String batch,String points , String count,String endTime,String remark) throws ParseException {
		super.addMenuParams(request, model);
		RechargeCode rechargeCode = new RechargeCode();
		if(oldId.equals("null")){
			rechargeCode.setStatu(true);
		}else{
			rechargeCode = rechargeCodeService.findById(oldId);
		}
		rechargeCode.setBatch(batch);
		rechargeCode.setEndTime(new SimpleDateFormat("yyyy-MM-dd").parse(endTime));
		rechargeCode.setRemark(remark);
		rechargeCode.setUser(userService.findByUserName(MyRealm.hardName));
		if(oldId.equals("null")){
			rechargeCodeService.save(rechargeCode);
			List<RechargeCode> addList = new ArrayList<RechargeCode>();
			for(int i=0;i<Integer.parseInt(count);i++){
				RechargeCode temp = new RechargeCode();
				temp.setCode(RandomCode.generate_18("ka"));
				temp.setKeyt(RandomCode.generate_18("mi"));
				temp.setStatu(true);
				temp.setPoints(Integer.parseInt(points));
		        temp.setParent(rechargeCode);
		        temp.setUsed(false);
		        addList.add(temp) ;
			}
			rechargeCodeService.batchSave(addList);
		}else{
			rechargeCodeService.update(rechargeCode);
		}
		return "redirect:batchlist";
	}
	
	/*
	 * 删除该批次和该批次下的所有积分码
	 */
	@RequestMapping(value = "/business/rechargeCode/deleteBatch")
	public String deleteBatch(HttpServletRequest request, ModelMap model,String productId,String oldId,String batch,String endTime,String remark) throws ParseException {
		super.addMenuParams(request, model);
		String id = request.getParameter("id");
		RechargeCode rechargeCode = rechargeCodeService.findById(id);
		rechargeCode.setDeleted(true);
		rechargeCodeService.update(rechargeCode);
		List<RechargeCode> children = rechargeCode.getChildren();
		for(RechargeCode temp : children){
			temp.setDeleted(true);
		}
		rechargeCodeService.batchUpdate(children);
		return "redirect:batchlist";
	}
	
	/*
	 * 改变批次可用状况
	 */
	@RequestMapping(value = "/business/rechargeCode/changeBatchState")
	public void changeBatchState(HttpServletRequest request, ModelMap model,HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		boolean state = request.getParameter("state").equals("false");
		RechargeCode rechargeCode = rechargeCodeService.findById(id);
		rechargeCode.setStatu(state);
		rechargeCodeService.update(rechargeCode);
		response.setContentType("application/json");
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write("{\"flag\":\"success\",\"msg\":\"改变批次可用状况成功\"}");
	}
	
	/*
	 * 制作某个批次下的积分码
	 */
	@RequestMapping(value = "/business/rechargeCode/makerechargeCode")
	public String makerechargeCode(HttpServletRequest request, ModelMap model,String fatherId , String points , String count ) throws ParseException {
		super.addMenuParams(request, model);
		RechargeCode batch = rechargeCodeService.findById(fatherId);
		List<RechargeCode> addList = new ArrayList<RechargeCode>();
		for(int i=0;i<Integer.parseInt(count);i++){
			RechargeCode temp = new RechargeCode();
			temp.setCode(RandomCode.generate_18("ka"));
			temp.setKeyt(RandomCode.generate_18("mi"));
			temp.setPoints(Integer.parseInt(points));
	        temp.setParent(batch);
	        temp.setUsed(false);
	        addList.add(temp) ;
		}
		rechargeCodeService.batchSave(addList);
		return "redirect:batchlist";
	}
	
	/*
	 * 单击批次后面的兑换码管理，跳转到兑换码页面
	 */
	@RequestMapping(value = "/business/rechargeCode/list")
	public String list(HttpServletRequest request, ModelMap model) {
		super.addMenuParams(request, model);
		String id = request.getParameter("id");
		RechargeCode batch = rechargeCodeService.findById(id);
		model.addAttribute("batch",batch);
		return "business/rechargeCode/list";
	}
	
	/*
	 *根据批次，获取批次下的积分码
	 */
	@RequestMapping(value = "/business/rechargeCode/getRechargeCodeByBatch")
	public void getRechargeCodeByBatch(HttpServletRequest request, ModelMap model,HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		RechargeCode batch = rechargeCodeService.findById(id);
		StringBuilder result = new StringBuilder("[");
		for(RechargeCode temp : batch.getChildrenOrderByUsed()){
			result.append("{\"points\":\""+temp.getPoints()+"\",\"code\":\""+temp.getCode()+"\",\"keyt\":\""+temp.getKeyt()+"\",\"used\":"+temp.isUsed()+",\"given\":"+temp.isGiven()+",\"statu\":"+temp.isStatu()+",\"id\":\""+temp.getId()+"\"},");
		}
		if(batch.getChildren().size()!=0)
			result.deleteCharAt(result.length()-1);
		result.append("]");
		System.out.println(result.toString());
		response.setContentType("application/json");
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write(result.toString());
	}
	
	@RequestMapping(value = "/business/rechargeCode/exportExcel")
	private void exportExcel(HttpServletResponse response, HttpServletRequest request, ModelMap model,String id) throws Exception {
			 RechargeCode batch = rechargeCodeService.findById(id);
			 String prefix = System.currentTimeMillis() + "";
			 String path = request.getServletContext().getRealPath("/")+ "excel\\" + FolderUtil.getFolder();
			 String temp = path +"\\" + prefix + batch.getBatch()+".xls";
			 String fileDir = temp;
			 if(fileExist(fileDir))
				 deleteExcel(fileDir);
			 File dir = new File(path);
			 if(!dir.exists()){
				 dir.mkdirs();
			 }
			 File excelFile = new File(fileDir);
			     excelFile.createNewFile();
			 createExcel(fileDir,"sheet1",new String[]{"分值","积分码","密钥","领取状态","使用状态"});
			 if(sheetExist(fileDir,"sheet1"))
				 writeToExcel(fileDir,"sheet1",batch.getChildrenOrderByUsed());
			 //设置文件MIME类型  
	         response.setContentType("application/x-msdownload");
	         //设置编码方式
	         response.setCharacterEncoding("utf-8");
	         //设置Content-Disposition  
	         response.setHeader("Content-Disposition", "attachment;filename="+ new String((batch.getBatch()+".xls").getBytes("GBK"), "ISO-8859-1")); 
	         
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
	         batch.setMade(true);
	         rechargeCodeService.update(batch);
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
    public static void writeToExcel(String fileDir,String sheetName,List<RechargeCode> list) throws Exception{  
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
                    cell0.setCellValue(list.get(rowId).getPoints()+"");  
                    cell1.setCellValue(list.get(rowId).getCode().toString());
                    cell2.setCellValue(list.get(rowId).getKeyt().toString()); 
                    cell3.setCellValue(list.get(rowId).isGiven()?"已领取":"未领取");  
                    cell4.setCellValue(list.get(rowId).isUsed()?"已使用":"未使用");  
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
