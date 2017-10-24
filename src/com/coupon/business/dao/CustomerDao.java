package com.coupon.business.dao;

import java.util.List;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageList;
import com.coupon.base.dao.BaseDao;
import com.coupon.business.entity.Customer;
import com.coupon.system.entity.User;

public interface CustomerDao extends BaseDao<Customer , String>{

	IPageList<Customer> findByAdmin(int pageNo, int pageSize, boolean check,String condition);

	IPageList<Customer> findByManager(int pageNo, int pageSize, boolean check, String cityIds,String condition);

	IPageList<Customer> findByStaff(int pageNo, int pageSize, boolean check, String userId,String condition);

	List<Customer> findByIds(String[] ids);

	PageList<Customer> findByCondition(int pageNo, int pageSize, User user ,String[] condition);

	List<Customer> exportByCondition(User user, String[] condition);

	Customer findByPhone(String phone);

	Customer findCheckedByPhone(String phone);

	List<Customer> getAllCustomers();


}
