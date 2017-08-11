package com.coupon.business.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageList;
import com.coupon.base.common.paging.PageListUtil;
import com.coupon.base.dao.impl.BaseDaoImpl;
import com.coupon.business.dao.RedeemCodeDao;
import com.coupon.business.entity.Customer;
import com.coupon.business.entity.RedeemCode;
import com.coupon.util.FolderUtil;

@Repository
public class RedeemCodeDaoImpl extends BaseDaoImpl<RedeemCode, String> implements RedeemCodeDao{

	@Override
	protected Class<RedeemCode> getEntityClass() {
		return RedeemCode.class;
	}

	@Override
	public IPageList<RedeemCode> findBatch(int pageNo, int pageSize,String productId) {
		int first = (pageNo - 1) * pageSize;
		List<RedeemCode> items = this.queryByHql(
				"from RedeemCode where deleted=false and parent is null and product.id='"+productId+"' order by createTime desc ", null,
				first, pageSize);
		int count = Integer.parseInt(this.findUnique(
				"select count(*) from RedeemCode where deleted=false and parent is null and product.id='"+productId+"'", null)
				.toString());
		System.out.println(count);
		return PageListUtil.getPageList(count, pageNo, items, pageSize);
	}

	/*
	 * String condition[] = new String[]{exStartTime,exEndTime,startTime,endTime,name,code,statu,city,phone}
	 */
	@Override
	public PageList<RedeemCode> findByCondition(int pageNo , int pageSize ,String[] condition) {
		StringBuilder sql = new StringBuilder("from RedeemCode r where r.deleted = false and r.parent is not null");
		if(!condition[0].equals("")||!condition[1].equals("")||!condition[6].equals("null")||condition[6].equals("2")||!condition[7].equals("null")||!condition[8].equals(""))
			sql.append(" and r.record is not null");
		if(!condition[0].equals(""))
			sql.append(" and r.record.createTime >= '"+condition[0]+"'");
		if(!condition[1].equals(""))
			sql.append(" and r.record.createTime <= '"+condition[1]+"'");
		if(!condition[2].equals(""))
			sql.append(" and r.parent.endTime >= '"+condition[2]+"'");
		if(!condition[3].equals(""))
			sql.append(" and r.parent.endTime <= '"+condition[3]+"'");
		if(!condition[4].equals(""))
			sql.append(" and r.parent.product.name like '%"+condition[4]+"%'");
		if(!condition[5].equals(""))
			sql.append(" and r.code = "+condition[5]);
		if(condition[6].equals("1")) //未过期中未兑换的  
			sql.append(" and r.used = false and r.parent.endTime >= '" + FolderUtil.getFolder() + "'");
		if(condition[6].equals("2")) //未过期中已经兑换的 
			sql.append(" and r.used = true and r.parent.endTime >= '" + FolderUtil.getFolder() + "'");
		if(condition[6].equals("3")) //已经过期的 
			sql.append(" and r.parent.endTime < '" + FolderUtil.getFolder() + "'");
		if(!condition[7].equals("null"))
			sql.append(" and r.record.customer.city.id = '"+condition[7]+"'");
		if(!condition[8].equals(""))
			sql.append(" and r.record.customer.phone = '"+condition[8]+"'");
		String sql1 = sql+ " order by r.parent.product.name desc, r.parent.batch desc, r.parent.endTime desc , used asc";
		System.out.println(sql);
		int first = (pageNo - 1) * pageSize;
		List<RedeemCode> items = this.queryByHql(sql1, null,first, pageSize);
		int count = Integer.parseInt(this.findUnique("select count(*) " + sql, null).toString());
		System.out.println(count);
		return PageListUtil.getPageList(count, pageNo, items, pageSize);
	}

}
