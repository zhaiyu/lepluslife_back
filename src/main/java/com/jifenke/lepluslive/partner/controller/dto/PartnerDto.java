package com.jifenke.lepluslive.partner.controller.dto;

import com.jifenke.lepluslive.partner.domain.entities.Partner;

/**
 * Created by wcg on 2016/10/25.
 */
public class PartnerDto {

  private Partner partner;

  private Long scoreA;

  private Long scoreB;

  private Integer inviteLimit;//邀请礼包限制

  public Integer getInviteLimit() {
    return inviteLimit;
  }

  public void setInviteLimit(Integer inviteLimit) {
    this.inviteLimit = inviteLimit;
  }

  public Partner getPartner() {
    return partner;
  }

  public void setPartner(Partner partner) {
    this.partner = partner;
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
}
