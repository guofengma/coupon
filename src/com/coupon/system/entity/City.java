package com.coupon.system.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.coupon.system.entity.base.CityEntity;

@Entity
@Table(name = "coupon_city")
public class City extends CityEntity{

}
