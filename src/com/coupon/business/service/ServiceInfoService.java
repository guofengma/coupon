package com.coupon.business.service;

import java.util.List;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.service.BaseService;
import com.coupon.business.entity.ServiceInfo;

public interface ServiceInfoService  extends BaseService<ServiceInfo , String>{

	List<ServiceInfo> findMyServiceInfo(String customerId);

	IPageList<ServiceInfo> findUndealByAdmin(int pageNo, int pageSize);

	IPageList<ServiceInfo> findUndealByManager(int pageNo, int pageSize, String string);

	IPageList<ServiceInfo> findUndealByStaff(int pageNo, int pageSize, String id);

}
