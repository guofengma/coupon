package com.coupon.business.dao.impl;

import org.springframework.stereotype.Repository;

import com.coupon.base.dao.impl.BaseDaoImpl;
import com.coupon.business.dao.CustomerDao;
import com.coupon.business.entity.Customer;

@Repository
public class CustomerDaoImpl extends BaseDaoImpl<Customer, String> implements CustomerDao{

	@Override
	protected Class<Customer> getEntityClass() {
		return Customer.class;
	}

}
