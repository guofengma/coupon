package com.coupon.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
