package com.coupon.business.service;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.service.BaseService;
import com.coupon.business.entity.Product;

public interface ProductService extends BaseService<Product , String>{

	IPageList<Product> findAll(int pageNo, int pageSize);

}
