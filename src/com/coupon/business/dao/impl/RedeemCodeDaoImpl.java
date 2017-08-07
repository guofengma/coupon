package com.coupon.business.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageListUtil;
import com.coupon.base.dao.impl.BaseDaoImpl;
import com.coupon.business.dao.RedeemCodeDao;
import com.coupon.business.entity.RedeemCode;

@Repository
public class RedeemCodeDaoImpl extends BaseDaoImpl<RedeemCode, String> implements RedeemCodeDao{

	@Override
	protected Class<RedeemCode> getEntityClass() {
		return RedeemCode.class;
	}

	@Override
	public IPageList<RedeemCode> findBatch(int pageNo, int pageSize,String productId) {
		int first = (pageNo - 1) * pageSize;
		List<RedeemCode> items = this.queryByHql(
				"from RedeemCode where deleted=false and parent is null and product.id='"+productId+"' order by createTime desc ", null,
				first, pageSize);
		int count = Integer.parseInt(this.findUnique(
				"select count(*) from RedeemCode where deleted=false and parent is null and product.id='"+productId+"'", null)
				.toString());
		System.out.println(count);
		return PageListUtil.getPageList(count, pageNo, items, pageSize);
	}

}
