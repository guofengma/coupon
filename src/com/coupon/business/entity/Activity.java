package com.coupon.business.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.coupon.business.entity.base.ActivityEntity;

@Entity
@Table(name = "coupon_activity")
public class Activity extends ActivityEntity{

}
