package com.coupon.business.entity.base;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import com.coupon.base.entity.BaseEntity;
import com.coupon.business.entity.Customer;
import com.coupon.business.entity.Product;
import com.coupon.business.entity.RechargeCode;
import com.coupon.business.entity.Record;
import com.coupon.business.entity.RedeemCode;
import com.coupon.business.entity.ServiceInfo;
import com.coupon.system.entity.User;

@MappedSuperclass
public abstract class RecordEntity extends BaseEntity{
	
	@OneToOne
	@JoinColumn(name = "redeemCodeId")
	protected RedeemCode redeemCode;  //兑换记录对应的商品兑换码
	
	@OneToOne
	@JoinColumn(name = "rechargeCodeId")
	protected RechargeCode rechargeCode;  //兑换记录对应的e兑卡兑换码（充值积分卡）

	@ManyToOne
	@JoinColumn(name = "customerId")
	protected Customer customer;  //兑换记录对应客户
	
	@ManyToOne
	@JoinColumn(name = "userId")
	protected User user;  //新增记录的员工
	
	@ManyToOne
	@JoinColumn(name = "checkUserId")
	protected User checkUser;  //审核记录的员工
	
	@OneToOne(cascade = { CascadeType.ALL }, mappedBy = "record",fetch = FetchType.EAGER)
	protected ServiceInfo serviceInfo;
	
	@OneToOne
	@JoinColumn(name = "productId")
	protected Product product;  //对应商品
	
	protected int points ;//发生交易的时候所需要的积分（充值或消费）
	
	protected boolean  raise;//标记是充值还是消费，true为充值，false为消费
	
	protected boolean statu ;//是否已经通过审核
	
	protected boolean deal;//是否被管理员处理（点击通过审核或不通过审核时置为true）
	
	protected String description ;//发生交易时的备注

	public RedeemCode getRedeemCode() {
		return redeemCode;
	}

	public void setRedeemCode(RedeemCode redeemCode) {
		this.redeemCode = redeemCode;
	}

	public RechargeCode getRechargeCode() {
		return rechargeCode;
	}

	public void setRechargeCode(RechargeCode rechargeCode) {
		this.rechargeCode = rechargeCode;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(User checkUser) {
		this.checkUser = checkUser;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public int getPoints() {
		return points;
	}

	public boolean isRaise() {
		return raise;
	}

	public String getDescription() {
		return description;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void setRaise(boolean raise) {
		this.raise = raise;
	}

	public boolean isStatu() {
		return statu;
	}

	public void setStatu(boolean statu) {
		this.statu = statu;
	}

	public boolean isDeal() {
		return deal;
	}

	public void setDeal(boolean deal) {
		this.deal = deal;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
