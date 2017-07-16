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
import com.coupon.system.entity.User;
import com.coupon.system.service.UserService;

public class MyRealm extends AuthorizingRealm {

	public static String hardName ;
	@Autowired
	protected UserService userService;

	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		// TODO Auto-generated method stub
		System.out.println("--doGetAuthenticationInfo");
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String username = token.getUsername();
		hardName = username ;
		if (username != null && !"".equals(username)) {
			// 用户的验证逻辑
			User user = userService.findByUserName(username);
			if (user != null) {
				return new SimpleAuthenticationInfo(user.getDisplayName(),
						user.getPassword(), getName());
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
		System.out.println("--doGetAuthorizationInfo");
		User user =new User();
		for(User temp : UserUtil.userList){
			if(temp.getName().equals(MyRealm.hardName))
				user = temp ;
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
