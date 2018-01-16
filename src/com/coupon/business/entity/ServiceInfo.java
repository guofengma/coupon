package com.coupon.business.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.coupon.business.entity.base.ServiceInfoEntity;

@Entity
@Table(name = "coupon_serviceInfo")//预约服务表
public class ServiceInfo extends ServiceInfoEntity{

}
