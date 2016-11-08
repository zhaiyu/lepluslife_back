package com.jifenke.lepluslive.merchant.controller.dto;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by wcg on 16/9/6.
 */
public class MerchantPosDto {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  private Merchant merchant;

  private String posId;

  private String sshKey;

  private String psamCard;

  private Integer type; //1 封顶类pos 0 非封顶pos

  private BigDecimal posCommission; //pos 佣金 针对非会员消费

  private BigDecimal ljCommission; //会员刷卡消费佣金

  private BigDecimal wxCommission; // 微信佣金

  private BigDecimal aliCommission; //阿里佣金

  private BigDecimal bdCommission; //百度佣金

  private Long ceil; //封顶手续费

  private String posMerchantNo; //pos商户号

  private String phoneNumber;//手机号

  private String name; //账户名称

  private String bankName; //开户行号

  private String unionBankNo;//联行号

  private String bankNo;//银行卡号

  private Date createdDate;

  private Long normalOrderFlow;

  private Long importOrderFlow;


  private BigDecimal debitCardCommission; //借记卡非会员佣金比

  private BigDecimal creditCardCommission; //贷记卡非会员佣金比

  private BigDecimal wxUserCommission;//微信会员佣金比

  private BigDecimal aliUserCommission; //支付宝会员佣金比


  public Long getNormalOrderFlow() {
    return normalOrderFlow;
  }

  public void setNormalOrderFlow(Long normalOrderFlow) {
    this.normalOrderFlow = normalOrderFlow;
  }

  public Long getImportOrderFlow() {
    return importOrderFlow;
  }

  public void setImportOrderFlow(Long importOrderFlow) {
    this.importOrderFlow = importOrderFlow;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Merchant getMerchant() {
    return merchant;
  }

  public void setMerchant(Merchant merchant) {
    this.merchant = merchant;
  }

  public String getPosId() {
    return posId;
  }

  public void setPosId(String posId) {
    this.posId = posId;
  }

  public String getSshKey() {
    return sshKey;
  }

  public void setSshKey(String sshKey) {
    this.sshKey = sshKey;
  }

  public String getPsamCard() {
    return psamCard;
  }

  public void setPsamCard(String psamCard) {
    this.psamCard = psamCard;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public BigDecimal getPosCommission() {
    return posCommission;
  }

  public void setPosCommission(BigDecimal posCommission) {
    this.posCommission = posCommission;
  }

  public BigDecimal getLjCommission() {
    return ljCommission;
  }

  public void setLjCommission(BigDecimal ljCommission) {
    this.ljCommission = ljCommission;
  }

  public BigDecimal getWxCommission() {
    return wxCommission;
  }

  public void setWxCommission(BigDecimal wxCommission) {
    this.wxCommission = wxCommission;
  }

  public BigDecimal getAliCommission() {
    return aliCommission;
  }

  public void setAliCommission(BigDecimal aliCommission) {
    this.aliCommission = aliCommission;
  }

  public BigDecimal getBdCommission() {
    return bdCommission;
  }

  public void setBdCommission(BigDecimal bdCommission) {
    this.bdCommission = bdCommission;
  }

  public Long getCeil() {
    return ceil;
  }

  public void setCeil(Long ceil) {
    this.ceil = ceil;
  }

  public String getPosMerchantNo() {
    return posMerchantNo;
  }

  public void setPosMerchantNo(String posMerchantNo) {
    this.posMerchantNo = posMerchantNo;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getUnionBankNo() {
    return unionBankNo;
  }

  public void setUnionBankNo(String unionBankNo) {
    this.unionBankNo = unionBankNo;
  }

  public String getBankNo() {
    return bankNo;
  }

  public void setBankNo(String bankNo) {
    this.bankNo = bankNo;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public BigDecimal getDebitCardCommission() {
    return debitCardCommission;
  }

  public void setDebitCardCommission(BigDecimal debitCardCommission) {
    this.debitCardCommission = debitCardCommission;
  }

  public BigDecimal getCreditCardCommission() {
    return creditCardCommission;
  }

  public void setCreditCardCommission(BigDecimal creditCardCommission) {
    this.creditCardCommission = creditCardCommission;
  }

  public BigDecimal getWxUserCommission() {
    return wxUserCommission;
  }

  public void setWxUserCommission(BigDecimal wxUserCommission) {
    this.wxUserCommission = wxUserCommission;
  }

  public BigDecimal getAliUserCommission() {
    return aliUserCommission;
  }

  public void setAliUserCommission(BigDecimal aliUserCommission) {
    this.aliUserCommission = aliUserCommission;
  }
}
