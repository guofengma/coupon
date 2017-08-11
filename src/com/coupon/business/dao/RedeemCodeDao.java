package com.coupon.business.dao;

import java.util.List;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.dao.BaseDao;
import com.coupon.business.entity.RedeemCode;

public interface RedeemCodeDao extends BaseDao<RedeemCode , String>{

	IPageList<RedeemCode> findBatch(int pageNo, int pageSize, String productId);

	List<RedeemCode> findByCondition(String[] condition);


}
