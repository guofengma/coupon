package com.coupon.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.service.impl.BaseServiceImpl;
import com.coupon.business.dao.BankDao;
import com.coupon.business.entity.Bank;
import com.coupon.business.service.BankService;


@Service
public class BankServiceImpl extends BaseServiceImpl<Bank, String> implements BankService{

	@Autowired
	protected void setBankDao(BankDao bankDao) {
		setBaseDao(bankDao);
	}
	
	protected BankDao getBankDao() {
		return (BankDao)this.baseDao;
	}

	@Override
	public IPageList<Bank> findAll(int pageNo, int pageSize) {
		return getBankDao().findAll(pageNo,pageSize);
	}
}
