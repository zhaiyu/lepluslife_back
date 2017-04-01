package com.jifenke.lepluslive.merchant.domain.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by wcg on 2017/3/16. 商户(非门店) 储值活动
 */
@Entity
@Table(name = "MERCHANT_STORED_PRODUCT")
public class MerchantStoredProduct {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  private MerchantStoredActivity merchantStoredActivity; //对应商户

  private Long storedIncrease; //增加储值

  private Long truePay; //实际支付

  private BigDecimal scoreCRate; //返金币比例

  private Long commission; //收取的佣金 固定值

  private Integer state; // 0 下线 1 上线

  private BigDecimal shareRate; //分润比例

  private Date createdDate = new Date();


  public BigDecimal getShareRate() {
    return shareRate;
  }

  public void setShareRate(BigDecimal shareRate) {
    this.shareRate = shareRate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public MerchantStoredActivity getMerchantStoredActivity() {
    return merchantStoredActivity;
  }

  public void setMerchantStoredActivity(MerchantStoredActivity merchantStoredActivity) {
    this.merchantStoredActivity = merchantStoredActivity;
  }

  public Long getStoredIncrease() {
    return storedIncrease;
  }

  public void setStoredIncrease(Long storedIncrease) {
    this.storedIncrease = storedIncrease;
  }

  public Long getTruePay() {
    return truePay;
  }

  public void setTruePay(Long truePay) {
    this.truePay = truePay;
  }

  public BigDecimal getScoreCRate() {
    return scoreCRate;
  }

  public void setScoreCRate(BigDecimal scoreCRate) {
    this.scoreCRate = scoreCRate;
  }

  public Long getCommission() {
    return commission;
  }

  public void setCommission(Long commission) {
    this.commission = commission;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
}
