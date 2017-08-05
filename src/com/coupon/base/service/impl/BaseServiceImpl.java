package com.coupon.base.service.impl;

import java.io.Serializable;
import java.util.List;

import com.coupon.base.dao.BaseDao;
import com.coupon.base.service.BaseService;

public abstract class BaseServiceImpl<T, ID extends Serializable> implements
		BaseService<T, ID> {

	protected BaseDao<T, ID> baseDao;

	protected void setBaseDao(BaseDao<T, ID> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public T save(T entity) {
		return this.baseDao.save(entity);
	}

	@Override
	public void delete(ID id) {
		this.baseDao.delete(id);
	}

	@Override
	public T findById(ID id) {
		return this.baseDao.findById(id);
	}

	@Override
	public void update(T entity) {
		this.baseDao.update(entity);
	}
	
	
	@Override
	public void batchMarkDelete(ID[] items){
		this.baseDao.batchMarkDelete(items);
	}
	
	/**
	 * 批量保存
	 */
	@Override
	public void batchSave(List<T> items){
		this.baseDao.batchSave(items);
	}
	
	/**
	 * 批量保存
	 */
	@Override
	public void batchUpdate(List<T> items){
		this.baseDao.batchUpdate(items);
	}

}
