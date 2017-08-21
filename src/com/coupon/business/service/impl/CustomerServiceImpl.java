package com.coupon.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageList;
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
	public IPageList<Customer> findByAdmin(int pageNo, int pageSize, boolean check,String condition) {
		return (IPageList<Customer>) getCustomerDao().findByAdmin(pageNo, pageSize, check,condition);
	}

	@Override
	public IPageList<Customer> findByManager(int pageNo, int pageSize, boolean check, String cityIds,String condition) {
		return getCustomerDao().findByManager(pageNo, pageSize, check,cityIds, condition);
	}

	@Override
	public IPageList<Customer> findByStaff(int pageNo, int pageSize, boolean check, String userId,String condition) {
		return getCustomerDao().findByStaff(pageNo, pageSize, check,userId, condition);
	}

	@Override
	public List<Customer> findByIds(String[] ids) {
		return getCustomerDao().findByIds(ids);
	}

	@Override
	public PageList<Customer> findByCondition(int pageNo, int pageSize, String[] condition) {
		return getCustomerDao().findByCondition(pageNo,pageSize,condition);
	}
}
