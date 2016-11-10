package com.jifenke.lepluslive.shortMessage.domain.criteria;


/**
 * Created by zhb on 2016/10/21.
 */
public class ShortMessageDataCriteria {

  //当前页
  private Integer offset;

  private String startDate;

  private String endDate;

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
