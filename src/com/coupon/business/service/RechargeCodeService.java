package com.coupon.business.service;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.service.BaseService;
import com.coupon.business.entity.RechargeCode;

public interface RechargeCodeService extends BaseService<RechargeCode , String>{

	IPageList<RechargeCode> findBatch(int pageNo, int pageSize);

}
