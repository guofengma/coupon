package com.coupon.business.dao;

import java.util.List;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageList;
import com.coupon.base.dao.BaseDao;
import com.coupon.business.entity.Customer;

public interface CustomerDao extends BaseDao<Customer , String>{

	IPageList<Customer> findByAdmin(int pageNo, int pageSize, boolean check);

	IPageList<Customer> findByManager(int pageNo, int pageSize, boolean check, String cityId);

	IPageList<Customer> findByStaff(int pageNo, int pageSize, boolean check, String userId);

	List<Customer> findByIds(String[] ids);

	PageList<Customer> findByCondition(int pageNo, int pageSize, String[] condition);


}
