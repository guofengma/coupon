package com.coupon.business.service;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.service.BaseService;
import com.coupon.business.entity.Bank;

public interface BankService extends BaseService<Bank , String>{

	IPageList<Bank> findAll(int pageNo, int pageSize);

}
