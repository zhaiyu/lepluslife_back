package com.jifenke.lepluslive.weixin.domain.criteria;

/**
 * Created by zhangwen on 16/7/15.
 */
public class MessageCriteria {

  private String startDate;

  private String endDate;

  private Integer offset;

  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
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

}
