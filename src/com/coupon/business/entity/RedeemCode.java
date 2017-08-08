package com.coupon.business.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.coupon.business.entity.base.RedeemCodeEntity;

@Entity
@Table(name = "coupon_redeemCode")
public class RedeemCode extends RedeemCodeEntity implements Comparable<RedeemCode>{

	public List<RedeemCode> getChildrenOrderByUsed(){
		List<RedeemCode> redeemCodeList = (List<RedeemCode>)this.getChildren();
		Collections.sort(redeemCodeList);
		return redeemCodeList;
	}

	@Override
	public int compareTo(RedeemCode o) {
		RedeemCode redeemCode = (RedeemCode)o;
		return (this.isUsed()+"").compareTo(redeemCode.isUsed()+"");
	}

}
