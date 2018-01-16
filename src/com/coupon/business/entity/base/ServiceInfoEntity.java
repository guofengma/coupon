package com.coupon.business.entity.base;

import java.util.Date;

import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import com.coupon.base.entity.BaseEntity;
import com.coupon.business.entity.Record;

@MappedSuperclass
public abstract class ServiceInfoEntity extends BaseEntity{

	@OneToOne
	@JoinColumn(name = "recordId")
	protected Record record;  //订单信息
	
	protected Date reservationTime;//客户预约日期
	
	protected String amOrPm;//客户预约时间（上午或下午）
	
	protected String reservationAddress;//客户填写服务地址
	
	protected String contact;//客户联系方式
	
	protected String comments;//客户填写备注
	
	protected String deal;//0是待处理；1是预约成功，2是预约失败
	
	protected Date confirmReservationTime;//后台确认服务时间
	
	protected String confirmReservationAddress;//后台确认服务地点
	
	protected String reservationPerson;//服务人员
	
	protected String confirmContact;//确认客户电话
	
	protected String reservationPersonContact;//服务人员电话
	
	protected String confirmComment;//确认备注信息

	public Record getRecord() {
		return record;
	}

	public Date getReservationTime() {
		return reservationTime;
	}

	public String getAmOrPm() {
		return amOrPm;
	}

	public String getReservationAddress() {
		return reservationAddress;
	}

	public String getContact() {
		return contact;
	}

	public String getComments() {
		return comments;
	}

	public String getDeal() {
		return deal;
	}

	public Date getConfirmReservationTime() {
		return confirmReservationTime;
	}

	public String getConfirmReservationAddress() {
		return confirmReservationAddress;
	}

	public String getReservationPerson() {
		return reservationPerson;
	}

	public String getConfirmContact() {
		return confirmContact;
	}

	public String getReservationPersonContact() {
		return reservationPersonContact;
	}

	public String getConfirmComment() {
		return confirmComment;
	}

	public void setRecord(Record record) {
		this.record = record;
	}

	public void setReservationTime(Date reservationTime) {
		this.reservationTime = reservationTime;
	}

	public void setAmOrPm(String amOrPm) {
		this.amOrPm = amOrPm;
	}

	public void setReservationAddress(String reservationAddress) {
		this.reservationAddress = reservationAddress;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setDeal(String deal) {
		this.deal = deal;
	}

	public void setConfirmReservationTime(Date confirmReservationTime) {
		this.confirmReservationTime = confirmReservationTime;
	}

	public void setConfirmReservationAddress(String confirmReservationAddress) {
		this.confirmReservationAddress = confirmReservationAddress;
	}

	public void setReservationPerson(String reservationPerson) {
		this.reservationPerson = reservationPerson;
	}

	public void setConfirmContact(String confirmContact) {
		this.confirmContact = confirmContact;
	}

	public void setReservationPersonContact(String reservationPersonContact) {
		this.reservationPersonContact = reservationPersonContact;
	}

	public void setConfirmComment(String confirmComment) {
		this.confirmComment = confirmComment;
	}
	
}
