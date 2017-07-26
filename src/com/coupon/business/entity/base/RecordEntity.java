package com.coupon.business.entity.base;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import com.coupon.base.entity.BaseEntity;
import com.coupon.business.entity.Customer;
import com.coupon.business.entity.Product;
import com.coupon.business.entity.RedeemCode;

@MappedSuperclass
public abstract class RecordEntity extends BaseEntity{
	
	@OneToOne
	@JoinColumn(name = "redeemCodeId")
	protected RedeemCode redeemCode;  //兑换记录对应客户

	@ManyToOne
	@JoinColumn(name = "customerId")
	protected Customer customer;  //兑换记录对应客户
	
	@ManyToOne
	@JoinColumn(name = "productId")
	protected Product product;  //对应兑换服务地址

	public RedeemCode getRedeemCode() {
		return redeemCode;
	}

	public void setRedeemCode(RedeemCode redeemCode) {
		this.redeemCode = redeemCode;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
}
