package com.jifenke.lepluslive.merchant.domain.criteria;

/**
 * Created by wcg on 16/5/10.
 */
public class MerchantCriteria {

  private Integer partnership;

  private String merchant;

  private Integer offset;

  private Long merchantType;

  public Long getMerchantType() {
    return merchantType;
  }

  public void setMerchantType(Long merchantType) {
    this.merchantType = merchantType;
  }

  public Integer getPartnership() {
    return partnership;
  }

  public void setPartnership(Integer partnership) {
    this.partnership = partnership;
  }

  public String getMerchant() {
    return merchant;
  }

  public void setMerchant(String merchant) {
    this.merchant = merchant;
  }

  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }
}
