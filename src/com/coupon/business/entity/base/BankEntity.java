package com.coupon.business.entity.base;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import com.coupon.base.entity.BaseEntity;
import com.coupon.business.entity.Customer;
import com.coupon.system.entity.City;

@MappedSuperclass
public abstract class BankEntity extends BaseEntity{
	
	protected String name ;//银行名
	
	protected String address ;//银行通讯地址
	
	protected String phone;//联系电话
	
	@ManyToOne
	@JoinColumn(name = "cityId")
	protected City city;
	
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "bank")
	protected List<Customer> customer;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public List<Customer> getCustomer() {
		return customer;
	}

	public void setCustomer(List<Customer> customer) {
		this.customer = customer;
	}
	

}
