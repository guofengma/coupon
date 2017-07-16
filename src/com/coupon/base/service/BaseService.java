package com.coupon.base.service;

import java.io.Serializable;
import java.util.List;

/**
 * Service基类，定义Service的基本方法
 * 
 * @author Danny
 *
 * @param <T>
 * @param <ID>
 */
public interface BaseService<T, ID extends Serializable> {

	/**
	 * 添加实体
	 * 
	 * @param entity
	 *            实体
	 * @return
	 */
	public T save(T entity);

	/**
	 * 删除实体
	 * 
	 * @param id
	 *            要删除的实体ID
	 */
	public void delete(ID id);

	/**
	 * 根据ID查找实体
	 * 
	 * @param id
	 * @return
	 */
	public T findById(ID id);

	/**
	 * 更新实体
	 * 
	 * @param entity
	 *            要更新的实体
	 */
	public void update(T entity);

	/**
	 * 更新实体集合
	 * 
	 * @param entity
	 *            要更新的实体
	 */

	/**
	 * 批量删除
	 * 
	 * @param items
	 *            要删除的实体ID集合
	 */
	public void batchMarkDelete(ID[] items);

	/**
	 * 批量保存
	 * @param items
	 */
	void batchSave(List<T> items);

}
