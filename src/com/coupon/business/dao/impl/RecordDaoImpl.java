package com.coupon.business.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.coupon.base.common.paging.PageList;
import com.coupon.base.common.paging.PageListUtil;
import com.coupon.base.dao.impl.BaseDaoImpl;
import com.coupon.business.dao.RecordDao;
import com.coupon.business.entity.Record;

@Repository
public class RecordDaoImpl extends BaseDaoImpl<Record, String> implements RecordDao{

	@Override
	protected Class<Record> getEntityClass() {
		return Record.class;
	}

	@Override
	public PageList<Record> findByCustomerId(int pageNo, int pageSize, String customerId) {
		int first = (pageNo - 1) * pageSize;
		List<Record> items = this.queryByHql(
				"from Record r where r.deleted=false and r.customer.id = '"+customerId +"' order by createTime desc", null,
				first, pageSize);
		int count = Integer.parseInt(this.findUnique(
				"select count(*) from Record r where r.deleted=false and r.customer.id = '"+customerId +"'", null)
				.toString());
		System.out.println(count);
		return PageListUtil.getPageList(count, pageNo, items, pageSize);
	}

}
