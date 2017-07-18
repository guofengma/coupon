package com.coupon.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coupon.base.service.impl.BaseServiceImpl;
import com.coupon.business.dao.ProductDao;
import com.coupon.business.entity.Product;
import com.coupon.business.service.ProductService;

@Service
public class ProductServiceImpl  extends BaseServiceImpl<Product, String> implements ProductService{

	@Autowired
	protected ProductDao getProductDao() {
		return (ProductDao)this.baseDao;
	}

	protected void setProductDao(ProductDao productDao) {
		setBaseDao(productDao);
	}
}