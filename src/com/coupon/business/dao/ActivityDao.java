package com.coupon.business.dao;

import java.util.List;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.dao.BaseDao;
import com.coupon.business.entity.Activity;

public interface ActivityDao extends BaseDao<Activity , String>{

	IPageList<Activity> findAll(int pageNo, int pageSize);

	List<Activity> findAll();

}
