package com.coupon.business.service;

import java.util.List;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.service.BaseService;
import com.coupon.business.entity.Customer;

public interface CustomerService extends BaseService<Customer , String>{

	IPageList<Customer> findByAdmin(int pageNo, int pageSize, boolean check);

	IPageList<Customer> findByManager(int pageNo, int pageSize, boolean check, String cityId);

	IPageList<Customer> findByStaff(int pageNo, int pageSize, boolean check, String userId);

	List<Customer> findByIds(String[] ids);

}
