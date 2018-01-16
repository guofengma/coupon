package com.coupon.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.service.impl.BaseServiceImpl;
import com.coupon.business.dao.ServiceInfoDao;
import com.coupon.business.entity.ServiceInfo;
import com.coupon.business.service.ServiceInfoService;

@Service
public class ServiceInfoServiceImpl extends BaseServiceImpl<ServiceInfo, String> implements ServiceInfoService{
	@Autowired
	protected void setServiceInfoDao(ServiceInfoDao serviceInfoDao) {
		setBaseDao(serviceInfoDao);
	}
	
	protected ServiceInfoDao getServiceInfoDao() {
		return (ServiceInfoDao)this.baseDao;
	}

	@Override
	public List<ServiceInfo> findMyServiceInfo(String customerId) {
		return getServiceInfoDao().findMyServiceInfo(customerId);
	}

	@Override
	public IPageList<ServiceInfo> findUndealByAdmin(int pageNo, int pageSize) {
		return getServiceInfoDao().findUndealByAdmin(pageNo,pageSize);
	}

	@Override
	public IPageList<ServiceInfo> findUndealByManager(int pageNo, int pageSize, String cityIds) {
		return getServiceInfoDao().findUndealByManager(pageNo, pageSize ,cityIds);
	}

	@Override
	public IPageList<ServiceInfo> findUndealByStaff(int pageNo, int pageSize, String userId) {
		return getServiceInfoDao().findUndealByStaff( pageNo,pageSize,userId);
	}
}
