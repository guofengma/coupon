package com.coupon.system.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.coupon.system.entity.base.CityEntity;

@Entity
@Table(name = "coupon_city")
public class City extends CityEntity implements Comparable<City>{

	@Override
	public int compareTo(City o) {
		City city = (City)o;
		return -(""+this.getParent()).compareTo(""+city.getParent())*2+compareToInt(this.getPriority(),city.getPriority());
	}

	private int compareToInt(int a , int b){
		if(a==b)
			return 0;
		if(a>b)
			return 1;
		if(a<b)
			return -1;
		return 0;
	}
}
