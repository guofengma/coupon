package com.coupon.business.entity.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.coupon.base.entity.BaseEntity;
import com.coupon.business.entity.RechargeCode;
import com.coupon.business.entity.Record;
import com.coupon.system.entity.User;

@MappedSuperclass
public class RechargeCodeEntity extends BaseEntity{
	
	protected String code ;//兑换码卡号
	
	protected String keyt ;//兑换码密钥
	
	protected boolean used ;//用户是否已经使用它去充值了
	
	protected Date startTime;//有效期开始时间
	
	protected Date endTime;//有效期结束时间
	
	protected String batch;//批次
	
	protected int points ;//充值分数
	
	protected boolean made ;//该批次是否已经制作
	
	protected boolean given;//该兑换码是否发放给用户了
	
	protected boolean statu;//状态  0为正常，1为停用
	
	protected String approved;//前台申请e兑码审核，1未审核，2审核已通过，3审核未通过
	
	protected String source;//来源（后台生成为1，前台申请生成为2）
	
	protected User fafangUser;//发放该兑换码的员工
	
	@ManyToOne
	@JoinColumn(name = "userId")
	protected  User user;
	
	@ManyToOne
	@JoinColumn(name = "parent_id")
	protected RechargeCode parent;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "parent")
	protected List<RechargeCode> children;
	
	@OneToOne(cascade = { CascadeType.ALL }, mappedBy = "rechargeCode", fetch = FetchType.EAGER)
	protected Record record;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getKeyt() {
		return keyt;
	}

	public void setKeyt(String keyt) {
		this.keyt = keyt;
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

	public boolean isStatu() {
		return statu;
	}

	public void setStatu(boolean statu) {
		this.statu = statu;
	}
	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}
	
	public User getFafangUser() {
		return fafangUser;
	}

	public void setFafangUser(User fafangUser) {
		this.fafangUser = fafangUser;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public boolean isGiven() {
		return given;
	}

	public void setGiven(boolean given) {
		this.given = given;
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
	
	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}

	public List<RechargeCode> getUnUsedChildren(){
		List<RechargeCode> RechargeCodeList = new ArrayList<RechargeCode>();
		for(RechargeCode temp : getChildren()){
			if(!temp.isUsed())
				RechargeCodeList.add(temp);
		}
		return RechargeCodeList;
	}
	
	public List<RechargeCode> getUnGivenChildren(){
		List<RechargeCode> RechargeCodeList = new ArrayList<RechargeCode>();
		for(RechargeCode temp : getChildren()){
			if(!temp.isGiven())
				RechargeCodeList.add(temp);
		}
		return RechargeCodeList;
	}
}
