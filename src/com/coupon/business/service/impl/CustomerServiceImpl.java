package com.coupon.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.service.impl.BaseServiceImpl;
import com.coupon.business.dao.CustomerDao;
import com.coupon.business.entity.Customer;
import com.coupon.business.service.CustomerService;

@Service
public class CustomerServiceImpl extends BaseServiceImpl<Customer, String> implements CustomerService{

	@Autowired
	protected void setCustomerDao(CustomerDao CustomerDao) {
		setBaseDao(CustomerDao);
	}
	
	protected CustomerDao getCustomerDao() {
		return (CustomerDao)this.baseDao;
	}

	@Override
	public IPageList<Customer> findByAdmin(int pageNo, int pageSize, boolean check) {
		return (IPageList<Customer>) getCustomerDao().findByAdmin(pageNo, pageSize, check);
	}

	@Override
	public IPageList<Customer> findByManager(int pageNo, int pageSize, boolean check, String cityId) {
		return getCustomerDao().findByManager(pageNo, pageSize, check,cityId);
	}

	@Override
	public IPageList<Customer> findByStaff(int pageNo, int pageSize, boolean check, String userId) {
		return getCustomerDao().findByStaff(pageNo, pageSize, check,userId);
	}

	@Override
	public List<Customer> findByIds(String[] ids) {
		return getCustomerDao().findByIds(ids);
	}
}
