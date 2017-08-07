package com.coupon.business.entity.base;


import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.coupon.base.entity.BaseEntity;
import com.coupon.business.entity.Product;
import com.coupon.business.entity.Record;
import com.coupon.business.entity.RedeemCode;
import com.coupon.system.entity.User;

@MappedSuperclass
public abstract class RedeemCodeEntity extends BaseEntity {
	
	protected String code ;//兑换码
	
	protected boolean used ;//是否已经使用
	
	protected Date startTime;//有效期开始时间
	
	protected Date endTime;//有效期结束时间
	
	protected String batch;//批次
	
	@ManyToOne
	@JoinColumn(name = "userId")
	protected  User user;  //对应商品
	
	@ManyToOne
	@JoinColumn(name = "parent_id")
	protected RedeemCode parent;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "parent")
	protected List<RedeemCode> children;

	@OneToOne(cascade = { CascadeType.ALL }, mappedBy = "redeemCode")
	protected Record record;
	
	@ManyToOne
	@JoinColumn(name = "productId")
	protected  Product product;  //对应商品

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public RedeemCode getParent() {
		return parent;
	}

	public void setParent(RedeemCode parent) {
		this.parent = parent;
	}

	public List<RedeemCode> getChildren() {
		return children;
	}

	public void setChildren(List<RedeemCode> children) {
		this.children = children;
	}
	
	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
}
