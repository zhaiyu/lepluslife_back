package com.jifenke.lepluslive.coupon.domain.entities;

import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantUser;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by wcg on 2017/6/20. 代金券产品
 */
@Entity
@Table(name = "CASH_COUPON_PRODUCT")
public class CashCouponProduct {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Long rebate=0L; //优惠金额

  private Long limit=0L; //使用门槛

  private String logo;

  private String sid = MvUtil.getRandomNumber(12);

  @ManyToOne
  private MerchantUser merchantUser;

  private Integer validityType; // 有效期类型 0 相对日期 1 绝对日期

  private String validity; // 如果是0 则是数字 如果是1 则显示 2017-09-19 01:14:31 ~ 2017-10-19 01:14:31

  private Integer state=0; // 0下架 1 上架

  private Integer marketFlag; //是否开启商城投放 0 不开启 1 开启

  private Integer userDailyLimit=0; //用户日最多得代金券限制 开启商城投放才有效

  private Integer userLimit=0; //总限制 开启商城投放才有效

  private Long price=0L; //金币兑换售价，如果是0 说明免费 开启商城投放才有效

  private Long amount;//代金券总数 开启商城投放才有效
}
