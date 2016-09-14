package com.jifenke.lepluslive.merchant.domain.criteria;

/**
 * Created by wcg on 16/9/6.
 */
public class MerchantPosCriteria {

  private Long merchantLocation;

  private String startDate;

  private String endDate;

  private String merchant;

  private Integer state;

  private Integer offset;

  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  public Long getMerchantLocation() {
    return merchantLocation;
  }

  public void setMerchantLocation(Long merchantLocation) {
    this.merchantLocation = merchantLocation;
  }

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

  public String getMerchant() {
    return merchant;
  }

  public void setMerchant(String merchant) {
    this.merchant = merchant;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }
}
