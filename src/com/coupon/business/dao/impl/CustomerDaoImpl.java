package com.coupon.business.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageList;
import com.coupon.base.common.paging.PageListUtil;
import com.coupon.base.dao.impl.BaseDaoImpl;
import com.coupon.business.dao.CustomerDao;
import com.coupon.business.entity.Customer;
import com.coupon.business.entity.RechargeCode;
import com.coupon.system.dao.CityDao;
import com.coupon.system.entity.City;
import com.coupon.system.entity.User;
import com.coupon.util.FolderUtil;

@Repository
public class CustomerDaoImpl extends BaseDaoImpl<Customer, String> implements CustomerDao{

	@Autowired CityDao cityDao ;
	
	@Override
	protected Class<Customer> getEntityClass() {
		return Customer.class;
	}

	@Override
	public IPageList<Customer> findByAdmin(int pageNo, int pageSize, boolean check,String condition) {
		String addSql = "";
		if(!condition.equals(""))
			addSql = " and (r.name like '%"+condition+"%' or r.phone like '%"+condition+"%') ";
		int first = (pageNo - 1) * pageSize;
		List<Customer> items = this.queryByHql(
				"from Customer r left outer join r.city c where r.deleted=false and r.statu="+check+addSql+ " group by r.id order by r.createTime desc", null,
				first, pageSize);
		int count = this.queryByHql("select count(*) from Customer r left outer join r.city c where r.deleted = false and r.statu = "+check+ addSql+" group by r.id ", null).size();
		System.out.println(count);
		return PageListUtil.getPageList(count, pageNo, items, pageSize);
	}

	@Override
	public IPageList<Customer> findByManager(int pageNo, int pageSize, boolean check, String cityIds,String condition) {
		StringBuilder  addSql = new StringBuilder();
		if(!condition.equals(""))
			addSql.append(" and (r.name like '%"+condition+"%' or r.phone like '%"+condition+"%')");
		int first = (pageNo - 1) * pageSize;
		if(cityIds.split(";").length>0){
			addSql.append(" and c.id in (");
			for(String id: cityIds.split(";")){
				addSql.append("'"+id+"',");
			}
			addSql.deleteCharAt(addSql.length()-1);
			addSql.append(")");
		}
		addSql.append(" group by r.id");
		List<Customer> items = this.queryByHql(
				"from Customer r left outer join r.city c where r.deleted = false and r.register = false and r.statu = "+check+ addSql+" order by r.createTime desc", null,
				first, pageSize);
		int count = this.queryByHql("select count(*) " +" from Customer r left outer join r.city c where r.deleted = false and r.register = false and r.statu = "+check+ addSql, null).size();
		return PageListUtil.getPageList(count, pageNo, items, pageSize);
	}

