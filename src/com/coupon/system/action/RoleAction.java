package com.coupon.system.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.coupon.system.entity.Role;
import com.coupon.system.entity.User;
import com.coupon.system.service.RoleService;

@Controller
public class RoleAction   extends BaseAction{

	@Autowired
	private RoleService roleService;

	@RequiresPermissions(value={"role:management"})
	@RequestMapping(value = "/system/role/list")
	public String list(HttpServletRequest request, ModelMap model) {
		super.addMenuParams(request, model);
		List<Role> roles = roleService.getList();
		model.addAttribute("roles", roles);
		return "system/role/list";
	}

	@RequiresPermissions(value={"role:management"})
	@RequestMapping(value = "/system/role/add")
	public String add() {
		return "system/role/edit";
	}

	@RequiresPermissions(value={"role:management"})
	@RequestMapping(value = "/system/role/edit")
	public String edit(HttpServletRequest request, ModelMap model, String id) {
		Role role = this.roleService.findById(id);
		model.addAttribute("role", role);
		return "system/role/edit";
	}

	@RequiresPermissions(value={"role:management"})
	@RequestMapping(value = "/system/role/save")
	public String save(Role role, String[] perms, String id, boolean isAdd,
			HttpServletRequest request, ModelMap model) {
		Role bean = null;
		if (isAdd) {
			bean = new Role();
			// bean.setPassword(user.getPassword());
		} else {
			bean = this.roleService.findById(id);
		}
		bean.setName(role.getName());
		bean.setPriority(role.getPriority());
		bean.setIsSuper(role.getIsSuper());
		if (isAdd) {
			roleService.save(bean,Role.splitPerms(perms));
		} else {
			roleService.update(bean,Role.splitPerms(perms));
		}
		return "redirect:list";
	}

	@RequiresPermissions(value={"role:management"})
	@RequestMapping(value = "/system/role/delete")
	public String delete(HttpServletRequest request, ModelMap model, String id) {
		Role role = roleService.findById(id);
		role.setDeleted(true);
		role.setUsers(null);
		roleService.update(role);
		return list(request, model);
	}

}
