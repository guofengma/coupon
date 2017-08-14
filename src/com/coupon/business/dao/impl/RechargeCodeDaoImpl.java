package com.coupon.business.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageList;
import com.coupon.base.common.paging.PageListUtil;
import com.coupon.base.dao.impl.BaseDaoImpl;
import com.coupon.business.dao.RechargeCodeDao;
import com.coupon.business.entity.RechargeCode;
import com.coupon.util.FolderUtil;
@Repository
public class RechargeCodeDaoImpl extends BaseDaoImpl<RechargeCode, String> implements RechargeCodeDao{

	@Override
	protected Class<RechargeCode> getEntityClass() {
		return RechargeCode.class;
	}

	@Override
	public IPageList<RechargeCode> findBatch(int pageNo, int pageSize) {
		int first = (pageNo - 1) * pageSize;
		List<RechargeCode> items = this.queryByHql(
				"from RechargeCode where deleted=false and parent is null order by createTime desc ", null,
				first, pageSize);
		int count = Integer.parseInt(this.findUnique(
				"select count(*) from RechargeCode where deleted=false and parent is null", null)
				.toString());
		System.out.println(count);
		return PageListUtil.getPageList(count, pageNo, items, pageSize);
	}

	/*
	 * {madeStartTime,madeEndTime,startTime,endTime,batch,points,code,keyt,statu,city,phone}
	 *      0              1         2          3      4     5    6     7     8   9     10
	 */
	@Override
	public PageList<RechargeCode> findByCondition(int pageNo, int pageSize, String[] condition) {
		StringBuilder sql = new StringBuilder("from RechargeCode r where r.deleted = false and r.parent is not null");
		if(condition[8].equals("2")||!condition[8].equals("null")||!condition[10].equals(""))
			sql.append(" and r.record is not null");
		if(!condition[0].equals(""))
			sql.append(" and r.createTime >= '"+condition[0]+"'");
		if(!condition[1].equals(""))
			sql.append(" and r.createTime <= '"+condition[1]+"'");
		if(!condition[2].equals(""))
			sql.append(" and r.parent.endTime >= '"+condition[2]+"'");
		if(!condition[3].equals(""))
			sql.append(" and r.parent.endTime <= '"+condition[3]+"'");
		if(!condition[4].equals(""))
			sql.append(" and r.parent.batch like '%"+condition[4]+"%'");
		if(!condition[5].equals(""))
			sql.append(" and r.points = "+condition[5]);
		if(!condition[6].equals(""))
			sql.append(" and r.code = '"+condition[6]+"'");
		if(!condition[7].equals(""))
			sql.append(" and r.keyt = '"+condition[7]+"'");
		if(condition[8].equals("1")) //未过期中未兑换的  
			sql.append(" and r.used = false and r.parent.endTime >= '" + FolderUtil.getFolder() + "'");
		if(condition[8].equals("2")) //未过期中已经兑换的 
			sql.append(" and r.used = true and r.parent.endTime >= '" + FolderUtil.getFolder() + "'");
		if(condition[8].equals("3")) //已经过期的 
			sql.append(" and r.parent.endTime < '" + FolderUtil.getFolder() + "'");
		if(!condition[9].equals("null"))
			sql.append(" and r.record.customer.city.id = '"+condition[8]+"'");
		if(!condition[10].equals(""))
			sql.append(" and r.record.customer.phone = '"+condition[9]+"'");
		String sql1 = sql + " order by r.parent.batch desc , r.points desc , r.parent.endTime desc , used asc";
		System.out.println(sql1);
		int first = (pageNo - 1) * pageSize;
		List<RechargeCode> items = this.queryByHql(sql1, null,
				first, pageSize);
		int count = Integer.parseInt(this.findUnique(
				"select count(*) "+sql, null)
				.toString());
		System.out.println(count);
		return PageListUtil.getPageList(count, pageNo, items, pageSize);
	}

}
