package com.coupon.business.dao;

import java.util.List;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.dao.BaseDao;
import com.coupon.business.entity.ServiceInfo;

public interface ServiceInfoDao extends BaseDao<ServiceInfo , String>{

	List<ServiceInfo> findMyServiceInfo(String customerId);

	IPageList<ServiceInfo> findUndealByAdmin(int pageNo, int pageSize);

	IPageList<ServiceInfo> findUndealByManager(int pageNo, int pageSize, String cityIds);

	IPageList<ServiceInfo> findUndealByStaff(int pageNo, int pageSize, String userId);

	IPageList<ServiceInfo> findDealByAdmin(int pageNo, int pageSize);

	IPageList<ServiceInfo> findDealByManager(int pageNo, int pageSize, String cityIds);

	IPageList<ServiceInfo> findDealByStaff(int pageNo, int pageSize, String userId);


}