	@Override
	public IPageList<Customer> findByStaff(int pageNo, int pageSize, boolean check, String userId,String condition) {
		String addSql = "";
		if(!condition.equals(""))
			addSql = " and (r.name like '%"+condition+"%' or r.phone like '%"+condition+"%') ";
		addSql = addSql + " group by r.id ";
		int first = (pageNo - 1) * pageSize;
		List<Customer> items = this.queryByHql(
				"from Customer r left outer join r.city c where r.deleted=false and  r.register = false and r.statu = "+check+" and r.user.id='"+userId+"'"+addSql+" order by r.createTime desc", null,
				first, pageSize);
		int count = this.queryByHql("select count(*) from Customer r left outer join r.city c where r.deleted = false  and r.register = false and r.user.id='"+userId+"' and r.statu="+check+addSql,null).size();
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

	@Override
	public PageList<Customer> findByCondition(int pageNo, int pageSize, User user ,String[] condition) {
		StringBuilder sql = new StringBuilder("from Customer r left outer join r.city c where r.deleted = false and r.statu = true");
		if(!condition[0].equals(""))
			sql.append(" and r.latestChargeTime >= '"+condition[0]+"'");
		if(!condition[1].equals(""))
			sql.append(" and r.latestChargeTime <= '"+condition[1]+"'");
		if(!condition[2].equals(""))
			sql.append(" and r.name like '%"+condition[2]+"%'");
		if(!condition[3].equals(""))
			sql.append(" and r.latestChargeUser.displayName like '%"+condition[3]+"%'");
		if(!condition[5].equals(""))
			sql.append(" and r.phone = '"+condition[5]+"'");
		if(condition[6].equals("2"))
			sql.append(" and r.register = false");
		if(condition[6].equals("3"))
			sql.append(" and r.register = false and r.user.id = '"+user.getId()+"'");		
		int first = (pageNo - 1) * pageSize;
		if(condition[6].equals("2")){//大区经理
			Set<City> userCity = user.getCity();
			if(userCity.size()>0){
				sql.append(" and c.id in (");
				for(City c : userCity){
					sql.append("'"+c.getId()+"',");
				}
				sql.deleteCharAt(sql.length()-1);
				sql.append(")");
			}
		}
		if(!condition[4].equals("null")){//选择城市查询条件
			City city = cityDao.findById(condition[4]);
			if(city.getParent()==null){//一级城市
				sql.append(" and c.id in ('"+condition[4]+"',");
				for(City tempC : city.getUsedChildren()){
					sql.append("'"+tempC.getId()+"',");
				}
				sql.deleteCharAt(sql.length()-1);
				sql.append(")");
			}else{
				sql.append(" and c.id in ('"+condition[4]+"')");
			}
		}
		String sql1 = sql + " group by r.id order by r.latestChargeTime desc , r.totalAddUp desc , r.point desc";
		List<Customer> items = this.queryByHql(sql1, null,first, pageSize);
		int count =this.queryByHql("select count(*) " +sql +" group by r.id", null).size();
		return PageListUtil.getPageList(count, pageNo, items, pageSize);
	}

	@Override
	public List<Customer> exportByCondition(User user, String[] condition) {
		StringBuilder sql = new StringBuilder("from Customer r left outer join r.city c where r.deleted = false and r.statu = true");
		if(!condition[0].equals(""))
			sql.append(" and r.latestChargeTime >= '"+condition[0]+"'");
		if(!condition[1].equals(""))
			sql.append(" and r.latestChargeTime <= '"+condition[1]+"'");
		if(!condition[2].equals(""))
			sql.append(" and r.name like '%"+condition[2]+"%'");
		if(!condition[3].equals(""))
			sql.append(" and r.latestChargeUser.displayName like '%"+condition[3]+"%'");
		if(!condition[5].equals(""))
			sql.append(" and r.phone = '"+condition[5]+"'");
		if(condition[6].equals("2"))
			sql.append(" and r.register = false");
		if(condition[6].equals("3"))
			sql.append(" and r.register = false and r.user.id = '"+user.getId()+"'");		
		if(condition[6].equals("2")){//大区经理
			Set<City> userCity = user.getCity();
			if(userCity.size()>0){
				sql.append(" and c.id in (");
				for(City c : userCity){
					sql.append("'"+c.getId()+"',");
				}
				sql.deleteCharAt(sql.length()-1);
				sql.append(")");
			}
		}
		if(!condition[4].equals("null")){//选择城市查询条件
			City city = cityDao.findById(condition[4]);
			if(city.getParent()==null){//一级城市
				sql.append(" and c.id in ('"+condition[4]+"',");
				for(City tempC : city.getUsedChildren()){
					sql.append("'"+tempC.getId()+"',");
				}
				sql.deleteCharAt(sql.length()-1);
				sql.append(")");
			}else{
				sql.append(" and c.id in ('"+condition[4]+"')");
			}
		}
		String sql1 = sql + " group by r.id order by r.latestChargeTime desc , r.totalAddUp desc , r.point desc";
		List<Object> object = this.queryByHql(sql1, null);
		List<Customer> customers = new ArrayList<Customer>();
		for(int i=0;i<object.size();i++){
			Object[] obj = (Object[])object.get(i);//取其中一行的查询结果  
			customers.add((Customer) obj[0]); 
		}
		List<Customer> items1 = this.queryByHql(sql1, null);
		return customers;
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
