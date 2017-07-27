package com.coupon.business.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageListUtil;
import com.coupon.base.dao.impl.BaseDaoImpl;
import com.coupon.business.dao.CustomerDao;
import com.coupon.business.entity.Customer;
import com.coupon.system.entity.User;

@Repository
public class CustomerDaoImpl extends BaseDaoImpl<Customer, String> implements CustomerDao{

	@Override
	protected Class<Customer> getEntityClass() {
		return Customer.class;
	}

	@Override
	public IPageList<Customer> findByAdmin(int pageNo, int pageSize, boolean check) {
		int first = (pageNo - 1) * pageSize;
		List<Customer> items = this.queryByHql(
				"from Customer where deleted=false and statu="+check+" order by createTime desc", null,
				first, pageSize);
		int count = Integer.parseInt(this.findUnique(
				"select count(*) from Customer where deleted=false and statu="+check, null)
				.toString());
		System.out.println(count);
		return PageListUtil.getPageList(count, pageNo, items, pageSize);
	}

	@Override
	public IPageList<Customer> findByManager(int pageNo, int pageSize, boolean check, String cityId) {
		int first = (pageNo - 1) * pageSize;
		List<Customer> items = this.queryByHql(
				"from Customer where deleted=false and statu="+check+" and city.id="+cityId+" order by createTime desc", null,
				first, pageSize);
		int count = Integer.parseInt(this.findUnique(
				"select count(*) from Customer where deleted=false and city.id="+cityId+" and statu="+check, null)
				.toString());
		return PageListUtil.getPageList(count, pageNo, items, pageSize);
	}

	@Override
	public IPageList<Customer> findByStaff(int pageNo, int pageSize, boolean check, String userId) {
		int first = (pageNo - 1) * pageSize;
		List<Customer> items = this.queryByHql(
				"from Customer where deleted=false and statu="+check+" and user.id="+userId+" order by createTime desc", null,
				first, pageSize);
		int count = Integer.parseInt(this.findUnique(
				"select count(*) from Customer where deleted=false and user.id="+userId+" and statu="+check, null)
				.toString());
		return PageListUtil.getPageList(count, pageNo, items, pageSize);
	}

}
