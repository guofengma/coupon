package com.coupon.business.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.utils.StringUtil;
import com.coupon.base.service.impl.BaseServiceImpl;
import com.coupon.business.dao.ProductDao;
import com.coupon.business.entity.Product;
import com.coupon.business.service.ProductService;
import com.coupon.system.entity.City;

@Service
public class ProductServiceImpl  extends BaseServiceImpl<Product, String> implements ProductService{

	@Autowired
	protected void setProductDao(ProductDao productDao) {
		setBaseDao(productDao);
	}
	
	protected ProductDao getProductDao() {
		return (ProductDao)this.baseDao;
	}

	@Override
	public IPageList<Product> findAll(int pageNo, int pageSize) {
		return getProductDao().findAll(pageNo,pageSize);
	}

	@Override
	public List<Product> findProductByCityIds(String[] cityToStringIds) {
		List<Product> products = getProductDao().findAllOnline();
		List<Product> result = new ArrayList<Product>();
		boolean flag = false ;
		for(Product temp : products){
			Set<City> citys = temp.getCity();
			for(City tempCity:citys){
				if(StringUtil.toIdString(cityToStringIds).contains(tempCity.getId())){
					flag = true ;
					break;
				}
			}
			if(flag)
				result.add(temp);
			flag = false;
		}
		return result;
	}
}
