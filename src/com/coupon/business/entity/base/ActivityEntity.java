package com.coupon.business.entity.base;

import javax.persistence.MappedSuperclass;

import com.coupon.base.entity.BaseEntity;

@MappedSuperclass
public class ActivityEntity extends BaseEntity{
	
	protected String name ;//活动名称
	
	protected String url;//活动链接
	
	protected String picPath;//图片路径

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	
	
}
