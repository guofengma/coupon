package com.coupon.base.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

public class BaseAction {
	
	/**
	 * 添加菜单相关参数
	 * @param request
	 * @param model
	 */
	protected void addMenuParams(HttpServletRequest request, ModelMap model) {
		String menus = request.getParameter("menus");
		model.addAttribute("menus", menus);// 指定导航栏展开那个子菜单
	}
	
}
