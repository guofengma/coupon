package com.coupon.business.dao;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.dao.BaseDao;
import com.coupon.business.entity.Customer;

public interface CustomerDao extends BaseDao<Customer , String>{

	IPageList<Customer> findByAdmin(int pageNo, int pageSize, boolean check);

	IPageList<Customer> findByManager(int pageNo, int pageSize, boolean check, String cityId);

	IPageList<Customer> findByStaff(int pageNo, int pageSize, boolean check, String userId);


}
