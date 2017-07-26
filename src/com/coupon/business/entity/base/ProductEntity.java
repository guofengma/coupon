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
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import com.coupon.base.entity.BaseEntity;
import com.coupon.business.entity.Record;
import com.coupon.business.entity.RedeemCode;
import com.coupon.system.entity.City;

@MappedSuperclass
public abstract class ProductEntity extends BaseEntity{

	protected String name ;
	
	protected String picPath ;
	
	protected int points ;
	
	protected Date startTime ;
	
	protected Date endTime ;
	
	protected String description ;
	
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "product")
	protected List<Record> record;
	
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "product")
	protected List<RedeemCode> redeemCode;
	
	@ManyToMany(targetEntity = City.class, fetch = FetchType.EAGER)
	@JoinTable(name = "coupon_city_product", joinColumns = @JoinColumn(name = "PRODUCT_ID"), inverseJoinColumns = @JoinColumn(name = "CITY_ID"))
	protected Set<City> city = new HashSet<City>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Record> getRecord() {
		return record;
	}

	public void setRecord(List<Record> record) {
		this.record = record;
	}

	public List<RedeemCode> getRedeemCode() {
		return redeemCode;
	}

	public void setRedeemCode(List<RedeemCode> redeemCode) {
		this.redeemCode = redeemCode;
	}

	public Set<City> getCity() {
		return city;
	}

	public void setCity(Set<City> city) {
		this.city = city;
	}


}
