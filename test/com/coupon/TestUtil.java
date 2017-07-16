package com.coupon;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

@Deprecated
public class TestUtil {
	private static ApplicationContext context;

	public static ApplicationContext getApplicationContext() {
		if (context == null) {
			context = new FileSystemXmlApplicationContext(
					"classpath:config/application-context.xml");
			// 为了生成相关的数据表
			@SuppressWarnings("unused")
			SessionFactory sessionFactory = (SessionFactory) context
					.getBean("sessionFactory");
		}
		return context;
	}
}
