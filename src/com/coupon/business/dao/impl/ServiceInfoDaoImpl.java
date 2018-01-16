package com.coupon.business.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageListUtil;
import com.coupon.base.dao.impl.BaseDaoImpl;
import com.coupon.business.dao.ServiceInfoDao;
import com.coupon.business.entity.Record;
import com.coupon.business.entity.ServiceInfo;
import com.coupon.system.entity.City;

@Repository
public class ServiceInfoDaoImpl  extends BaseDaoImpl<ServiceInfo, String> implements ServiceInfoDao{
	@Override
	protected Class<ServiceInfo> getEntityClass() {
		return ServiceInfo.class;
	}

	@Override
	public List<ServiceInfo> findMyServiceInfo(String customerId) {
		List<ServiceInfo> items = this.queryByHql(
				"from ServiceInfo r where r.deleted=false and r.record.customer.id = '"+customerId +"' order by createTime desc", null);
		return items ;
	}

	@Override
	public IPageList<ServiceInfo> findUndealByAdmin(int pageNo, int pageSize) {
		int first = (pageNo - 1) * pageSize;
		List<ServiceInfo> items = this.queryByHql(
				"from ServiceInfo r where r.deleted=false and r.deal = '0' order by createTime desc", null,
				first, pageSize);
		int count = Integer.parseInt(this.findUnique(
				"select count(*) from ServiceInfo r where r.deleted=false and r.deal = '0'", null)
				.toString());
		System.out.println(count);
		return PageListUtil.getPageList(count, pageNo, items, pageSize);
	}

	@Override
	public IPageList<ServiceInfo> findUndealByManager(int pageNo, int pageSize, String cityIds) {
		int first = (pageNo - 1) * pageSize;
		List<ServiceInfo> tempList = this.queryByHql(
				"from ServiceInfo r where r.deleted=false and r.deal='0' order by createTime desc", null,
				first, pageSize);
		List<ServiceInfo> items = new ArrayList<ServiceInfo>();
		for(ServiceInfo temp : tempList){
			boolean is = false ;
			for(City tempCity :temp.getRecord().getCustomer().getCity()){
				if(cityIds.contains(tempCity.getId())){
					is = true ;
				}
			}
			if(is){
				items.add(temp);
			}
		}
		int count = items.size();
		return PageListUtil.getPageList(count, pageNo, items, pageSize);
	}

	@Override
	public IPageList<ServiceInfo> findUndealByStaff(int pageNo, int pageSize, String userId) {
		int first = (pageNo - 1) * pageSize;
		List<ServiceInfo> items = this.queryByHql(
				"from ServiceInfo r where r.deleted=false and r.deal='0' and r.record.customer.user.id ='"+userId+"' order by createTime desc", null,
				first, pageSize);
		int count = Integer.parseInt(this.findUnique(
				"select count(*) from ServiceInfo r where r.deleted=false and r.deal='0' and r.record.customer.user.id ='"+userId+"' ", null)
				.toString());
		System.out.println(count);
		return PageListUtil.getPageList(count, pageNo, items, pageSize);
	}
}
