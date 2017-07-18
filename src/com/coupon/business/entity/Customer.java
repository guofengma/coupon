package com.coupon.business.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.coupon.business.entity.base.CustomerEntity;

@Entity
@Table(name = "ogpis_customer")
public class Customer extends CustomerEntity{

}
