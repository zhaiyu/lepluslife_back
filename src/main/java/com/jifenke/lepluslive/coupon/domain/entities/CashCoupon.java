package com.jifenke.lepluslive.coupon.domain.entities;

import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by wcg on 2017/6/20. 用户代金券
 */
@Entity
@Table(name = "CASH_COUPON")
public class CashCoupon {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  private LeJiaUser leJiaUser;

  private Long rebate; //抵现金额

  @ManyToOne
  private CashCouponProduct cashCouponProduct;

  private Long limit; //使用限制

  private Integer state; //0 未使用 1 已经使用 2 过期


  private String orderSid; //使用订单号


  private Integer orderType; //订单类型 0 乐加订单 1 2码合一订单

  private Date createDate = new Date(); //创建时间

  private Date startDate; //启始时间

  private Date expiredDate; //失效时间


}
