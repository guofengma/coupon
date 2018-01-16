package com.coupon.business.service;

import java.util.List;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageList;
import com.coupon.base.service.BaseService;
import com.coupon.business.entity.Record;

public interface RecordService extends BaseService<Record , String>{

	PageList<Record> findByCustomerId(int pageNo, int pageSize, String customerId);

	IPageList<Record> findUndealByAdmin(int pageNo, int pageSize);

	IPageList<Record> findUndealByManager(int pageNo, int pageSize, String cityIds);

	IPageList<Record> findUndealByStaff(int pageNo, int pageSize, String userId);

	List<Record> findByIds(String[] ids);

	List<Record> findAchievementByStaff(String userId, String startTime, String endTime);

	List<Record> findRecordCanExchangeService(String productId, String customerId);

}
