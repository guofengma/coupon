package com.coupon.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageList;
import com.coupon.base.service.impl.BaseServiceImpl;
import com.coupon.business.dao.RechargeCodeDao;
import com.coupon.business.entity.RechargeCode;
import com.coupon.business.service.RechargeCodeService;

@Service
public class RechargeCodeServiceImpl extends BaseServiceImpl<RechargeCode, String> implements RechargeCodeService{

	@Autowired
	protected void setRechargeCodeDao(RechargeCodeDao rechargeCodeDao) {
		setBaseDao(rechargeCodeDao);
	}
	
	protected RechargeCodeDao getRechargeCodeDao() {
		return (RechargeCodeDao)this.baseDao;
	}

	@Override
	public IPageList<RechargeCode> findBatch(int pageNo, int pageSize) {
		return getRechargeCodeDao().findBatch(pageNo,pageSize);
	}

	@Override
	public PageList<RechargeCode> findByCondition(int pageNo, int pageSize, String[] condition) {
		return getRechargeCodeDao().findByCondition(pageNo,pageSize,condition);
	}
}
