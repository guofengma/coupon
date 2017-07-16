package com.coupon.base.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 实体基类，定义了公有属性
 * @author Danny
 *
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	public BaseEntity() {
		super();
		this.id = UUID.randomUUID().toString();
		this.remark = "";
		this.deleted = false;
		this.modifiedTime = new Timestamp(System.currentTimeMillis());
		this.createTime = this.modifiedTime;
	}

	@Id
	/**
	 * 主键
	 */
	protected String id;

	/**
	 * 备注
	 */
	protected String remark;

	/**
	 * 是否删除
	 */
	protected boolean deleted;
	/**
	 * 最后一次修改时间
	 */
	protected Timestamp modifiedTime;

	/**
	 * 创建时间
	 */
	protected Timestamp createTime;

	public String getId() {
		return id;
	}

	public String getRemark() {
		return remark;
	}

	protected void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Timestamp getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Timestamp modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

}
