package com.coupon.system.service.impl;


import org.junit.BeforeClass;
import org.junit.Test;

import com.coupon.TestUtil;
import com.coupon.system.entity.MenuItem;
import com.coupon.system.service.MenuItemService;

public class MenuItemServiceImplTest {

	private static MenuItemService menuItemService;

	@BeforeClass
	public static void init() {
		menuItemService = TestUtil.getApplicationContext().getBean(
				MenuItemService.class);
	}

	@Test
	public void testAddFather() {
		MenuItem menuItemFather = new MenuItem();
		menuItemFather.setName("menuItemFather");
		menuItemService.save(menuItemFather);
		for (int i = 0; i < 100; i++) {
			MenuItem menuItemChild = new MenuItem();
			menuItemChild.setName("menuItemChild" + i);
			menuItemChild.setFather(menuItemFather);
			menuItemService.save(menuItemChild);
		}
	}
	

	
	

	@Test
	public void testFather() {
		String fatherId = "a0fe9155-2651-4889-9a27-5255f95f6931";
		MenuItem menuItemFather = menuItemService.findById(fatherId);
		System.out.println("menuItemFather.getChildren().size(): "
				+ menuItemFather.getChildren().size());
		
		// System.out.println(" menuItemFather.getName(): "
		// + menuItemFather.getName());
	}

	@Test
	public void testChild() {
		String childId = "c7d0a92e-e7aa-4a32-9b5e-98781dd999fd";
		MenuItem menuItem = menuItemService.findById(childId);
		System.out.println("menuItem.getFather().getName(): "
				+ menuItem.getFather().getName());

		// System.out.println(" menuItemFather.getName(): "
		// + menuItemFather.getName());
	}
	@Test
	public void testDelete() {
		String childId = "ecb1518b-dacc-46b4-be20-22bdce664c70";
		menuItemService.delete(childId);
	}

}
