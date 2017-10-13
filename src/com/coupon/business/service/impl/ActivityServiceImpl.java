package com.coupon.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.service.impl.BaseServiceImpl;
import com.coupon.business.dao.ActivityDao;
import com.coupon.business.entity.Activity;
import com.coupon.business.service.ActivityService;

@Service
public class ActivityServiceImpl extends BaseServiceImpl<Activity, String> implements ActivityService{
	@Autowired
	protected void setActivityDao(ActivityDao activityDao) {
		setBaseDao(activityDao);
	}
	
	protected ActivityDao getActivityDao() {
		return (ActivityDao)this.baseDao;
	}

	@Override
	public IPageList<Activity> findAll(int pageNo, int pageSize) {
		return getActivityDao().findAll(pageNo,pageSize);
	}

	@Override
	public List<Activity> findAll() {
		return getActivityDao().findAll();
	}
}
