package com.jifenke.lepluslive.scoreAAccount.domain.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by lss on 2016/9/12.
 */
@Entity
@Table(name = "SCOREA_ACCOUNT")
public class ScoreAAccount {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Date changeDate; //红包变更时间

  private Long useScoreA;//使用红包金额

  private Long settlementAmount;//应结算金额

  private Long issuedScoreA;//发放红包金额

  private Long  commissionIncome;//佣金收入

  private Long jfkShare;//分润后积分客收入

  public Long getCommissionIncome() {
    return commissionIncome;
  }

  public void setCommissionIncome(Long commissionIncome) {
    this.commissionIncome = commissionIncome;
  }

  public Date getChangeDate() {
    return changeDate;
  }

  public void setChangeDate(Date changeDate) {
    this.changeDate = changeDate;
  }

  public Long getIssuedScoreA() {
    return issuedScoreA;
  }

  public void setIssuedScoreA(Long issuedScoreA) {
    this.issuedScoreA = issuedScoreA;
  }

  public Long getJfkShare() {
    return jfkShare;
  }

  public void setJfkShare(Long jfkShare) {
    this.jfkShare = jfkShare;
  }

  public Long getSettlementAmount() {
    return settlementAmount;
  }

  public void setSettlementAmount(Long settlementAmount) {
    this.settlementAmount = settlementAmount;
  }

  public Long getUseScoreA() {
    return useScoreA;
  }

  public void setUseScoreA(Long useScoreA) {
    this.useScoreA = useScoreA;
  }
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
