package com.coupon.business.dao;

import java.util.List;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageList;
import com.coupon.base.dao.BaseDao;
import com.coupon.business.entity.RechargeCode;

public interface RechargeCodeDao extends BaseDao<RechargeCode , String>{

	IPageList<RechargeCode> findBatch(int pageNo, int pageSize);

	PageList<RechargeCode> findByCondition(int pageNo, int pageSize, String[] condition);

	List<RechargeCode> findCanBeGivenBatch();

	RechargeCode findByKeyt(String keyt);

	List<RechargeCode> findByIds(String string);

	IPageList<RechargeCode> findMyApplyRecharegeCode(int pageNo, int pageSize,String userId);

	List<RechargeCode> findFrontBatch();

	IPageList<RechargeCode> findUndealRechargeCodeByAdmin(int pageNo, int pageSize);

	IPageList<RechargeCode> findUndealRechargeByManager(int pageNo, int pageSize, String cityIds);

}
