package com.coupon.business.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageListUtil;
import com.coupon.base.dao.impl.BaseDaoImpl;
import com.coupon.business.dao.ProductDao;
import com.coupon.business.entity.Customer;
import com.coupon.business.entity.Product;

@Repository
public class ProductDaoImpl extends BaseDaoImpl<Product, String> implements ProductDao{

	@Override
	protected Class<Product> getEntityClass() {
		return Product.class;
	}

	@Override
	public IPageList<Product> findAll(int pageNo, int pageSize) {
		int first = (pageNo - 1) * pageSize;
		List<Product> items = this.queryByHql(
				"from Product where deleted=false order by statu desc , createTime desc ", null,
				first, pageSize);
		int count = Integer.parseInt(this.findUnique(
				"select count(*) from Product where deleted=false" , null)
				.toString());
		System.out.println(count);
		return PageListUtil.getPageList(count, pageNo, items, pageSize);
	}

}
