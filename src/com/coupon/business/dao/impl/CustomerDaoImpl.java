package com.coupon.business.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageList;
import com.coupon.base.common.paging.PageListUtil;
import com.coupon.base.dao.impl.BaseDaoImpl;
import com.coupon.business.dao.CustomerDao;
import com.coupon.business.entity.Customer;
import com.coupon.business.entity.RechargeCode;
import com.coupon.system.entity.City;
import com.coupon.system.entity.User;
import com.coupon.util.FolderUtil;

@Repository
public class CustomerDaoImpl extends BaseDaoImpl<Customer, String> implements CustomerDao{

	@Override
	protected Class<Customer> getEntityClass() {
		return Customer.class;
	}

	@Override
	public IPageList<Customer> findByAdmin(int pageNo, int pageSize, boolean check,String condition) {
		String addSql = "";
		if(!condition.equals(""))
			addSql = " and (name like '%"+condition+"%' or phone like '%"+condition+"%')";
		int first = (pageNo - 1) * pageSize;
		List<Customer> items = this.queryByHql(
				"from Customer where deleted=false and statu="+check+addSql+ " order by createTime desc", null,
				first, pageSize);
		int count = Integer.parseInt(this.findUnique(
				"select count(*) from Customer where deleted=false and statu="+check+ addSql, null)
				.toString());
		System.out.println(count);
		return PageListUtil.getPageList(count, pageNo, items, pageSize);
	}

	@Override
	public IPageList<Customer> findByManager(int pageNo, int pageSize, boolean check, String cityIds,String condition) {
		String addSql = "";
		if(!condition.equals(""))
			addSql = " and (r.name like '%"+condition+"%' or r.phone like '%"+condition+"%')";
		int first = (pageNo - 1) * pageSize;
		List<Customer> tempList = this.queryByHql(
				"from Customer r where r.deleted = false and r.statu = "+check+ addSql+" order by r.createTime desc", null,
				first, pageSize);
		List<Customer> items = new ArrayList<Customer>();
		for(Customer temp : tempList){
			boolean is = false ;
			for(City tempCity :temp.getCity()){
				if(cityIds.contains(tempCity.getId())){
					is = true ;
				}
			}
			if(is){
				items.add(temp);
			}
		}
		System.out.println(items.size());
		int count = items.size();
		System.out.println(count);
		return PageListUtil.getPageList(count, pageNo, items, pageSize);
	}

	@Override
	public IPageList<Customer> findByStaff(int pageNo, int pageSize, boolean check, String userId,String condition) {
		String addSql = "";
		if(!condition.equals(""))
			addSql = " and (r.name like '%"+condition+"%' or r.phone like '%"+condition+"%')";
		int first = (pageNo - 1) * pageSize;
		List<Customer> items = this.queryByHql(
				"from Customer r where r.deleted=false and r.statu = "+check+" and r.user.id='"+userId+"'"+addSql+" order by r.createTime desc", null,
				first, pageSize);
		int count = Integer.parseInt(this.findUnique(
				"select count(*) from Customer r where r.deleted = false and r.user.id='"+userId+"' and r.statu="+check+addSql, null)
				.toString());
		return PageListUtil.getPageList(count, pageNo, items, pageSize);
	}

	@Override
	public List<Customer> findByIds(String[] ids) {
		StringBuilder id = new StringBuilder();
		for(String temp : ids){
			id.append("'"+temp+"',");
		}
		id.deleteCharAt(id.length()-1);
		return (List<Customer>) this.queryByHql("from Customer where id in ("+id.toString()+")",
				null);
	}

	/*
	 * {startTime,endTime,name,latestName,city,phone}
	 *      0        1      2     3          4   5
	 */
	@Override
	public PageList<Customer> findByCondition(int pageNo, int pageSize, String[] condition) {
		StringBuilder sql = new StringBuilder("from Customer r where r.deleted = false and r.statu = true");
		/*if(!condition[0].equals("")||!condition[1].equals("")||!condition[3].equals(""))
			sql.append(" and r.record is not null");*/
		if(!condition[0].equals(""))
			sql.append(" and r.latestChargeTime >= '"+condition[0]+"'");
		if(!condition[1].equals(""))
			sql.append(" and r.latestChargeTime <= '"+condition[1]+"'");
		if(!condition[2].equals(""))
			sql.append(" and r.name like '%"+condition[2]+"%'");
		if(!condition[3].equals(""))
			sql.append(" and r.latestChargeUser.displayName like '%"+condition[3]+"%'");
		if(!condition[4].equals("null"))
			sql.append(" and r.city.id = '"+condition[4]+"'");
		if(!condition[5].equals(""))
			sql.append(" and r.phone = '"+condition[5]+"'");
		String sql1 = sql + " order by r.latestChargeTime desc , r.totalAddUp desc , r.point desc";
		System.out.println(sql1);
		int first = (pageNo - 1) * pageSize;
		List<Customer> items = this.queryByHql(sql1, null,
				first, pageSize);
		int count = Integer.parseInt(this.findUnique(
				"select count(*) "+sql, null)
				.toString());
		System.out.println(count);
		return PageListUtil.getPageList(count, pageNo, items, pageSize);
	}

}
