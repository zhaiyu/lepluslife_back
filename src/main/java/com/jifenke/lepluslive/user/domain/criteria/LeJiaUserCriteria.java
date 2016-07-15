package com.jifenke.lepluslive.user.domain.criteria;


/**
 * Created by zhangwen on 16/7/5.
 */
public class LeJiaUserCriteria {

  private String startDate;

  private String endDate;

  private String nickname;

  private String phoneNumber;

  private String userSid;

  private Long province;

  private Long city;

  private String merchant;  //商户名称

  private String partner;  //合伙人名称

  private Integer userType;  //用户类型   WeiXinUser.state

  private Integer subState;  //关注状态   WeiXinUser.state

  private Integer massRemain;  //本月群发余数

  private Integer offset;

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getUserSid() {
    return userSid;
  }

  public void setUserSid(String userSid) {
    this.userSid = userSid;
  }

  public String getMerchant() {
    return merchant;
  }

  public void setMerchant(String merchant) {
    this.merchant = merchant;
  }

  public String getPartner() {
    return partner;
  }

  public void setPartner(String partner) {
    this.partner = partner;
  }

  public Integer getUserType() {
    return userType;
  }

  public void setUserType(Integer userType) {
    this.userType = userType;
  }

  public Integer getSubState() {
    return subState;
  }

  public void setSubState(Integer subState) {
    this.subState = subState;
  }

  public Integer getMassRemain() {
    return massRemain;
  }

  public void setMassRemain(Integer massRemain) {
    this.massRemain = massRemain;
  }

  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  public Long getProvince() {
    return province;
  }

  public void setProvince(Long province) {
    this.province = province;
  }

  public Long getCity() {
    return city;
  }

  public void setCity(Long city) {
    this.city = city;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }
}
