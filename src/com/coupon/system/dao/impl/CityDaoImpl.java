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
	public List<City> getFCity() {
		 List<City> fCitys = this.queryByHql(
					"from City where deleted=false and parent is null order by priority asc", null
					);
			 return fCitys;
	}

}
