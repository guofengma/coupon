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
import com.coupon.system.entity.City;
import com.coupon.system.service.CityService;

@Controller
public class CityAction extends BaseAction{
	
	@Autowired CityService cityService ;
	
	@RequiresPermissions(value={"city:management"})
	@RequestMapping(value = "/system/city/list")
	public String list(HttpServletRequest request, ModelMap model) {
		return "system/city/list";
	}
	
	@RequiresPermissions(value={"city:management"})
	@RequestMapping(value = "/system/city/getFCity")
	public void getFCity(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 StringBuilder result = new StringBuilder("[");
		 List<City> fCity = cityService.getFCity();
		 String used = "" ;
		 
		 for(City temp : fCity){
			 used = temp.isUsed()?"是":"否";
			 result.append("{\"id\":\""+temp.getId()+"\",\"name\":\""+temp.getName()+"\",\"used\":\""+used+"\",\"priority\":\""+temp.getPriority()+"\"},");
		 }
		 if(fCity.size()!=0){
			 result.deleteCharAt(result.length()-1);
		 }
		 result.append("]");
		 response.setContentType("application/json");
	 	 response.setCharacterEncoding("utf-8");
	 	 response.getWriter().write(result.toString());
	}
	
	@RequiresPermissions(value={"city:management"})
	@RequestMapping(value = "/system/city/getSCity")
	public void getSCity(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fid = request.getParameter("id");
		City fCity = cityService.findById(fid);
		StringBuilder result = new StringBuilder("[");
		 List<City> sCity = fCity.getChildren();
		 String used = "" ;
		 for(City temp : sCity){
			 used = temp.isUsed()?"是":"否";
			 result.append("{\"id\":\""+temp.getId()+"\",\"name\":\""+temp.getName()+"\",\"used\":\""+used+"\",\"priority\":\""+temp.getPriority()+"\"},");
		 }
		 if(sCity.size()!=0){
			 result.deleteCharAt(result.length()-1);
		 }
		 result.append("]");
		 response.setContentType("application/json");
	 	 response.setCharacterEncoding("utf-8");
	 	 response.getWriter().write(result.toString());
	}

}
