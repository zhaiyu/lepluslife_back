package com.jifenke.lepluslive.user.controller.dto;

import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.user.domain.entities.RegisterOrigin;
import com.jifenke.lepluslive.user.domain.entities.WeiXinUser;

import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.OneToOne;

/**
 * Created by wcg on 16/4/22.
 */
public class LeJiaUserDto {

  private Long id;

  private String oneBarCodeUrl;

  private String userSid = MvUtil.getBarCodeStr();

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
}
