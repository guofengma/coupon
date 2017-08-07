package com.coupon.business.dao.impl;

import org.springframework.stereotype.Repository;

import com.coupon.base.dao.impl.BaseDaoImpl;
import com.coupon.business.dao.RedeemCodeDao;
import com.coupon.business.entity.RedeemCode;

@Repository
public class RedeemCodeDaoImpl extends BaseDaoImpl<RedeemCode, String> implements RedeemCodeDao{

	@Override
	protected Class<RedeemCode> getEntityClass() {
		return RedeemCode.class;
	}

}
