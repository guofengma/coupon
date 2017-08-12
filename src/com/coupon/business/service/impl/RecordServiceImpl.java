package com.coupon.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageList;
import com.coupon.base.service.impl.BaseServiceImpl;
import com.coupon.business.dao.RecordDao;
import com.coupon.business.entity.Customer;
import com.coupon.business.entity.Record;
import com.coupon.business.service.RecordService;

@Service
public class RecordServiceImpl extends BaseServiceImpl<Record, String> implements RecordService{

	@Autowired
	protected void setRecordDao(RecordDao recordDao) {
		setBaseDao(recordDao);
	}
	
	protected RecordDao getRecordDao() {
		return (RecordDao)this.baseDao;
	}

	@Override
	public PageList<Record> findByCustomerId(int pageNo, int pageSize, String customerId) {
		return getRecordDao().findByCustomerId(pageNo, pageSize, customerId);
	}

	@Override
	public IPageList<Record> findUndealByAdmin(int pageNo, int pageSize) {
		return getRecordDao().findUndealByAdmin(pageNo, pageSize);
	}

	@Override
	public IPageList<Record> findUndealByManager(int pageNo, int pageSize, String cityId) {
		return getRecordDao().findUndealByManager(pageNo, pageSize,cityId);
	}

	@Override
	public IPageList<Record> findUndealByStaff(int pageNo, int pageSize, String userId) {
		return getRecordDao().findUndealByStaff(pageNo, pageSize,userId);
	}
}
