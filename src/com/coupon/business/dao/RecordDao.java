package com.coupon.business.dao;

import com.coupon.base.common.paging.PageList;
import com.coupon.base.dao.BaseDao;
import com.coupon.business.entity.Record;

public interface RecordDao extends BaseDao<Record , String>{

	PageList<Record> findByCustomerId(int pageNo, int pageSize, String customerId);

}
