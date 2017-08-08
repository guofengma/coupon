package com.coupon.business.entity.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import com.coupon.base.entity.BaseEntity;
import com.coupon.business.entity.RechargeCode;
import com.coupon.system.entity.User;

@MappedSuperclass
public class RechargeCodeEntity extends BaseEntity{
	
	protected String code ;//兑换码
	
	protected boolean used ;//是否已经使用
	
	protected Date startTime;//有效期开始时间
	
	protected Date endTime;//有效期结束时间
	
	protected String batch;//批次
	
	protected int points ;//充值分数
	
	protected boolean made ;//该批次是否已经制作
	
	@ManyToOne
	@JoinColumn(name = "userId")
	protected  User user;
	
	@ManyToOne
	@JoinColumn(name = "parent_id")
	protected RechargeCode parent;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "parent")
	protected List<RechargeCode> children;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
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

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	public boolean isMade() {
		return made;
	}

	public void setMade(boolean made) {
		this.made = made;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public RechargeCode getParent() {
		return parent;
	}

	public void setParent(RechargeCode parent) {
		this.parent = parent;
	}

	public List<RechargeCode> getChildren() {
		return children;
	}

	public void setChildren(List<RechargeCode> children) {
		this.children = children;
	}
	
	public List<RechargeCode> getUnUsedChildren(){
		List<RechargeCode> RechargeCodeList = new ArrayList<RechargeCode>();
		for(RechargeCode temp : getChildren()){
			if(!temp.isUsed())
				RechargeCodeList.add(temp);
		}
		return RechargeCodeList;
	}
}
