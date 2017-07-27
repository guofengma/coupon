package com.coupon.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.coupon.base.dao.impl.BaseDaoImpl;
import com.coupon.system.dao.CityDao;
import com.coupon.system.entity.City;
import com.coupon.system.entity.User;

@Repository
public class CityDaoImpl extends BaseDaoImpl<City, String> implements CityDao{

	@Override
	protected Class<City> getEntityClass() {
		return City.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<City> getFCity() {//获取所有的一级行政区
		 List<City> fCitys = this.queryByHql(
					"from City where deleted=false and parent is null order by priority asc", null
					);
			 return fCitys;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<City> getFCityUsed() {//获取已经有业务的一级行政区
		List<City> fCitys = this.queryByHql(
				"from City where deleted=false and parent is null and used=true order by priority asc", null
				);
		 return fCitys;
	}

}
