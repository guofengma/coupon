package com.coupon.business.entity.base;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	
	protected boolean statu;//是否通过审核
	
	protected boolean deal;//是否被管理员处理（点击通过审核或不通过审核时置为true）
	
	protected String bankAddress;//银行兑换地址
	
	protected boolean register ; //是注册的还是后台添加的

	@ManyToOne
	@JoinColumn(name = "bankId")
	protected Bank bank;  //对应兑换服务地址
	
	@ManyToMany(targetEntity = City.class, fetch = FetchType.EAGER)
	@JoinTable(name = "coupon_customer_city", joinColumns = @JoinColumn(name = "CUSTOMER_ID"), inverseJoinColumns = @JoinColumn(name = "CITY_ID"))
	protected Set<City> city = new HashSet<City>();
	
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "customer" ,fetch=FetchType.EAGER)
	protected List<Record> record; //对应兑换记录
	
	@ManyToOne
	@JoinColumn(name = "userId")
	protected User user;  //所属业务员
	
	@ManyToOne
	@JoinColumn(name = "checkUserId")
	protected User checkUser;  //审核人员
	
	@ManyToOne
	@JoinColumn(name = "latestChargeUserId")
	protected User latestChargeUser;  //最近一次充值人员
	
	protected Date latestChargeTime;//最近一次充值时间
	
	protected int totalAddUp;//累计兑换积分数量

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

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public Set<City> getCity() {
		return city;
	}

	public void setCity(Set<City> city) {
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
	
	public User getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(User checkUser) {
		this.checkUser = checkUser;
	}

	public User getLatestChargeUser() {
		return latestChargeUser;
	}

	public Date getLatestChargeTime() {
		return latestChargeTime;
	}

	public int getTotalAddUp() {
		return totalAddUp;
	}

	public void setLatestChargeUser(User latestChargeUser) {
		this.latestChargeUser = latestChargeUser;
	}

	public void setLatestChargeTime(Date latestChargeTime) {
		this.latestChargeTime = latestChargeTime;
	}

	public void setTotalAddUp(int totalAddUp) {
		this.totalAddUp = totalAddUp;
	}
	
	public boolean isRegister() {
		return register;
	}

	public void setRegister(boolean register) {
		this.register = register;
	}
	
}
