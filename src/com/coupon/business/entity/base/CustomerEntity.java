package com.coupon.business.entity.base;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import com.coupon.base.entity.BaseEntity;
import com.coupon.business.entity.Bank;
import com.coupon.business.entity.Record;
import com.coupon.system.entity.City;
import com.coupon.system.entity.User;

@MappedSuperclass
public abstract class CustomerEntity extends BaseEntity{

	protected String phone ;//电话
	
	protected String name ;//姓名
	
	protected int point ;//剩余积分
	
	protected String password ; //登录密码
	
	@ManyToOne
	@JoinColumn(name = "bankId")
	protected Bank bank;  //对应兑换服务地址
	
	@ManyToOne
	@JoinColumn(name = "cityId")
	protected City city;  //所属城市
	
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "customer")
	protected List<Record> record; //对应兑换记录
	
	@ManyToOne
	@JoinColumn(name = "userId")
	protected User user;  //所属业务员

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public List<Record> getRecord() {
		return record;
	}

	public void setRecord(List<Record> record) {
		this.record = record;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
