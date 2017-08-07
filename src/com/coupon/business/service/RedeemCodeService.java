package com.coupon.business.service;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.service.BaseService;
import com.coupon.business.entity.RedeemCode;

public interface RedeemCodeService  extends BaseService<RedeemCode , String>{

	IPageList<RedeemCode> findBatch(int pageNo, int pageSize,String productId);

}
