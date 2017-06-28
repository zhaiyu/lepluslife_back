package com.jifenke.lepluslive.coupon.domain.entities;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by wcg on 2017/6/20. 门店代金券发放规则
 */
@Entity
@Table(name = "CASH_COUPON_POLICY")
public class CashCouponPolicy {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  private Merchant merchant;

  @ManyToOne
  private CashCouponProduct cashCouponProduct;

  private Long limit =0l;//消费限制

  private Integer policy=0; // 0不限数量发放 1 只发放最高门槛

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Merchant getMerchant() {
    return merchant;
  }

  public void setMerchant(Merchant merchant) {
    this.merchant = merchant;
  }

  public CashCouponProduct getCashCouponProduct() {
    return cashCouponProduct;
  }

  public void setCashCouponProduct(CashCouponProduct cashCouponProduct) {
    this.cashCouponProduct = cashCouponProduct;
  }

  public Long getLimit() {
    return limit;
  }

  public void setLimit(Long limit) {
    this.limit = limit;
  }

  public Integer getPolicy() {
    return policy;
  }

  public void setPolicy(Integer policy) {
    this.policy = policy;
  }
}
