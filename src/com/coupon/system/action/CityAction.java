package com.coupon.system.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.coupon.base.action.BaseAction;
import com.coupon.business.entity.Bank;
import com.coupon.system.entity.City;
import com.coupon.system.service.CityService;

@Controller
public class CityAction extends BaseAction{
	
	@Autowired CityService cityService ;
	
	@RequiresPermissions(value={"city:management"})
	@RequestMapping(value = "/system/city/list")
	public String list(HttpServletRequest request, ModelMap model) {
		super.addMenuParams(request, model);
		return "system/city/list";
	}
	
	@RequestMapping(value = "/system/city/getFCity")
	public void getFCity(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 StringBuilder result = new StringBuilder("[");
		 List<City> fCity = cityService.getFCity();
		 for(City temp : fCity){
			 result.append("{\"id\":\""+temp.getId()+"\",\"name\":\""+temp.getName()+"\",\"used\":"+temp.isUsed()+",\"priority\":\""+temp.getPriority()+"\"},");
		 }
		 if(fCity.size()!=0){
			 result.deleteCharAt(result.length()-1);
		 }
		 result.append("]");
		 response.setContentType("application/json");
	 	 response.setCharacterEncoding("utf-8");
	 	 response.getWriter().write(result.toString());
	}
	
	@RequestMapping(value = "/system/city/getSCity")
	public void getSCity(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fid = request.getParameter("id");
		City fCity = cityService.findById(fid);
		StringBuilder result = new StringBuilder("[");
		 List<City> sCity = fCity.getChildren();
		 for(City temp : sCity){
			 result.append("{\"id\":\""+temp.getId()+"\",\"name\":\""+temp.getName()+"\",\"used\":"+temp.isUsed()+",\"priority\":\""+temp.getPriority()+"\"},");
		 }
		 if(sCity.size()!=0){
			 result.deleteCharAt(result.length()-1);
		 }
		 result.append("]");
		 response.setContentType("application/json");
	 	 response.setCharacterEncoding("utf-8");
	 	 response.getWriter().write(result.toString());
	}
	
	@RequestMapping(value = "/system/city/getSCityUsedByFCityId")
	public void getSCityUsedByFCityId(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fid = request.getParameter("id");
		City fCity = cityService.findById(fid);
		StringBuilder result = new StringBuilder("{\"sCity\":[");
		List<City> sCity = fCity.getChildren();
		boolean hasSCityUsed = false ;
		boolean hasBank = false ;
		for(City temp : sCity){
			 if(temp.isUsed()){
				 hasSCityUsed = true ;
				 result.append("{\"id\":\""+temp.getId()+"\",\"name\":\""+temp.getName()+"\"},");
			 }
		 }
		 if(hasSCityUsed){
			 result.deleteCharAt(result.length()-1);
		 }
		 result.append("],\"bank\":[");
		 //请求一级城市下的兑换服务地址
		 List<Bank> banks = fCity.getBank();
		 for(Bank bank : banks){
			 if(!bank.getDeleted()){
				 hasBank = true ;
				 result.append("{\"id\":\""+bank.getId()+"\",\"name\":\""+bank.getName()+"\"},");
			 }
		 }
		 if(hasBank){
			 result.deleteCharAt(result.length()-1);
		 }
		 result.append("]}");
		 System.out.println(result.toString());
		 response.setContentType("application/json");
	 	 response.setCharacterEncoding("utf-8");
	 	 response.getWriter().write(result.toString());
	}
	
	@RequestMapping(value = "/system/city/getBankBySCityId")
	public void getBankBySCityId(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		City sCity = cityService.findById(id);
		StringBuilder result = new StringBuilder("[");
		boolean hasBank = false;
		 //请求二级城市下的兑换服务地址
		 List<Bank> banks = sCity.getBank();
		 for(Bank bank : banks){
			 if(!bank.getDeleted()){
				 hasBank = true ;
				 result.append("{\"id\":\""+bank.getId()+"\",\"name\":\""+bank.getName()+"\"},");
			 }
		 }
		 if(hasBank){
			 result.deleteCharAt(result.length()-1);
		 }
		 result.append("]");
		 System.out.println(result.toString());
		 response.setContentType("application/json");
	 	 response.setCharacterEncoding("utf-8");
	 	 response.getWriter().write(result.toString());
	}
	
	
	@RequestMapping(value = "/system/city/changeState")
	public void changeState(HttpServletRequest request, HttpServletResponse response) throws IOException {
		StringBuilder result = new StringBuilder();
		boolean isFCity = false ;
		String id = request.getParameter("id");
		City city = cityService.findById(id);
		if(city.getParent()==null)
			isFCity = true ;
		String state = request.getParameter("state");
		if(isFCity){//如果是一级城市
			if(state.equals("false")){//如果是关闭
				boolean normal = true ; //默认可以正常关闭一级城市
				List<City> sCitys = city.getChildren();
				for(City temp: sCitys){
					if(temp.isUsed()){
						normal = false ;
					}
				}
				if(normal){
					city.setUsed(false);
					cityService.update(city);
					result.append("{\"flag\":\"success\",\"msg\":\"关闭业务成功\"}");
				}else{
					result.append("{\"flag\":\"failed\",\"msg\":\"有二级城市未关闭，一级城市不可关闭！\"}");
				}
			}else{
				city.setUsed(true);
				cityService.update(city);
				result.append("{\"flag\":\"success\",\"msg\":\"拓展业务成功\"}");
			}
		}
		if(!isFCity){//如果是二级城市
			if(state.equals("true")){//如果是开启
				City fCity = city.getParent();
				if(fCity.isUsed()){//如果它的父城市是开启的，那么开启二级城市没问题
					city.setUsed(true);
					cityService.update(city);
					result.append("{\"flag\":\"success\",\"msg\":\"拓展业务成功\"}");
				}else{
					result.append("{\"flag\":\"failed\",\"msg\":\"一级城市还未开启，二级城市不可开启\"}");
				}
			}else{
				city.setUsed(false);
				cityService.update(city);
				result.append("{\"flag\":\"success\",\"msg\":\"关闭业务成功\"}");
			}
			
		}
			
		
		response.setContentType("application/json");
	 	response.setCharacterEncoding("utf-8");
	 	response.getWriter().write(result.toString());
	}
	
	
	
	@RequestMapping(value = "/system/city/getUsedCity")
	public void getFUsedCity(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 StringBuilder result = new StringBuilder("[");
		 List<City> fCityUsedList = cityService.getFCityUsed();
		 for(City temp : fCityUsedList){
			 boolean hasSCity = false ;
			 result.append("{\"id\":\""+temp.getId()+"\",\"name\":\""+temp.getName()+"\",\"children\":[");
			 List<City> sCityList = temp.getChildren();
			 for(City sTemp : sCityList){
				 if(sTemp.isUsed()){
					 hasSCity = true ;
					 result.append("{\"id\":\""+sTemp.getId()+"\",\"name\":\""+sTemp.getName()+"\"},");
				 }
			 }
			 if(hasSCity)
				 result.deleteCharAt(result.length()-1);
			 result.append("]},");
		 }
		 if(fCityUsedList.size()!=0){
			 result.deleteCharAt(result.length()-1);
		 }
		 result.append("]");
		 System.out.println(result.toString());
		 response.setContentType("application/json");
	 	 response.setCharacterEncoding("utf-8");
	 	 response.getWriter().write(result.toString());
	}

}
