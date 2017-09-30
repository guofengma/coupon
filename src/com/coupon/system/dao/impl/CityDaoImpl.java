package com.coupon.system.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		return (List<City>)this.queryByHql(
				"from City where deleted=false and parent is null and used=true order by priority asc", null
				);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<City> getCityUsed() {//获取已经有业务的城市
		return (List<City>)this.queryByHql(
				"from City where deleted=false and used=true order by priority asc", null
				);
	}

	@Override
	public Set<City> findByIds(String[] ids) {
		StringBuilder id = new StringBuilder();
		for(String temp : ids){
			id.append("'"+temp+"',");
		}
		id.deleteCharAt(id.length()-1);
		List<City> fCitys =  this.queryByHql(
				"from City where deleted=false and id in ("+id.toString()+")", null
				);
		 return new HashSet(fCitys);
	}
}
