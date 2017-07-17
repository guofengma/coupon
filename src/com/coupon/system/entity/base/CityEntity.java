package com.coupon.system.entity.base;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import com.coupon.base.entity.BaseEntity;
import com.coupon.system.entity.City;

@MappedSuperclass
public abstract class CityEntity extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "parent_id")
	protected City parent;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "parent")
	protected List<City> children;
	
	protected String name ;
	
	protected int priority;
	
	protected boolean used ;

	public City getParent() {
		return parent;
	}

	public void setParent(City parent) {
		this.parent = parent;
	}

	public List<City> getChildren() {
		return children;
	}

	public void setChildren(List<City> children) {
		this.children = children;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}
	
}
