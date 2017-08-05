package com.coupon.system.entity.base;

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
import com.coupon.business.entity.Customer;
import com.coupon.system.entity.City;
import com.coupon.system.entity.Role;
import com.coupon.system.entity.User;

@MappedSuperclass
public abstract class UserEntity extends BaseEntity {

	protected String displayName;
	protected String name;
	protected String password;

	@ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER )
	@JoinTable(name = "coupon_user_role", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
	protected Set<Role> roles = new HashSet<Role>();
	
	@ManyToOne
	@JoinColumn(name = "city_id")
	protected City city;
	
	public List<Customer> getCustomer() {
		return customer;
	}

	public void setCustomer(List<Customer> customer) {
		this.customer = customer;
	}

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "user")
	protected List<Customer> customer;
	
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "checkUser")
	protected List<Customer> beCheckedCustomer;

	public List<Customer> getBeCheckedCustomer() {
		return beCheckedCustomer;
	}

	public void setBeCheckedCustomer(List<Customer> beCheckedCustomer) {
		this.beCheckedCustomer = beCheckedCustomer;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	/**
	 * @return the roles
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles
	 *            the roles to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof User))
			return false;
		else {
			User user = (User) obj;
			if (null == this.getId() || null == user.getId())
				return false;
			else
				return (this.getId().equals(user.getId()));
		}
	}

	private int hashCode = Integer.MIN_VALUE;

	public int hashCode() {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId())
				return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":"
						+ this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

}
