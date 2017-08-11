package com.coupon.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageList;
import com.coupon.base.service.impl.BaseServiceImpl;
import com.coupon.business.dao.RedeemCodeDao;
import com.coupon.business.entity.RedeemCode;
import com.coupon.business.service.RedeemCodeService;

@Service
public class RedeemCodeServiceImpl extends BaseServiceImpl<RedeemCode, String> implements RedeemCodeService{

	@Autowired
	protected void setRedeemCodeDao(RedeemCodeDao redeemCodeDao) {
		setBaseDao(redeemCodeDao);
	}
	
	protected RedeemCodeDao getRedeemCodeDao() {
		return (RedeemCodeDao)this.baseDao;
	}

	@Override
	public IPageList<RedeemCode> findBatch(int pageNo, int pageSize,String productId) {
		return getRedeemCodeDao().findBatch(pageNo, pageSize,productId);
	}

	@Override
	public PageList<RedeemCode> findByCondition(int pageNo, int pageSize, String[] condition) {
		return getRedeemCodeDao().findByCondition(pageNo,pageSize,condition);
	}
}
