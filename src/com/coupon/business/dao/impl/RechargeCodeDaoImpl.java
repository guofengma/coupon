package com.coupon.business.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageListUtil;
import com.coupon.base.dao.impl.BaseDaoImpl;
import com.coupon.business.dao.RechargeCodeDao;
import com.coupon.business.entity.RechargeCode;
@Repository
public class RechargeCodeDaoImpl extends BaseDaoImpl<RechargeCode, String> implements RechargeCodeDao{

	@Override
	protected Class<RechargeCode> getEntityClass() {
		return RechargeCode.class;
	}

	@Override
	public IPageList<RechargeCode> findBatch(int pageNo, int pageSize) {
		int first = (pageNo - 1) * pageSize;
		List<RechargeCode> items = this.queryByHql(
				"from RechargeCode where deleted=false and parent is null order by createTime desc ", null,
				first, pageSize);
		int count = Integer.parseInt(this.findUnique(
				"select count(*) from RechargeCode where deleted=false and parent is null", null)
				.toString());
		System.out.println(count);
		return PageListUtil.getPageList(count, pageNo, items, pageSize);
	}

}
