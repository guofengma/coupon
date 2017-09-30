package com.coupon.security;

import java.util.Set;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.coupon.base.common.utils.UserUtil;
import com.coupon.business.entity.Customer;
import com.coupon.business.service.CustomerService;
import com.coupon.system.entity.User;
import com.coupon.system.service.UserService;

public class MyRealm extends AuthorizingRealm {

	@Autowired
	protected UserService userService;
	@Autowired
	protected CustomerService customerService;

	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
			UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
			String username = token.getUsername();
			System.out.println("-------------------认证------------------"+username);
			if (username != null && !"".equals(username)) {
				User user = userService.findByUserName(username);
				if (user != null) {
					return new SimpleAuthenticationInfo(user.getName(),
							user.getPassword(), "true");
				}
				Customer customer = customerService.findByPhone(username);
				if(customer!= null){
					return new SimpleAuthenticationInfo(customer.getPhone(),
							customer.getPassword(), "false");
				}
			}
		return null;

	}

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		// 给用户授权
		String username = (String) principals.getPrimaryPrincipal();
		if("false".equals(principals.getRealmNames().toString()))//是客户则没有权限
			return null ;
		System.out.println("-------------------授权------------------"+username+principals.getRealmNames().toString());
		User user = new User();
		for(User temp : UserUtil.userList){
			if(username.equals(temp.getName())){
				user = temp ;
				break ;
			}
		}
		SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
		if (user != null) {
			Set<String> perms = user.getPerms();
			if (!CollectionUtils.isEmpty(perms)) {
				// 权限加入AuthorizationInfo认证对象
				auth.setStringPermissions(perms);
			}
		}
		return auth;
		}
}
