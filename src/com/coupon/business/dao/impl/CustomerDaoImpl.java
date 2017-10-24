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
				"from Customer r where r.deleted = false and r.register = false and r.statu = "+check+ addSql+" order by r.createTime desc", null,
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
				"from Customer r where r.deleted=false and  r.register = false and r.statu = "+check+" and r.user.id='"+userId+"'"+addSql+" order by r.createTime desc", null,
				first, pageSize);
		int count = Integer.parseInt(this.findUnique(
				"select count(*) from Customer r where r.deleted = false  and r.register = false and r.user.id='"+userId+"' and r.statu="+check+addSql, null)
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
	 * {startTime,endTime,name,latestName,city,phone,roleType}
	 *      0        1      2     3          4   5       6(1是管理员和超级管理员，2是大区经理，3是员工）
	 */
	@Override
	public PageList<Customer> findByCondition(int pageNo, int pageSize, User user ,String[] condition) {
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
		/*if(!condition[4].equals("null"))
			sql.append(" and r.city.id = '"+condition[4]+"'");*/
		if(!condition[5].equals(""))
			sql.append(" and r.phone = '"+condition[5]+"'");
		if(condition[6].equals("2"))
			sql.append(" and r.register = false");
		if(condition[6].equals("3"))
			sql.append(" and r.register = false and r.user.id = '"+user.getId()+"'");
		String sql1 = sql + " order by r.latestChargeTime desc , r.totalAddUp desc , r.point desc";
		System.out.println(sql1);
		int first = (pageNo - 1) * pageSize;
		int count = 0;
		List<Customer> tempList = this.queryByHql(sql1, null,first, pageSize);
		List<Customer> items = new ArrayList<Customer>();	
		List<Customer> items1 = new ArrayList<Customer>();	
		if(condition[4].equals("null")){
			items1 = this.queryByHql(sql1, null,first, pageSize);
		}else{
			for(Customer temp : tempList){
				boolean is = false ;
				for(City tempCity : temp.getCity()){
					if(tempCity.getId().equals(condition[4]))
						is = true ;
				}
				if(is)
					items1.add(temp);
			}
		}
		if(condition[6].equals("2")){
			StringBuilder cityIds = new StringBuilder();
			for(City temp : user.getCity()){
				cityIds.append(temp.getId()+";");
			}
			for(Customer temp : items1){
				boolean is = false ;
				for(City tempCity :temp.getCity()){
					if(cityIds.toString().contains(tempCity.getId())){
						is = true ;
					}
				}
				if(is){
					items.add(temp);
				}
			}
		}
		if(condition[6].equals("1")||condition[6].equals("3")){
			items = items1; 
		}
		count = items.size();
		return PageListUtil.getPageList(count, pageNo, items, pageSize);
	}

	@Override
	public List<Customer> exportByCondition(User user, String[] condition) {
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
		/*if(!condition[4].equals("null"))
			sql.append(" and r.city.id = '"+condition[4]+"'");*/
		if(!condition[5].equals(""))
			sql.append(" and r.phone = '"+condition[5]+"'");
		if(condition[6].equals("3"))
			sql.append(" and r.user.id = '"+user.getId()+"'");
		String sql1 = sql + " order by r.latestChargeTime desc , r.totalAddUp desc , r.point desc";
		System.out.println(sql1);
		List<Customer> tempList = this.queryByHql(sql1, null);
		List<Customer> items = new ArrayList<Customer>();	
		List<Customer> items1 = new ArrayList<Customer>();	
		if(condition[4].equals("null")){
			items1 = this.queryByHql(sql1, null);
		}else{
			for(Customer temp : tempList){
				boolean is = false ;
				for(City tempCity : temp.getCity()){
					if(tempCity.getId().equals(condition[4]))
						is = true ;
				}
				if(is)
					items1.add(temp);
			}
		}
		if(condition[6].equals("2")){
			StringBuilder cityIds = new StringBuilder();
			for(City temp : user.getCity()){
				cityIds.append(temp.getId()+";");
			}
			for(Customer temp : items1){
				boolean is = false ;
				for(City tempCity :temp.getCity()){
					if(cityIds.toString().contains(tempCity.getId())){
						is = true ;
					}
				}
				if(is){
					items.add(temp);
				}
			}
		}
		if(condition[6].equals("1")||condition[6].equals("3")){
			items = items1; 
		}
		return items;
	}

	@Override
	public Customer findByPhone(String phone) {
		return (Customer)this.findUnique("from Customer where deleted=false and phone = '"+phone+"'",
				null);
	}

	@Override
	public Customer findCheckedByPhone(String phone) {
		return (Customer)this.findUnique("from Customer where deleted=false and statu=true and phone = '"+phone+"'",
				null);
	}

	@Override
	public List<Customer> getAllCustomers() {
		return (List<Customer>) this.queryByHql("from Customer where deleted=false and statu=true",
				null);
	}

}
