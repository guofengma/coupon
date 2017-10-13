package com.coupon.business.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageListUtil;
import com.coupon.base.dao.impl.BaseDaoImpl;
import com.coupon.business.dao.ActivityDao;
import com.coupon.business.entity.Activity;

@Repository
public class ActivityDaoImpl extends BaseDaoImpl<Activity, String> implements ActivityDao{

	@Override
	protected Class<Activity> getEntityClass() {
		return Activity.class;
	}

	@Override
	public IPageList<Activity> findAll(int pageNo, int pageSize) {
		int first = (pageNo - 1) * pageSize;
		List<Activity> items = this.queryByHql(
				"from Activity where deleted=false order by createTime desc ", null,
				first, pageSize);
		int count = Integer.parseInt(this.findUnique(
				"select count(*) from Activity where deleted=false" , null)
				.toString());
		System.out.println(count);
		return PageListUtil.getPageList(count, pageNo, items, pageSize);
	}

	@Override
	public List<Activity> findAll() {
		return this.queryByHql(
				"from Activity where deleted=false order by createTime desc ", null);
	}

}
