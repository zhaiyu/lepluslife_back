package com.jifenke.lepluslive.coupon.domain.entities;

import com.jifenke.lepluslive.groupon.domain.entities.GrouponProduct;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by wcg on 2017/6/14.满减券产品对应门店
 */
@Entity
@Table(name = "CASH_COUPON_MERCHANT")
public class CashCouponMerchant {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  private CashCouponProduct cashCouponProduct;

  @ManyToOne
  private Merchant merchant;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public CashCouponProduct getCashCouponProduct() {
    return cashCouponProduct;
  }

  public void setCashCouponProduct(CashCouponProduct cashCouponProduct) {
    this.cashCouponProduct = cashCouponProduct;
  }

  public Merchant getMerchant() {
    return merchant;
  }

  public void setMerchant(Merchant merchant) {
    this.merchant = merchant;
  }
}
