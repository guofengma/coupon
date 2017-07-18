package com.coupon.business.dao.impl;

import org.springframework.stereotype.Repository;

import com.coupon.base.dao.impl.BaseDaoImpl;
import com.coupon.business.dao.ProductDao;
import com.coupon.business.entity.Product;

@Repository
public class ProductDaoImpl extends BaseDaoImpl<Product, String> implements ProductDao{

	@Override
	protected Class<Product> getEntityClass() {
		return Product.class;
	}

}
