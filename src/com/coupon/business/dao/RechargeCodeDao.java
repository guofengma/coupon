package com.coupon.business.dao;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.dao.BaseDao;
import com.coupon.business.entity.RechargeCode;

public interface RechargeCodeDao extends BaseDao<RechargeCode , String>{

	IPageList<RechargeCode> findBatch(int pageNo, int pageSize);

}
