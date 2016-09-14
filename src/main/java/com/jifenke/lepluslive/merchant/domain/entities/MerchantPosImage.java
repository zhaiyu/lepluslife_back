package com.jifenke.lepluslive.merchant.domain.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by xf on 2016/9/13.
 */
@Table(name="MERCHANT_POS_IMAGE")
@Entity
public class MerchantPosImage {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  private Merchant merchant;

  private String platServerProcImg1;         // 积分客微商城:  平台受理服务协议书
  private String platServerProcImg2;
  private String platServerProcImg3;
  private String platServerProcImg4;
  private String platServerProcImg5;

  private String mercBaseInfoImg1;           // 商户基础资料表
  private String mercBaseInfoImg2;

  private String cnepaySpecialMercInfoImg1;  // 中汇支付收单特约商户信息调查表
  private String cnepaySpecialMercInfoImg2;
  private String cnepaySpecialMercInfoImg3;

  private String accountAuthorizationImg;     // 结算授权书

  private String licenseImg;           //   营业执照
  private String idCardImg;            //   法人身份证（复印件）
  private String taxRegistrationImg;   //   税务登记证
  private String bankIdCardImg;        //   结算银行卡持有身份证（复印件）
  private String orgConstructionImg;   //   组织结构照
  private String bankCardImg;          //   结算银行卡


  public String getAccountAuthorizationImg() {
    return accountAuthorizationImg;
  }

  public void setAccountAuthorizationImg(String accountAuthorizationImg) {
    this.accountAuthorizationImg = accountAuthorizationImg;
  }

  public String getBankCardImg() {
    return bankCardImg;
  }

  public void setBankCardImg(String bankCardImg) {
    this.bankCardImg = bankCardImg;
  }

  public String getBankIdCardImg() {
    return bankIdCardImg;
  }

  public void setBankIdCardImg(String bankIdCardImg) {
    this.bankIdCardImg = bankIdCardImg;
  }

  public String getCnepaySpecialMercInfoImg1() {
    return cnepaySpecialMercInfoImg1;
  }

  public void setCnepaySpecialMercInfoImg1(String cnepaySpecialMercInfoImg1) {
    this.cnepaySpecialMercInfoImg1 = cnepaySpecialMercInfoImg1;
  }

  public String getCnepaySpecialMercInfoImg2() {
    return cnepaySpecialMercInfoImg2;
  }

  public void setCnepaySpecialMercInfoImg2(String cnepaySpecialMercInfoImg2) {
    this.cnepaySpecialMercInfoImg2 = cnepaySpecialMercInfoImg2;
  }

  public String getCnepaySpecialMercInfoImg3() {
    return cnepaySpecialMercInfoImg3;
  }

  public void setCnepaySpecialMercInfoImg3(String cnepaySpecialMercInfoImg3) {
    this.cnepaySpecialMercInfoImg3 = cnepaySpecialMercInfoImg3;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getIdCardImg() {
    return idCardImg;
  }

  public void setIdCardImg(String idCardImg) {
    this.idCardImg = idCardImg;
  }

  public String getLicenseImg() {
    return licenseImg;
  }

  public void setLicenseImg(String licenseImg) {
    this.licenseImg = licenseImg;
  }

  public String getMercBaseInfoImg1() {
    return mercBaseInfoImg1;
  }

  public void setMercBaseInfoImg1(String mercBaseInfoImg1) {
    this.mercBaseInfoImg1 = mercBaseInfoImg1;
  }

  public String getMercBaseInfoImg2() {
    return mercBaseInfoImg2;
  }

  public void setMercBaseInfoImg2(String mercBaseInfoImg2) {
    this.mercBaseInfoImg2 = mercBaseInfoImg2;
  }

  public Merchant getMerchant() {
    return merchant;
  }

  public void setMerchant(Merchant merchant) {
    this.merchant = merchant;
  }

  public String getOrgConstructionImg() {
    return orgConstructionImg;
  }

  public void setOrgConstructionImg(String orgConstructionImg) {
    this.orgConstructionImg = orgConstructionImg;
  }

  public String getPlatServerProcImg1() {
    return platServerProcImg1;
  }

  public void setPlatServerProcImg1(String platServerProcImg1) {
    this.platServerProcImg1 = platServerProcImg1;
  }

  public String getPlatServerProcImg2() {
    return platServerProcImg2;
  }

  public void setPlatServerProcImg2(String platServerProcImg2) {
    this.platServerProcImg2 = platServerProcImg2;
  }

  public String getPlatServerProcImg3() {
    return platServerProcImg3;
  }

  public void setPlatServerProcImg3(String platServerProcImg3) {
    this.platServerProcImg3 = platServerProcImg3;
  }

  public String getPlatServerProcImg4() {
    return platServerProcImg4;
  }

  public void setPlatServerProcImg4(String platServerProcImg4) {
    this.platServerProcImg4 = platServerProcImg4;
  }

  public String getPlatServerProcImg5() {
    return platServerProcImg5;
  }

  public void setPlatServerProcImg5(String platServerProcImg5) {
    this.platServerProcImg5 = platServerProcImg5;
  }

  public String getTaxRegistrationImg() {
    return taxRegistrationImg;
  }

  public void setTaxRegistrationImg(String taxRegistrationImg) {
    this.taxRegistrationImg = taxRegistrationImg;
  }
}
