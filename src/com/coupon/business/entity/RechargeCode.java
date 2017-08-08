package com.coupon.business.entity;


import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.coupon.business.entity.base.RechargeCodeEntity;



@Entity
@Table(name = "coupon_rechargeCode")
public class RechargeCode extends RechargeCodeEntity implements Comparable<RechargeCode>{

	public List<RechargeCode> getChildrenOrderByUsed(){
		List<RechargeCode> rechargeCodeList = (List<RechargeCode>)this.getChildren();
		Collections.sort(rechargeCodeList);
		return rechargeCodeList;
	}

	@Override
	public int compareTo(RechargeCode o) {
		RechargeCode rechargeCode = (RechargeCode)o;
		return (""+this.getPoints()+this.isUsed()).compareTo(""+rechargeCode.getPoints()+rechargeCode.isUsed());
	}

}
