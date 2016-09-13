package com.jifenke.lepluslive.merchant.controller.dto;

/**
 * Created by zxf on 2016/9/9.
 * pos管理-营业执照-图片
 */
public class BussineLicenseImgDto {

  private String licenseImg;           //   营业执照
  private String idCardImg;            //   法人身份证（复印件）
  private String taxRegistrationImg;   //   税务登记证
  private String bankIdCardImg;        //   结算银行卡持有身份证（复印件）
  private String orgConstructionImg;   //   组织结构照
  private String bankCardImg;          //   结算银行卡

  public String getLicenseImg() {
    return licenseImg;
  }

  public void setLicenseImg(String licenseImg) {
    this.licenseImg = licenseImg;
  }

  public String getIdCardImg() {
    return idCardImg;
  }

  public void setIdCardImg(String idCardImg) {
    this.idCardImg = idCardImg;
  }

  public String getTaxRegistrationImg() {
    return taxRegistrationImg;
  }

  public void setTaxRegistrationImg(String taxRegistrationImg) {
    this.taxRegistrationImg = taxRegistrationImg;
  }

  public String getBankIdCardImg() {
    return bankIdCardImg;
  }

  public void setBankIdCardImg(String bankIdCardImg) {
    this.bankIdCardImg = bankIdCardImg;
  }

  public String getOrgConstructionImg() {
    return orgConstructionImg;
  }

  public void setOrgConstructionImg(String orgConstructionImg) {
    this.orgConstructionImg = orgConstructionImg;
  }

  public String getBankCardImg() {
    return bankCardImg;
  }

  public void setBankCardImg(String bankCardImg) {
    this.bankCardImg = bankCardImg;
  }
}
