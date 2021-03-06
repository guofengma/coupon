package com.coupon.system.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.coupon.base.action.BaseAction;
import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageListUtil;
import com.coupon.base.common.utils.CookieUtil;
import com.coupon.security.MyRealm;
import com.coupon.system.entity.City;
import com.coupon.system.entity.Role;
import com.coupon.system.entity.User;
import com.coupon.system.service.CityService;
import com.coupon.system.service.RoleService;
import com.coupon.system.service.UserService;

@Controller
public class UserAction extends BaseAction {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private CityService cityService;

	@RequiresPermissions(value={"user:management"})
	@RequestMapping(value = "/system/user/list")
	public String list(HttpServletRequest request, ModelMap model) {
		super.addMenuParams(request, model);
		int pageNo = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_NO_NAME, PageListUtil.DEFAULT_PAGE_NO);
		int pageSize = ServletRequestUtils.getIntParameter(request,
				PageListUtil.PAGE_SIZE_NAME, PageListUtil.DEFAULT_PAGE_SIZE);
		IPageList<User> users = userService.getAllUsers(pageNo, pageSize);
		model.addAttribute("currentUser",userService.findByUserName(CookieUtil.getCookie(request, "name_EN")));
		model.addAttribute("users", users);
		return "system/user/list";
	}

	@RequiresPermissions(value={"user:management"})
	@RequestMapping(value = "/system/user/add")
	public String add(HttpServletRequest request,ModelMap model) {
		String same = request.getParameter("same");
		List<Role> roleList = roleService.getList();
		List<City> fCityUsedList = cityService.getFCityUsed();//获取一级城市
		/*if(fCityUsedList.size()>0){	
			model.addAttribute("sCityUsedList",fCityUsedList.get(0).getUsedChildren());
		}*/
		model.addAttribute("fCityUsedList",fCityUsedList);
		model.addAttribute("roleList",roleList);
		model.addAttribute("same", same);
		return "system/user/edit";
	}

	@RequiresPermissions(value={"user:management"})
	@RequestMapping(value = "/system/user/edit")
	public String edit(HttpServletRequest request, ModelMap model, String id) {
		String same = request.getParameter("same");
		User user = this.userService.findById(id);
		List<City> fCityUsedList = cityService.getFCityUsed();//获取一级城市
		Set<City> userCity = user.getCity();//获取员工对应的城市，按一级城市放前面，优先级排序
		City fCity = new City();
		for(City temp:userCity){
			if(temp.getParent()==null)
				fCity = temp ;
		}
		if(userCity.size()!=0)
			model.addAttribute("sCityUsedList",fCity.getUsedChildren());
		List<Role> roleList = roleService.getList();
		Set<Role> userRoleList = user.getRoles();
		model.addAttribute("fCity",fCity);
		model.addAttribute("user", user);
		model.addAttribute("roleList", roleList);
		model.addAttribute("userRoleList", userRoleList);
		model.addAttribute("fCityUsedList",fCityUsedList);
		model.addAttribute("userCity",userCity);
		System.out.println(same);
		model.addAttribute("same", same);
		return "system/user/edit";
	}

	@RequiresPermissions(value={"user:management"})
	@RequestMapping(value = "/system/user/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, ModelMap model, User user,
			String id, String[] roleIds, boolean isAdd,String fCity,String sCity) {
		Set<City> userCity = new HashSet<City>();
		if(fCity.equals("null")){
			userCity = null ;
		}else if(sCity==null){
			userCity = cityService.findByIds(fCity.split(","));
		}else{
			userCity = cityService.findByIds((fCity+","+sCity).split(","));
		}
		List<User> temp = userService.findUserByName(user.getName());
		User bean = new User();
		if (isAdd) {
			if(temp.size()>0)//该用户名存在
			{
				return "redirect:add?same=0";
			}
		} else {
			bean = this.userService.findById(id);
			if(!bean.getName().equals(user.getName())&&temp.size()>0)
			{
				return "redirect:edit?id="+id+"&&same=0";
			}
		}
		bean.setDisplayName(user.getDisplayName());
		bean.setName(user.getName());
		bean.setCity(userCity);
		if(isAdd)
			bean.setPassword(user.getPassword());
		// 更新角色
		bean.getRoles().clear();// 先清空角色
		if (roleIds != null) {
			for (String rid : roleIds) {
				System.out.println("rid: "+rid);
				bean.addToRoles(roleService.findById(rid));
			}
		}
		if (isAdd) {
			userService.save(bean);
		} else {
			userService.update(bean);
		}
		return "redirect:list";
	}

	@RequiresPermissions(value={"user:management"})
	@RequestMapping(value = "/system/user/delete")
	public String delete(HttpServletRequest request, ModelMap model, String id) {
		User user = userService.findById(id);
		user.setDeleted(true);
		userService.update(user);
		return list(request, model);
	}
	
	@RequestMapping(value = "/system/user/changePassword")
	public void changePassword(HttpServletRequest request, HttpServletResponse response , ModelMap model) throws IOException {
		 String displayName = request.getParameter("displayName");
		 String password = request.getParameter("password");
		 String currentUserName = CookieUtil.getCookie(request, "name_EN");
		 User user = userService.findByUserName(currentUserName);
		 user.setPassword(password);
		 user.setDisplayName(displayName);
		 userService.update(user);
	 	 String result = "{\"result\":\"success\"}";
	 	 response.setContentType("application/json");
	 	 response.setCharacterEncoding("utf-8");
	 	 response.getWriter().write(result);
	}

}
