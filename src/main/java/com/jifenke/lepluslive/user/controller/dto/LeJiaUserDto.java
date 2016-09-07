package com.jifenke.lepluslive.user.controller.dto;

import com.jifenke.lepluslive.user.domain.entities.RegisterOrigin;

import java.awt.print.PrinterGraphics;
import java.util.Date;

/**
 * Created by wcg on 16/4/22.
 */
public class LeJiaUserDto {

  private Long id;

  private Integer state;   //会员类型

  private Integer subState;  //是否关注公众号

  private String city;      //城市

  private Integer massRemain = 4;  //本月群发余数

  private String merchantName;   //绑定商户名称

  private Date bindMerchantDate;   //绑定商户时间

  private Date bindPartnerDate;   //绑定合伙人时间

  private String partnerName;   //绑定合伙人名称

  private String oneBarCodeUrl;

  private String userSid;

  private Date createDate;

  private Date phoneBindDate;

  private String phoneNumber;

  private RegisterOrigin registerOrigin;

  private Long scoreA;

  private Long scoreB;

  private Long totalScoreA;

  private Long totalScoreB;

  private Long onLineCount;

  private String nickname;

  private String headImageUrl;

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getHeadImageUrl() {
    return headImageUrl;
  }

  public void setHeadImageUrl(String headImageUrl) {
    this.headImageUrl = headImageUrl;
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

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getPhoneBindDate() {
    return phoneBindDate;
  }

  public void setPhoneBindDate(Date phoneBindDate) {
    this.phoneBindDate = phoneBindDate;
  }

  public Integer getSubState() {
    return subState;
  }

  public void setSubState(Integer subState) {
    this.subState = subState;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public RegisterOrigin getRegisterOrigin() {
    return registerOrigin;
  }

  public void setRegisterOrigin(RegisterOrigin registerOrigin) {
    this.registerOrigin = registerOrigin;
  }

  public Long getScoreA() {
    return scoreA;
  }

  public void setScoreA(Long scoreA) {
    this.scoreA = scoreA;
  }

  public Long getScoreB() {
    return scoreB;
  }

  public void setScoreB(Long scoreB) {
    this.scoreB = scoreB;
  }

  public Long getTotalScoreA() {
    return totalScoreA;
  }

  public void setTotalScoreA(Long totalScoreA) {
    this.totalScoreA = totalScoreA;
  }

  public Long getTotalScoreB() {
    return totalScoreB;
  }

  public void setTotalScoreB(Long totalScoreB) {
    this.totalScoreB = totalScoreB;
  }

  public Long getOnLineCount() {
    return onLineCount;
  }

  public void setOnLineCount(Long onLineCount) {
    this.onLineCount = onLineCount;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Integer getMassRemain() {
    return massRemain;
  }

  public void setMassRemain(Integer massRemain) {
    this.massRemain = massRemain;
  }

  public String getMerchantName() {
    return merchantName;
  }

  public void setMerchantName(String merchantName) {
    this.merchantName = merchantName;
  }

  public String getPartnerName() {
    return partnerName;
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

  public void setPartnerName(String partnerName) {
    this.partnerName = partnerName;
  }
}
