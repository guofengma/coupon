package com.coupon.system.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coupon.base.service.impl.BaseServiceImpl;
import com.coupon.system.dao.CityDao;
import com.coupon.system.entity.City;
import com.coupon.system.service.CityService;

@Service
public class CityServiceImpl extends BaseServiceImpl<City, String> implements CityService{

	@Autowired
	protected void setCityDao(CityDao cityDao) {
		setBaseDao(cityDao);
	}

	protected CityDao getCityDao() {
		return (CityDao) this.baseDao;
	}

	@Override
	public List<City> getFCity() {
		return getCityDao().getFCity();
	}

	@Override
	public List<City> getFCityUsed() {
		return (List<City>)getCityDao().getFCityUsed();
	}

	@Override
	public Set<City> findByIds(String[] ids) {
		return getCityDao().findByIds(ids);
	}

	@Override
	public List<City> getCityUsed() {
		return getCityDao().getCityUsed();
	}
}
