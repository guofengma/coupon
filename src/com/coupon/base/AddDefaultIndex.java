package com.coupon.base;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.coupon.index.entity.IndexManagement;
import com.coupon.index.service.IndexManagementService;
import com.coupon.plan.entity.Plan;
import com.coupon.plan.service.PlanService;

public class AddDefaultIndex {
	@Resource
	private PlanService planService;
	
	@Resource
	private IndexManagementService indexManagementService;
	
	private String[] defaultIndexs =  {"新增石油探明地质储量","新增天然气探明地质储量","新增煤层气探明地质储量","新增页岩气探明地质储量","石油产量","天然气产量","煤层气产量","页岩气产量"};
	
	public void addDefaultIndex(String id)
	{
		System.out.println(id);
		Plan bean = planService.findById(id);
		IndexManagement index ;
		for(int i=0;i<defaultIndexs.length;i++)
		{
			index = new IndexManagement();
			index.setIndexName(defaultIndexs[i]);
			index.setDeleted(false);
			index.setPlan(bean);
			indexManagementService.save(index);
		}
	}

}
