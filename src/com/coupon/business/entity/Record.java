package com.coupon.business.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.coupon.business.entity.base.RecordEntity;

@Entity
@Table(name = "coupon_record")
public class Record extends RecordEntity{

}
