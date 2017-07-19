package com.jifenke.lepluslive.order.domain.entities;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by wcg on 2017/4/27.
 * 记录scanCodeOrder 额外信息
 */
@Entity
@Table(name = "SCAN_CODE_ORDER_EXT")
public class ScanCodeOrderExt {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Long merchantUserId; //门店对应的商户ID

  private Integer source = 0;   //支付来源  0=WAP|1=APP

  private Integer payType = 0; //0代表微信 1 代表支付宝

  private Integer useScoreA = 0; //是否使用红包付款 0 不用 1 使用

  private String merchantNum;  //该订单使用的通道子商户号

  private String tradeDate; //yyyy-MM-dd 结算日期

  private Integer gatewayType; //通道类型 0 富有 1 易宝

  private BigDecimal merchantRate;  //商户号当时的佣金费率

  private Long thirdCommission = 0L; //三方手续费=totalPrice*commission

  private Long thirdTrueCommission = 0L;  //三方实际手续费(对积分客)=truePay*0.35%

  private String aliUserid; //当为支付宝支付时的userid

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getMerchantUserId() {
    return merchantUserId;
  }

  public void setMerchantUserId(Long merchantUserId) {
    this.merchantUserId = merchantUserId;
  }

  public Integer getSource() {
    return source;
  }

  public void setSource(Integer source) {
    this.source = source;
  }

  public Integer getPayType() {
    return payType;
  }

  public void setPayType(Integer payType) {
    this.payType = payType;
  }

  public Integer getUseScoreA() {
    return useScoreA;
  }

  public void setUseScoreA(Integer useScoreA) {
    this.useScoreA = useScoreA;
  }

  public String getMerchantNum() {
    return merchantNum;
  }

  public void setMerchantNum(String merchantNum) {
    this.merchantNum = merchantNum;
  }

  public String getTradeDate() {
    return tradeDate;
  }

  public void setTradeDate(String tradeDate) {
    this.tradeDate = tradeDate;
  }

  public Integer getGatewayType() {
    return gatewayType;
  }

  public void setGatewayType(Integer gatewayType) {
    this.gatewayType = gatewayType;
  }

  public BigDecimal getMerchantRate() {
    return merchantRate;
  }

  public void setMerchantRate(BigDecimal merchantRate) {
    this.merchantRate = merchantRate;
  }

  public Long getThirdCommission() {
    return thirdCommission;
  }

  public void setThirdCommission(Long thirdCommission) {
    this.thirdCommission = thirdCommission;
  }

  public Long getThirdTrueCommission() {
    return thirdTrueCommission;
  }

  public void setThirdTrueCommission(Long thirdTrueCommission) {
    this.thirdTrueCommission = thirdTrueCommission;
  }

  public String getAliUserid() {
    return aliUserid;
  }

  public void setAliUserid(String aliUserid) {
    this.aliUserid = aliUserid;
  }
}
