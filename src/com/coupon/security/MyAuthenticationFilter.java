package com.coupon.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

public class MyAuthenticationFilter extends FormAuthenticationFilter {

	public boolean onPreHandle(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		//目前过滤器没有用
		System.out.println("onPreHandle");
		boolean isAllowed = isAccessAllowed(request, response, mappedValue);
		Subject subject = getSubject(request, response);
		System.out.println("isAllowed：" + isAllowed);
		System.out.println("subject：" + (subject == null));
		//return true;
				
		return super.onPreHandle(request, response, mappedValue);
	}

}
