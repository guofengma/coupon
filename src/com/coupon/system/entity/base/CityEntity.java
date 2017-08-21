package com.coupon.system.entity.base;

import java.util.ArrayList;
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
import com.coupon.business.entity.Customer;
import com.coupon.business.entity.Product;
import com.coupon.system.entity.City;
import com.coupon.system.entity.User;

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
	
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "city")
	protected List<Bank> bank;
	
	@ManyToMany(targetEntity = Customer.class,cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinTable(name = "coupon_customer_city", joinColumns = @JoinColumn(name = "CITY_ID"), inverseJoinColumns = @JoinColumn(name = "CUSTOMER_ID"))
	protected Set<Customer> customer = new HashSet<Customer>();
	
	@ManyToMany(targetEntity = User.class,cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinTable(name = "coupon_user_city", joinColumns = @JoinColumn(name = "CITY_ID"), inverseJoinColumns = @JoinColumn(name = "USER_ID"))
	protected Set<User> user = new HashSet<User>();
	
	@ManyToMany(targetEntity = Product.class, fetch = FetchType.EAGER)
	@JoinTable(name = "coupon_city_product", joinColumns = @JoinColumn(name = "CITY_ID"), inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))
	protected Set<Product> product = new HashSet<Product>();


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

	public List<Bank> getBank() {
		return bank;
	}

	public void setBank(List<Bank> bank) {
		this.bank = bank;
	}

	public Set<Customer> getCustomer() {
		return customer;
	}

	public void setCustomer(Set<Customer> customer) {
		this.customer = customer;
	}

	public Set<User> getUser() {
		return user;
	}

	public void setUser(Set<User> user) {
		this.user = user;
	}

	public Set<Product> getProduct() {
		return product;
	}

	public void setProduct(Set<Product> product) {
		this.product = product;
	}
	
	public List<City> getUsedChildren() {
		List<City> usedChildren = new ArrayList<City>();
		for(City temp : getChildren()){
			if(temp.isUsed())
				usedChildren.add(temp);
		}
		return usedChildren;
	}

}
