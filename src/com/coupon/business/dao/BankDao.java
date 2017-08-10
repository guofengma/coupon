package com.coupon.business.dao;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.dao.BaseDao;
import com.coupon.business.entity.Bank;

public interface BankDao extends BaseDao<Bank , String>{

	IPageList<Bank> findAll(int pageNo, int pageSize);

}
