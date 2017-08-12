package com.coupon.business.service;

import com.coupon.base.common.paging.PageList;
import com.coupon.base.service.BaseService;
import com.coupon.business.entity.Record;

public interface RecordService extends BaseService<Record , String>{

	PageList<Record> findByCustomerId(int pageNo, int pageSize, String customerId);

}
