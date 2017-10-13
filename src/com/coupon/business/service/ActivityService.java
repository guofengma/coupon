package com.coupon.business.service;

import java.util.List;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.service.BaseService;
import com.coupon.business.entity.Activity;

public interface ActivityService extends BaseService<Activity , String>{

	IPageList<Activity> findAll(int pageNo, int pageSize);

	List<Activity> findAll();

}
