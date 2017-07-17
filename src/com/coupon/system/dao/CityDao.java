package com.coupon.system.dao;

import java.util.List;

import com.coupon.base.dao.BaseDao;
import com.coupon.system.entity.City;

public interface CityDao extends BaseDao<City, String>{

	List<City> getFCity();

}
