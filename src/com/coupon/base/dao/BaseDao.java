package com.coupon.base.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 基础Dao接口 定义了常用的数据库操作方法
 * 
 * @author Danny
 *
 * @param <T>
 * @param <ID>
 */
public interface BaseDao<T, ID extends Serializable> extends CommonDao {

	/**
	 * 根据ID查找实体
	 * 
	 * @param id
	 *            ID
	 * @return
	 */
	public T findById(ID id);

	/**
	 * 保存实体
	 * 
	 * @param entity
	 * @return
	 */
	public T save(T entity);

	/**
	 * 删除实体
	 * 
	 * @param entity
	 */
	public void delete(T entity);

	/**
	 * 删除实体（根据ID）
	 * 
	 * @param id
	 *            ID
	 */
	public void delete(ID id);

	/**
	 * 删除实体
	 * 
	 * @param clazz
	 *            要删除实体的类型
	 * @param id
	 *            要删除实体的ID
	 */
	public void delete(Class<T> clazz, ID id);

	/**
	 * 更新实体
	 * 
	 * @param entity
	 *            要更新的实体
	 */
	public void update(T entity);

	/**
	 * 批量保存实体
	 * 
	 * @param entities
	 */
	public void saveEntities(List<T> entities);

	/**
	 * 利用实体本身作为查询条件查找实体
	 * 
	 * @param entity
	 *            作为查询条件的实体
	 * @return
	 */
	public List<T> queryEntityByExample(T entity);

	/**
	 * 利用实体本身作为查询条件查找实体
	 * 
	 * @param entity
	 *            作为查询条件的实体
	 * @param start
	 *            开始位置
	 * @param size
	 *            查找步长
	 * @return
	 */
	public List<T> queryEntityByExample(T entity, int start, int size);

	/**
	 * 批量删除
	 * 
	 * @param items
	 *            要删除实体的ID集合
	 */
	public void batchMarkDelete(ID[] items);

}
