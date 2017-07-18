package com.coupon.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coupon.base.service.impl.BaseServiceImpl;
import com.coupon.business.dao.CustomerDao;
import com.coupon.business.entity.Customer;
import com.coupon.business.service.CustomerService;

@Service
public class CustomerServiceImpl extends BaseServiceImpl<Customer, String> implements CustomerService{

	@Autowired
	protected CustomerDao getCustomerDao() {
		return (CustomerDao)this.baseDao;
	}

	protected void setCustomerDao(CustomerDao CustomerDao) {
		setBaseDao(CustomerDao);
	}
}
