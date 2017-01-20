package com.jifenke.lepluslive.product.domain.entities;

/**
 * 积分秒杀 查询
 * Created by tqy on 2017/1/12.
 */
public class ProductSecKillCriteria {

  private Integer offset;
  private String startDate;
  private String endDate;
  private String secKillDate; //秒杀日期
  private Integer status;     //秒杀商品 状态   1=正常  0=下架
  private Integer type;       //秒杀商品 商品类型type=3

  private Integer pageSize = 10;//每页size


  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public String getSecKillDate() {
    return secKillDate;
  }

  public void setSecKillDate(String secKillDate) {
    this.secKillDate = secKillDate;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

}
