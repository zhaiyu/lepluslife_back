package com.jifenke.lepluslive.user.domain.entities;

import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.partner.domain.entities.Partner;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by wcg on 16/3/22.
 */
@Entity
@Table(name = "LE_JIA_USER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LeJiaUser {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String oneBarCodeUrl;

  private String userSid = MvUtil.getBarCodeStr();

  private Date createDate = new Date();

  private Date phoneBindDate;

  @OneToOne(fetch = FetchType.LAZY)
  private WeiXinUser weiXinUser;

  private String phoneNumber;

  @ManyToOne
  private RegisterOrigin registerOrigin;

  @ManyToOne
  private Merchant bindMerchant;

  @ManyToOne
  private Partner bindPartner;

  private Date bindMerchantDate;

  private Date bindPartnerDate;

  public Merchant getBindMerchant() {
    return bindMerchant;
  }

  public void setBindMerchant(Merchant bindMerchant) {
    this.bindMerchant = bindMerchant;
  }

  public Partner getBindPartner() {
    return bindPartner;
  }

  public void setBindPartner(Partner bindPartner) {
    this.bindPartner = bindPartner;
  }

  public RegisterOrigin getRegisterOrigin() {
    return registerOrigin;
  }

  public void setRegisterOrigin(RegisterOrigin registerOrigin) {
    this.registerOrigin = registerOrigin;
  }

  public Date getPhoneBindDate() {
    return phoneBindDate;
  }

  public void setPhoneBindDate(Date phoneBindDate) {
    this.phoneBindDate = phoneBindDate;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public WeiXinUser getWeiXinUser() {
    return weiXinUser;
  }

  public void setWeiXinUser(WeiXinUser weiXinUser) {
    this.weiXinUser = weiXinUser;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getOneBarCodeUrl() {
    return oneBarCodeUrl;
  }

  public void setOneBarCodeUrl(String oneBarCodeUrl) {
    this.oneBarCodeUrl = oneBarCodeUrl;
  }

  public String getUserSid() {
    return userSid;
  }

  public void setUserSid(String userSid) {
    this.userSid = userSid;
  }

  public Date getBindMerchantDate() {
    return bindMerchantDate;
  }

  public void setBindMerchantDate(Date bindMerchantDate) {
    this.bindMerchantDate = bindMerchantDate;
  }

  public Date getBindPartnerDate() {
    return bindPartnerDate;
  }

  public void setBindPartnerDate(Date bindPartnerDate) {
    this.bindPartnerDate = bindPartnerDate;
  }
}
