package com.coupon.business.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.coupon.business.entity.base.ProductEntity;
import com.coupon.system.entity.City;

@Entity
@Table(name = "coupon_product")
public class Product extends ProductEntity{

	public List<RedeemCode> getCanBeGivenCode(){//一个商品可以发放的兑换码
		List<RedeemCode> redeemCode = new ArrayList<RedeemCode>();
		List<RedeemCode> batch = this.getRedeemCode(); //获取该商品的批次
		for(RedeemCode temp : batch){
			if((!temp.getDeleted())&&(!temp.isUsed())){
				List<RedeemCode> code = temp.getUnUsedChildren();
				for(RedeemCode temp1 : code){
					if(!temp1.isStatu()){
						redeemCode.add(temp1);
					}
				}
			}
		}
		return redeemCode;
	}
	
	public List<RedeemCode> getCanBeGivenBatch(){//一个商品可以发放的兑换码批次
		List<RedeemCode> redeemCode = new ArrayList<RedeemCode>();
		List<RedeemCode> batch = this.getRedeemCode(); //获取该商品的批次
		for(RedeemCode temp : batch){
			if(!temp.getDeleted()){
				redeemCode.add(temp);
			}
		}
		return redeemCode;
	}
}
