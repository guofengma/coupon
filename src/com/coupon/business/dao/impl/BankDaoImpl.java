package com.coupon.business.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageListUtil;
import com.coupon.base.dao.impl.BaseDaoImpl;
import com.coupon.business.dao.BankDao;
import com.coupon.business.entity.Bank;

@Repository
public class BankDaoImpl extends BaseDaoImpl<Bank, String> implements BankDao{

	@Override
	protected Class<Bank> getEntityClass() {
		return Bank.class;
	}

	@Override
	public IPageList<Bank> findAll(int pageNo, int pageSize) {
		int first = (pageNo - 1) * pageSize;
		List<Bank> items = this.queryByHql(
				"from Bank where deleted=false", null,
				first, pageSize);
		int count = Integer.parseInt(this.findUnique(
				"select count(*) from Bank where deleted=false", null)
				.toString());
		System.out.println(count);
		return PageListUtil.getPageList(count, pageNo, items, pageSize);
	}

}
