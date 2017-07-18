package com.coupon.business.entity.base;

import java.util.Date;

import com.coupon.base.entity.BaseEntity;

public class ProductEntity extends BaseEntity{

	protected String name ;
	
	protected String picPath ;
	
	protected int points ;
	
	protected Date startTime ;
	
	protected Date endTime ;
	
	protected String description ;

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
}
