package com.coupon.business.dao;

import java.util.List;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageList;
import com.coupon.base.dao.BaseDao;
import com.coupon.business.entity.Record;

public interface RecordDao extends BaseDao<Record , String>{

	PageList<Record> findByCustomerId(int pageNo, int pageSize, String customerId);

	IPageList<Record> findUndealByAdmin(int pageNo, int pageSize);

	IPageList<Record> findUndealByManager(int pageNo, int pageSize, String cityIds);

	IPageList<Record> findUndealByStaff(int pageNo, int pageSize, String userId);

	List<Record> findByIds(String[] ids);

	List<Record> findAchievementByStaff(String userId, String startTime, String endTime);

}
