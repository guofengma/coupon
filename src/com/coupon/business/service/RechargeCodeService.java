package com.coupon.business.service;

import java.util.List;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageList;
import com.coupon.base.service.BaseService;
import com.coupon.business.entity.RechargeCode;

public interface RechargeCodeService extends BaseService<RechargeCode , String>{

	IPageList<RechargeCode> findBatch(int pageNo, int pageSize);

	PageList<RechargeCode> findByCondition(int pageNo, int pageSize, String[] condition);

	List<RechargeCode> findCanBeGivenBatch();

	RechargeCode findByKeyt(String keyt);

	List<RechargeCode> findByIds(String ids);

	IPageList<RechargeCode> findMyApplyRecharegeCode(int pageNo, int pageSize,String userId);

	List<RechargeCode> findFrontBatch();

	IPageList<RechargeCode> findUndealRechargeCodeByAdmin(int pageNo, int pageSize);

	IPageList<RechargeCode> findUndealRechargeByManager(int pageNo, int pageSize, String cityIds);

}
