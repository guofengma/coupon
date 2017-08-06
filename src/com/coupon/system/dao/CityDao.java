package com.coupon.system.dao;

import java.util.List;
import java.util.Set;

import com.coupon.base.dao.BaseDao;
import com.coupon.system.entity.City;

public interface CityDao extends BaseDao<City, String>{

	List<City> getFCity();

	List<City> getFCityUsed();

	Set<City> findByIds(String[] ids);

}
