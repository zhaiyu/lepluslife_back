package com.jifenke.lepluslive.coupon.domain.entities;

import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantUser;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by wcg on 2017/6/20. 用户代金券领取日志
 */
@Entity
@Table(name = "CASH_COUPON_LOG")
public class CashCouponLog {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  private LeJiaUser leJiaUser;

  private Long rebate; //抵现金额

  @OneToOne
  private CashCoupon cashCoupon;

  @ManyToOne
  private MerchantUser merchantUser;

  private Long limit; //使用限制

  private Integer state; //0 未使用 1 已经使用 2 过期

  private Integer origin; //订单类型 0 商城兑换 1 消费领取

  private Date createDate = new Date(); //创建时间

  private String sid = MvUtil.getRandomNumber(12); //领券单号

  private Long scorec; //使用金币






}
