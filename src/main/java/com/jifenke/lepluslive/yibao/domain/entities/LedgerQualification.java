package com.jifenke.lepluslive.yibao.domain.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 资质管理
 * 注意： 1、 个人类型账户必上传资质：身份证正、反面，手持身份证照片，银行卡正面；
 * 企业类型账户必上传资质：企业五证（营业执照，法人身份证正、反面，银行开户许可证，组织机构代码证，
 * 税务登记证），仅当营业执照为统一社会信用代码时，不需上传组织机构代码证和税务登记证；
 * 非必填参数请传""
 * Created by zhangwen on 2017/7/11.
 */
@Entity
@Table(name = "YB_LEDGER_QUALIFICATION")
public class LedgerQualification {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Date dateCreated = new Date();

  private Date dateUpdated = new Date();

  @OneToOne
  @NotNull
  private MerchantUserLedger merchantUserLedger;

  /**
   * 该注释适用以下所有字段
   * "0":初始化状态，未上传
   * 已成功上传到易宝，必以'http'开头
   * 图片上传到服务器本地(/home/yibaoDir)和OSS(lepluslive-image)
   */
  @Column(nullable = false, length = 150)
  private String idCardFront = "0";  //身份证正面

  @Column(nullable = false, length = 150)
  private String idCardBack = "0";   //身份证背面

  @Column(nullable = false, length = 150)
  private String bankCardFront = "0";  //银行卡正面

  @Column(nullable = false, length = 150)
  private String bankCardBack = "0";  //银行卡背面

  @Column(nullable = false, length = 150)
  private String personPhoto = "0";   //手持身份证照片

  @Column(length = 150)
  private String bussinessLicense = "0";  //营业执照

  @Column(length = 150)
  private String bussinessCertificates = "0";  //工商证

  @Column(length = 150)
  private String organizationCode = "0";  //组织机构代码证

  @Column(length = 150)
  private String taxRegistration = "0";   //税务登记证

  @Column(length = 150)
  private String bankAccountLicence = "0";   //银行开户许可证

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(Date dateCreated) {
    this.dateCreated = dateCreated;
  }

  public Date getDateUpdated() {
    return dateUpdated;
  }

  public void setDateUpdated(Date dateUpdated) {
    this.dateUpdated = dateUpdated;
  }

  public MerchantUserLedger getMerchantUserLedger() {
    return merchantUserLedger;
  }

  public void setMerchantUserLedger(
      MerchantUserLedger merchantUserLedger) {
    this.merchantUserLedger = merchantUserLedger;
  }

  public String getIdCardFront() {
    return idCardFront;
  }

  public void setIdCardFront(String idCardFront) {
    this.idCardFront = idCardFront;
  }

  public String getIdCardBack() {
    return idCardBack;
  }

  public void setIdCardBack(String idCardBack) {
    this.idCardBack = idCardBack;
  }

  public String getBankCardFront() {
    return bankCardFront;
  }

  public void setBankCardFront(String bankCardFront) {
    this.bankCardFront = bankCardFront;
  }

  public String getBankCardBack() {
    return bankCardBack;
  }

  public void setBankCardBack(String bankCardBack) {
    this.bankCardBack = bankCardBack;
  }

  public String getPersonPhoto() {
    return personPhoto;
  }

  public void setPersonPhoto(String personPhoto) {
    this.personPhoto = personPhoto;
  }

  public String getBussinessLicense() {
    return bussinessLicense;
  }

  public void setBussinessLicense(String bussinessLicense) {
    this.bussinessLicense = bussinessLicense;
  }

  public String getBussinessCertificates() {
    return bussinessCertificates;
  }

  public void setBussinessCertificates(String bussinessCertificates) {
    this.bussinessCertificates = bussinessCertificates;
  }

  public String getOrganizationCode() {
    return organizationCode;
  }

  public void setOrganizationCode(String organizationCode) {
    this.organizationCode = organizationCode;
  }

  public String getTaxRegistration() {
    return taxRegistration;
  }

  public void setTaxRegistration(String taxRegistration) {
    this.taxRegistration = taxRegistration;
  }

  public String getBankAccountLicence() {
    return bankAccountLicence;
  }

  public void setBankAccountLicence(String bankAccountLicence) {
    this.bankAccountLicence = bankAccountLicence;
  }
}
