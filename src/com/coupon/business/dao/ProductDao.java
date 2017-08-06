package com.coupon.business.dao;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.dao.BaseDao;
import com.coupon.business.entity.Product;

public interface ProductDao extends BaseDao<Product , String>{

	IPageList<Product> findAll(int pageNo, int pageSize);

}
