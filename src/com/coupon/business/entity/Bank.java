package com.coupon.business.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.coupon.business.entity.base.BankEntity;

@Entity
@Table(name = "coupon_bank")
public class Bank extends BankEntity{

}
