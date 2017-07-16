package com.coupon.system.service.impl;

import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import com.coupon.TestUtil;
import com.coupon.system.entity.Dictionary;
import com.coupon.system.service.DictionaryService;

public class DictionaryServiceImplTest {

	private static DictionaryService dictionaryService;

	@BeforeClass
	public static void init() {
		dictionaryService = TestUtil.getApplicationContext().getBean(
				DictionaryService.class);
	}

	@Test
	public void testAdd() {
		Dictionary dic = new Dictionary();
		dic.setName("dic_name");
		dic.setValue("dic_value");
		dic.setPriority(0);
		dic.setType("dic_type");
		dictionaryService.save(dic);
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindById() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

}
