package com.coupon.business.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.coupon.business.entity.base.ProductEntity;

@Entity
@Table(name = "ogpis_product")
public class Product extends ProductEntity{

}