package com.coupon.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coupon.base.common.paging.IPageList;
import com.coupon.base.common.paging.PageList;
import com.coupon.base.service.impl.BaseServiceImpl;
import com.coupon.business.dao.RechargeCodeDao;
import com.coupon.business.entity.RechargeCode;
import com.coupon.business.service.RechargeCodeService;

@Service
public class RechargeCodeServiceImpl extends BaseServiceImpl<RechargeCode, String> implements RechargeCodeService{

	@Autowired
	protected void setRechargeCodeDao(RechargeCodeDao rechargeCodeDao) {
		setBaseDao(rechargeCodeDao);
	}
	
	protected RechargeCodeDao getRechargeCodeDao() {
		return (RechargeCodeDao)this.baseDao;
	}

	@Override
	public IPageList<RechargeCode> findBatch(int pageNo, int pageSize) {
		return getRechargeCodeDao().findBatch(pageNo,pageSize);
	}

	@Override
	public PageList<RechargeCode> findByCondition(int pageNo, int pageSize, String[] condition) {
		return getRechargeCodeDao().findByCondition(pageNo,pageSize,condition);
	}

	@Override
	public List<RechargeCode> findCanBeGivenBatch() {
		return getRechargeCodeDao().findCanBeGivenBatch();
		
	}

	@Override
	public RechargeCode findByKeyt(String keyt) {
		return getRechargeCodeDao().findByKeyt(keyt);
	}

	@Override
	public List<RechargeCode> findByIds(String ids) {
		String[] idsArray = ids.split(";");
		StringBuilder idSql = new StringBuilder("");
		for(String temp : idsArray){
			idSql.append("'" + temp + "',");
		}
		if(idsArray.length>0)
			idSql.deleteCharAt(idSql.length()-1);
		return  getRechargeCodeDao().findByIds(idSql.toString());
	}

	@Override
	public IPageList<RechargeCode> findMyApplyRecharegeCode(int pageNo, int pageSize,String userId) {
		return  getRechargeCodeDao().findMyApplyRecharegeCode(pageNo,pageSize,userId);
	}

	@Override
	public List<RechargeCode> findFrontBatch() {
		return  getRechargeCodeDao().findFrontBatch();
	}

	@Override
	public IPageList<RechargeCode> findUndealRechargeCodeByAdmin(int pageNo, int pageSize) {
		return  getRechargeCodeDao().findUndealRechargeCodeByAdmin(pageNo,pageSize);
	}

	@Override
	public IPageList<RechargeCode> findUndealRechargeByManager(int pageNo, int pageSize, String cityIds) {
		return  getRechargeCodeDao().findUndealRechargeByManager( pageNo, pageSize, cityIds);
	}
}
