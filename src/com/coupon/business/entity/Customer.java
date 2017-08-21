package com.coupon.business.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.coupon.business.entity.base.CustomerEntity;
import com.coupon.system.entity.City;

@Entity
@Table(name = "coupon_customer")
public class Customer extends CustomerEntity{
	
	public List<City> getCustomerCityByPriority(){
		List<City> cityList = new ArrayList<City>();
		for(City temp : this.getCity()){
			cityList.add(temp);
		}
		Collections.sort(cityList);
		return cityList;
	}

}
