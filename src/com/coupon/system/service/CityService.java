package com.coupon.system.service;

import java.util.List;
import java.util.Set;

import com.coupon.base.service.BaseService;
import com.coupon.system.entity.City;

public interface CityService extends BaseService<City, String>{

	List<City> getFCity();

	public List<City> getFCityUsed();

	Set<City> findByIds(String[] ids);

}
