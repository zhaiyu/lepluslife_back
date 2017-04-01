package com.jifenke.lepluslive.score.domain.entities;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantUser;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by wcg on 2017/3/15. 储值账户,与用户,商户对应
 */
@Entity
@Table(name = "SCORED")
public class ScoreD {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Long score = 0L; //可用余额，单位分

  @OneToOne
  private LeJiaUser leJiaUser;

  @OneToOne
  private MerchantUser merchantUser;

  private Long totalScore = 0L; // 累计储值

  private Long totalPay = 0L;// 累计实付金额

  private Long totalScoreC = 0L;//总返c积分值 储值消费会带来c积分的改变

  private Long scoreCToUser = 0L; //已经带来的c金币的改变

  private BigDecimal shareRate; // 分润比例  只要会员购买储值产品 就会刷新比例

  private BigDecimal scoreCRate; //返金币比例 只要会员购买储值产品 就会刷新比例

  private Date createdDate = new Date();

  public BigDecimal getShareRate() {
    return shareRate;
  }

  public void setShareRate(BigDecimal shareRate) {
    this.shareRate = shareRate;
  }

  public BigDecimal getScoreCRate() {
    return scoreCRate;
  }

  public void setScoreCRate(BigDecimal scoreCRate) {
    this.scoreCRate = scoreCRate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getScore() {
    return score;
  }

  public void setScore(Long score) {
    this.score = score;
  }

  public LeJiaUser getLeJiaUser() {
    return leJiaUser;
  }

  public void setLeJiaUser(LeJiaUser leJiaUser) {
    this.leJiaUser = leJiaUser;
  }

  public MerchantUser getMerchantUser() {
    return merchantUser;
  }

  public void setMerchantUser(MerchantUser merchantUser) {
    this.merchantUser = merchantUser;
  }

  public Long getTotalScore() {
    return totalScore;
  }

  public void setTotalScore(Long totalScore) {
    this.totalScore = totalScore;
  }

  public Long getTotalPay() {
    return totalPay;
  }

  public void setTotalPay(Long totalPay) {
    this.totalPay = totalPay;
  }

  public Long getTotalScoreC() {
    return totalScoreC;
  }

  public void setTotalScoreC(Long totalScoreC) {
    this.totalScoreC = totalScoreC;
  }

  public Long getScoreCToUser() {
    return scoreCToUser;
  }

  public void setScoreCToUser(Long scoreCToUser) {
    this.scoreCToUser = scoreCToUser;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
}
